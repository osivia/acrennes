package fr.toutatice.portail.acrennes.cua.client.portlet.service;

import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaClientApplication;
import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaClientApplicationsContainer;
import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaClientForm;
import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaClientSettingsForm;
import fr.toutatice.portail.acrennes.cua.client.portlet.model.comparator.CuaClientApplicationComparator;
import fr.toutatice.portail.acrennes.cua.client.portlet.model.dto.*;
import fr.toutatice.portail.acrennes.cua.client.portlet.repository.CuaClientRepository;
import fr.toutatice.portail.acrennes.directory.model.ToutaticePerson;
import fr.toutatice.portail.acrennes.directory.service.ToutaticePersonService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.util.CollectionUtil;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.portlet.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * CUA client portlet service implementation.
 *
 * @author Cédric Krommenhoek
 * @see CuaClientService
 */
@Service
public class CuaClientServiceImpl implements CuaClientService {

    /**
     * Log.
     */
    private final Log log;

    /**
     * Date format.
     */
    private final DateFormat dateFormat;


    /**
     * Application context.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * View resolver.
     */
    @Autowired
    private InternalResourceViewResolver viewResolver;

    /**
     * Portlet repository.
     */
    @Autowired
    private CuaClientRepository repository;

    /**
     * Application comparator.
     */
    @Autowired
    private CuaClientApplicationComparator applicationComparator;

    /**
     * Toutatice person service.
     */
    @Autowired
    private ToutaticePersonService personService;


    /**
     * Constructor.
     */
    public CuaClientServiceImpl() {
        super();

        // Log
        this.log = LogFactory.getLog(this.getClass());

        // Date format
        this.dateFormat = new SimpleDateFormat(DateFormatUtils.ISO_DATETIME_FORMAT.getPattern());
        this.dateFormat.setTimeZone(DateUtils.UTC_TIME_ZONE);
    }


    @Override
    public CuaClientForm getForm(PortalControllerContext portalControllerContext) throws PortletException {
        // Form
        CuaClientForm form = this.applicationContext.getBean(CuaClientForm.class);

        if (StringUtils.isEmpty(form.getCatalogId())) {
            // Catalog identifier
            String catalogId = this.getCatalogId(portalControllerContext);
            form.setCatalogId(catalogId);
        }

        return form;
    }


    @Override
    public void loadStarredApplications(PortalControllerContext portalControllerContext) throws PortletException, IOException {
        // Portlet request
        PortletRequest request = portalControllerContext.getRequest();
        // Portlet response
        PortletResponse response = portalControllerContext.getResponse();
        // Portlet context
        PortletContext portletContext = portalControllerContext.getPortletCtx();


        // Form
        CuaClientForm form = this.applicationContext.getBean(CuaClientForm.class);
        request.setAttribute("form", form);

        // Catalog identifier
        String catalogId = form.getCatalogId();

        if (StringUtils.isNotEmpty(catalogId) && !form.isPartiallyLoaded()) {
            // Synchronize
            this.synchronize(portalControllerContext, catalogId);

            // Starred applications
            CuaApplication[] starredApplications = this.repository.getStarredApplications(portalControllerContext, catalogId);
            form.setStarredApplications(this.convert(starredApplications));

            form.setPartiallyLoaded(true);


            if (ArrayUtils.isEmpty(starredApplications)) {
                // Other applications
                CuaApplication[] otherApplications = this.repository.getApplications(portalControllerContext, catalogId);
                form.setOtherApplications(this.convert(otherApplications));

                form.setFullyLoaded(true);
            }
        }


        // Path
        String path;
        try {

            View view = this.viewResolver.resolveViewName("starred-applications", null);
            JstlView jstlView = (JstlView) view;
            path = jstlView.getUrl();
        } catch (Exception e) {
            throw new PortletException(e);
        }

        PortletRequestDispatcher dispatcher = portletContext.getRequestDispatcher(path);
        dispatcher.include(request, response);
    }


