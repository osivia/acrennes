package fr.toutatice.portail.acrennes.cua.client.portlet.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.toutatice.portail.acrennes.cua.client.portlet.model.dto.CuaApplication;
import fr.toutatice.portail.acrennes.cua.client.portlet.model.dto.CuaIdentityVector;
import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaClientOperation;
import fr.toutatice.portail.acrennes.cua.client.portlet.model.dto.CuaSynchronization;
import net.sf.json.JSONArray;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.portlet.PortletException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

/**
 * CUA client portlet repository implementation.
 *
 * @author Cédric Krommenhoek
 * @see CuaClientRepository
 */
@Repository
public class CuaClientRepositoryImpl implements CuaClientRepository {

    /**
     * Log.
     */
    private final Log log;


    /**
     * Application context.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * REST template.
     */
    @Autowired
    private RestTemplate restTemplate;


    /**
     * Constructor.
     */
    public CuaClientRepositoryImpl() {
        super();

        // Log
        this.log = LogFactory.getLog(this.getClass());
    }


    @Override
    public boolean isCatalogExists(PortalControllerContext portalControllerContext, String catalogId) throws PortletException {
        boolean exists;
        try {
            ResponseEntity<String> response = this.execute(CuaClientOperation.IS_CATALOG_EXISTS, String.class, null, catalogId);
            exists = HttpStatus.NO_CONTENT.equals(response.getStatusCode());
        } catch (HttpClientErrorException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                exists = false;
            } else {
                throw new PortletException(e);
            }
        }