    @Override
    public void loadOtherApplications(PortalControllerContext portalControllerContext) throws PortletException, IOException {
        // Portlet request
        PortletRequest request = portalControllerContext.getRequest();
        // Portlet response
        PortletResponse response = portalControllerContext.getResponse();
        // Portlet context
        PortletContext portletContext = portalControllerContext.getPortletCtx();


        // Form
        CuaClientForm form = this.applicationContext.getBean(CuaClientForm.class);
        request.setAttribute("form", form);

        // Catalog identifier
        String catalogId = form.getCatalogId();

        if (StringUtils.isNotEmpty(catalogId) && !form.isFullyLoaded()) {
            // Applications
            CuaApplication[] applications = this.repository.getApplications(portalControllerContext, catalogId);

            // Other applications
            List<CuaClientApplication> otherApplications = this.convert(applications);
            otherApplications.removeAll(form.getStarredApplications());
            form.setOtherApplications(otherApplications);

            form.setFullyLoaded(true);
        }


        // Path
        String path;
        try {

            View view = this.viewResolver.resolveViewName("other-applications", null);
            JstlView jstlView = (JstlView) view;
            path = jstlView.getUrl();
        } catch (Exception e) {
            throw new PortletException(e);
        }

        PortletRequestDispatcher dispatcher = portletContext.getRequestDispatcher(path);
        dispatcher.include(request, response);
    }


    @Override
    public CuaClientSettingsForm getSettingsForm(PortalControllerContext portalControllerContext) throws PortletException {
        // Form
        CuaClientSettingsForm form = this.applicationContext.getBean(CuaClientSettingsForm.class);

        // Catalog identifier
        String catalogId = this.getCatalogId(portalControllerContext);
        form.setCatalogId(catalogId);

        if (StringUtils.isNotEmpty(catalogId)) {
            // Applications
            CuaApplication[] applications = this.repository.getApplications(portalControllerContext, catalogId);
            List<CuaClientApplication> starredClientApplications;
            List<CuaClientApplication> otherClientApplications;
            if (ArrayUtils.isEmpty(applications)) {
                starredClientApplications = new ArrayList<>(0);
                otherClientApplications = new ArrayList<>(0);
            } else {
                starredClientApplications = new ArrayList<>(applications.length);
                otherClientApplications = new ArrayList<>(applications.length);
                for (CuaApplication application : applications) {
                    CuaClientApplication clientApplication = this.convert(application);
                    if (clientApplication.isStarred()) {
                        starredClientApplications.add(clientApplication);
                    } else {
                        otherClientApplications.add(clientApplication);
                    }
                }
            }
            form.setStarredApplications(starredClientApplications);
            form.setOtherApplications(otherClientApplications);
        }

        return form;
    }


    @Override
    public void addStar(PortalControllerContext portalControllerContext, CuaClientSettingsForm form, String applicationId) throws PortletException {
        this.updateStar(portalControllerContext, form, applicationId, true);
    }


    @Override
    public void removeStar(PortalControllerContext portalControllerContext, CuaClientSettingsForm form, String applicationId) throws PortletException {
        this.updateStar(portalControllerContext, form, applicationId, false);
    }


    @Override
    public void reorder(PortalControllerContext portalControllerContext, CuaClientSettingsForm form) throws PortletException {
        // Find application
        CuaClientApplication application = null;
        Iterator<CuaClientApplication> iterator = form.getStarredApplications().iterator();
        while ((application == null) && iterator.hasNext()) {
            CuaClientApplication item = iterator.next();
            if (!item.isStarred()) {
                application = item;
            }
        }
        iterator = form.getOtherApplications().iterator();
        while ((application == null) && iterator.hasNext()) {
            CuaClientApplication item = iterator.next();
            if (item.isStarred()) {
                application = item;
            }
        }

        if (application != null) {
            this.updateStar(portalControllerContext, form, application.getId(), application.isStarred());
        }

        Collections.sort(form.getStarredApplications(), this.applicationComparator);
        Collections.sort(form.getOtherApplications(), this.applicationComparator);
    }