        return exists;
    }


    @Override
    public void createCatalog(PortalControllerContext portalControllerContext, String catalogId) throws PortletException {
        try {
            this.execute(CuaClientOperation.CREATE_CATALOG, String.class, null, catalogId);
        } catch (HttpClientErrorException e) {
            if (HttpStatus.CONFLICT.equals(e.getStatusCode())) {
                this.log.error(e.getLocalizedMessage());
            } else {
                throw new PortletException(e);
            }
        }
    }


    @Override
    public CuaApplication[] getApplications(PortalControllerContext portalControllerContext, String catalogId) throws PortletException {
        return this.get(CuaClientOperation.GET_APPLICATIONS, CuaApplication[].class, catalogId);
    }


    @Override
    public String[] getApplicationsIdentifiers(PortalControllerContext portalControllerContext, String catalogId) throws PortletException {
        return this.get(CuaClientOperation.GET_APPLICATIONS_IDENTIFIERS, String[].class, catalogId);
    }


    @Override
    public void reorderApplications(PortalControllerContext portalControllerContext, String catalogId, String[] applicationsIds) throws PortletException {
        this.reorder(CuaClientOperation.REORDER_APPLICATIONS, catalogId, applicationsIds);
    }


    @Override
    public boolean isApplicationStarred(PortalControllerContext portalControllerContext, String catalogId, String applicationId) throws PortletException {
        boolean starred;
        try {
            ResponseEntity<Boolean> response = this.execute(CuaClientOperation.IS_APPLICATION_STARRED, Boolean.class, null, catalogId, applicationId);
            starred = BooleanUtils.isTrue(response.getBody());
        } catch (HttpClientErrorException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                starred = false;
                this.log.error(e.getLocalizedMessage());
            } else {
                throw new PortletException(e);
            }
        }

        return starred;
    }


    @Override
    public void setApplicationStarred(PortalControllerContext portalControllerContext, String catalogId, String applicationId, boolean starred) throws PortletException {
        try {
            this.execute(CuaClientOperation.SET_APPLICATION_STARRED, String.class, String.valueOf(starred), catalogId, applicationId);
        } catch (HttpClientErrorException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                this.log.error(e.getLocalizedMessage());
            } else {
                throw new PortletException(e);
            }
        }
    }


    @Override
    public CuaApplication[] getStarredApplications(PortalControllerContext portalControllerContext, String catalogId) throws PortletException {
        return this.get(CuaClientOperation.GET_STARRED_APPLICATIONS, CuaApplication[].class, catalogId);
    }


    @Override
    public String[] getStarredApplicationsIdentifiers(PortalControllerContext portalControllerContext, String catalogId) throws PortletException {
        return this.get(CuaClientOperation.GET_STARRED_APPLICATIONS_IDENTIFIERS, String[].class, catalogId);
    }


    @Override
    public void reorderStarredApplications(PortalControllerContext portalControllerContext, String catalogId, String[] applicationsIds) throws PortletException {
        this.reorder(CuaClientOperation.REORDER_STARRED_APPLICATIONS, catalogId, applicationsIds);
    }


    @Override
    public CuaSynchronization getSynchronization(PortalControllerContext portalControllerContext, String catalogId) throws PortletException {
        return this.get(CuaClientOperation.GET_SYNCHRONIZATION, CuaSynchronization.class, catalogId);
    }


    @Override
    public void synchronize(PortalControllerContext portalControllerContext, String catalogId, CuaIdentityVector identityVector) throws PortletException {
        // Identity vector JSON value
        String json;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(identityVector);
        } catch (JsonProcessingException e) {
            throw new PortletException(e);
        }

        try {
            this.execute(CuaClientOperation.SYNCHRONIZE, String.class, json, catalogId);
        } catch (HttpClientErrorException e) {
            if (HttpStatus.BAD_REQUEST.equals(e.getStatusCode()) || HttpStatus.NOT_FOUND.equals(e.getStatusCode()) || HttpStatus.TOO_MANY_REQUESTS.equals(e.getStatusCode())) {
                this.log.error(e.getLocalizedMessage());
            } else {
                throw new PortletException(e);
            }
        }
    }


    /**
     * Get operation result.
     *
     * @param operation    operation
     * @param responseType response type
     * @param catalogId    catalog identifier
     * @return result
     */
    private <T> T get(CuaClientOperation operation, Class<T> responseType, String catalogId) throws PortletException {
        T result;

        try {
            ResponseEntity<T> response = this.execute(operation, responseType, null, catalogId);
            result = response.getBody();
        } catch (HttpClientErrorException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                result = null;
                this.log.error(e.getLocalizedMessage());
            } else {
                throw new PortletException(e);
            }
        }
        return result;
    }


    /**
     * Reorder applications.
     *
     * @param operation       operation
     * @param catalogId       catalog identifier
     * @param applicationsIds applications identifiers
     */
    private void reorder(CuaClientOperation operation, String catalogId, String[] applicationsIds) throws PortletException {
        // Body
        String body;
        JSONArray array = new JSONArray();
        if (ArrayUtils.isNotEmpty(applicationsIds)) {
            array.addAll(Arrays.asList(applicationsIds));
        }
        body = array.toString();

        try {
            this.execute(operation, String.class, body, catalogId);
        } catch (HttpClientErrorException e) {
            if (HttpStatus.BAD_REQUEST.equals(e.getStatusCode()) || HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                this.log.error(e.getLocalizedMessage());
            } else {
                throw new PortletException(e);
            }
        }
    }


    /**
     * Execute operation.
     *
     * @param operation    operation
     * @param responseType operation response type
     * @param body         operation body
     * @param parameters   operation parameter
     * @return operation response
     */
    private <T> ResponseEntity<T> execute(CuaClientOperation operation, Class<T> responseType, String body, String... parameters) throws PortletException {
        // File
        String file = String.format(operation.getPath(), parameters);

        // URI
        URI uri;
        try {
            URL url = new URL("https", "partenaires.ipanema.education.fr", file);
            uri = url.toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new PortletException(e);
        }

        // HTTP headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // HTTP entity
        HttpEntity<?> httpEntity = new HttpEntity<>(body, httpHeaders);

        return this.restTemplate.exchange(uri, operation.getMethod(), httpEntity, responseType);
    }

}