    @Override
    public void saveSettings(PortalControllerContext portalControllerContext, CuaClientSettingsForm settingsForm) throws PortletException {
        // Catalog identifier
        String catalogId = settingsForm.getCatalogId();
        // Starred applications
        List<CuaClientApplication> starredApplications = settingsForm.getStarredApplications();
        // Other applications
        List<CuaClientApplication> otherApplications = settingsForm.getOtherApplications();

        // Identifiers
        String[] starredApplicationsIdentifiers = new String[starredApplications.size()];
        String[] applicationsIdentifiers = new String[starredApplications.size() + otherApplications.size()];
        int index = 0;

        for (CuaClientApplication application : starredApplications) {
            if (!application.isStarredOriginalValue()) {
                this.repository.setApplicationStarred(portalControllerContext, catalogId, application.getId(), true);
            }

            starredApplicationsIdentifiers[index] = application.getId();
            applicationsIdentifiers[index] = application.getId();
            index++;
        }

        for (CuaClientApplication application : otherApplications) {
            if (application.isStarredOriginalValue()) {
                this.repository.setApplicationStarred(portalControllerContext, catalogId, application.getId(), false);
            }

            applicationsIdentifiers[index] = application.getId();
            index++;
        }

        this.repository.reorderStarredApplications(portalControllerContext, catalogId, starredApplicationsIdentifiers);
        this.repository.reorderApplications(portalControllerContext, catalogId, applicationsIdentifiers);


        // Update form model
        CuaClientForm form = this.applicationContext.getBean(CuaClientForm.class);
        form.setPartiallyLoaded(false);
        form.setFullyLoaded(false);
    }


    /**
     * Get catalog identifier.
     *
     * @param portalControllerContext portal controller context
     * @return catalog identifier
     */
    private String getCatalogId(PortalControllerContext portalControllerContext) {
        // Portlet request
        PortletRequest request = portalControllerContext.getRequest();
        // Remote user
        String remoteUser = request.getRemoteUser();

        // Person
        ToutaticePerson person;
        if (StringUtils.isEmpty(remoteUser)) {
            person = null;
        } else {
            person = this.personService.getPerson(remoteUser);
        }

        // Catalog identifier
        String catalogId;
        if (person == null) {
            catalogId = null;
        } else {
            catalogId = "e9406cec-5937-4a1a-ae55-95432a56a9c2"; // FIXME
        }

        return catalogId;
    }


    /**
     * Synchronize catalog and created it if it doesn't exists.
     *
     * @param portalControllerContext portal controller context
     * @param catalogId               catalog identifier
     */
    private void synchronize(PortalControllerContext portalControllerContext, String catalogId) throws PortletException {
        // Portlet request
        PortletRequest request = portalControllerContext.getRequest();
        // Remote user
        String remoteUser = request.getRemoteUser();


        // Check if catalog exists
        boolean exists = this.repository.isCatalogExists(portalControllerContext, catalogId);
        if (!exists) {
            // Create catalog
            this.repository.createCatalog(portalControllerContext, catalogId);
        }


        // Synchronisation
        CuaSynchronization synchronization = this.repository.getSynchronization(portalControllerContext, catalogId);

        // Refresh synchronization indicator
        boolean refresh;
        if (CuaSynchronizationState.NEVER_SYNCHRONIZED.equals(synchronization.getState()) || CuaSynchronizationState.FAILED.equals(synchronization.getState())) {
            refresh = true;
        } else if (CuaSynchronizationState.DONE.equals(synchronization.getState())) {
            // Next not before
            Date nextNotBefore;
            if (StringUtils.isEmpty(synchronization.getNextNotBefore())) {
                nextNotBefore = null;
            } else {
                try {
                    nextNotBefore = this.dateFormat.parse(synchronization.getNextNotBefore());
                } catch (ParseException e) {
                    nextNotBefore = null;
                    this.log.warn(e.getLocalizedMessage());
                }
            }

            refresh = (nextNotBefore == null) || nextNotBefore.before(new Date());
        } else {
            refresh = false;
        }

        if (refresh) {
            // Identity vector
            CuaIdentityVector identityVector = this.applicationContext.getBean(CuaIdentityVector.class);
            identityVector.setToutaticeId(remoteUser);
            // TODO ARENA, GAR, ...

            // Synchronize
            this.repository.synchronize(portalControllerContext, catalogId, identityVector);

            // Wait
            synchronization = this.repository.getSynchronization(portalControllerContext, catalogId);
            while (CuaSynchronizationState.WAITING.equals(synchronization.getState()) || CuaSynchronizationState.IN_PROGRESS.equals(synchronization.getState())) {
                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                } catch (InterruptedException e) {
                    throw new PortletException(e);
                }

                synchronization = this.repository.getSynchronization(portalControllerContext, catalogId);
            }
        }
    }


    /**
     * Convert applications DTO to client applications.
     *
     * @param applications applications DTO
     * @return client applications
     */
    private List<CuaClientApplication> convert(CuaApplication[] applications) {
        List<CuaClientApplication> clientApplications;

        if (ArrayUtils.isEmpty(applications)) {
            clientApplications = null;
        } else {
            clientApplications = new ArrayList<>(applications.length);
            for (CuaApplication application : applications) {
                CuaClientApplication clientApplication = this.convert(application);
                clientApplications.add(clientApplication);
            }
        }

        return clientApplications;
    }


    /**
     * Convert application DTO to client application.
     *
     * @param application application DTO
     * @return client application
     */
    private CuaClientApplication convert(CuaApplication application) {
        // Hub metadata
        CuaHubMetadata hubMetadata = application.getHubMetadata();

        // Client application
        CuaClientApplication clientApplication = this.applicationContext.getBean(CuaClientApplication.class);

        // Identifier
        String id;
        if (hubMetadata == null) {
            id = null;
        } else {
            id = hubMetadata.getIdInterneCUA();
        }
        clientApplication.setId(id);

        // Title
        String title = StringUtils.join(application.getTitle(), " ");
        clientApplication.setTitle(title);

        // Description
        String description = application.getDescription();
        clientApplication.setDescription(description);

        // URL
        String url;
        if (StringUtils.isEmpty(application.getSource())) {
            url = null;
        } else {
            try {
                URL sourceUrl = new URL(application.getSource());
                url = sourceUrl.toString();
            } catch (MalformedURLException e) {
                url = null;
                this.log.debug(e.getLocalizedMessage());
            }
        }
        clientApplication.setUrl(url);

        // Starred indicator
        boolean starred;
        if (hubMetadata == null) {
            starred = false;
        } else {
            starred = hubMetadata.isFavori();
        }
        clientApplication.setStarred(starred);
        clientApplication.setStarredOriginalValue(starred);

        return clientApplication;
    }


    /**
     * Update application starred indicator.
     *
     * @param portalControllerContext portal controller context
     * @param form                    settings form
     * @param applicationId           application identifier
     * @param starred                 starred indicator
     */
    private void updateStar(PortalControllerContext portalControllerContext, CuaClientSettingsForm form, String applicationId, boolean starred) {
        // Source & target applications
        List<CuaClientApplication> sourceApplications;
        List<CuaClientApplication> targetApplications;
        if (starred) {
            sourceApplications = form.getOtherApplications();
            targetApplications = form.getStarredApplications();
        } else {
            sourceApplications = form.getStarredApplications();
            targetApplications = form.getOtherApplications();
        }

        // Find application
        CuaClientApplication application = null;
        Iterator<CuaClientApplication> iterator = sourceApplications.iterator();
        while ((application == null) && iterator.hasNext()) {
            CuaClientApplication item = iterator.next();
            if (StringUtils.equals(applicationId, item.getId())) {
                application = item;

                // Remove from source applications
                iterator.remove();
            }
        }

        if (application != null) {
            // Add to target applications
            targetApplications.add(application);

            // Update model
            application.setStarred(starred);
        }
    }

}
