package fr.toutatice.portail.acrennes.cua.client.portlet.repository;

import fr.toutatice.portail.acrennes.cua.client.portlet.model.dto.CuaApplication;
import fr.toutatice.portail.acrennes.cua.client.portlet.model.dto.CuaIdentityVector;
import fr.toutatice.portail.acrennes.cua.client.portlet.model.dto.CuaSynchronization;
import org.osivia.portal.api.context.PortalControllerContext;

import javax.portlet.PortletException;

/**
 * CUA client portlet repository interface.
 *
 * @author Cédric Krommenhoek
 */
public interface CuaClientRepository {

    /**
     * CUA base path.
     */
    String CUA_BASE_PATH = "/cua-hub";


    /**
     * Check if the calalog exists.
     *
     * @param portalControllerContext portal controller context
     * @param catalogId               catalog identifier
     * @return true if the catalog exists
     */
    boolean isCatalogExists(PortalControllerContext portalControllerContext, String catalogId) throws PortletException;


    /**
     * Create catalog.
     *
     * @param portalControllerContext portal controller context
     * @param catalogId               catalog identifier
     */
    void createCatalog(PortalControllerContext portalControllerContext, String catalogId) throws PortletException;


    /**
     * Get catalog applications.
     *
     * @param portalControllerContext portal controller context
     * @param catalogId               catalog identifier
     * @return applications
     */
    CuaApplication[] getApplications(PortalControllerContext portalControllerContext, String catalogId) throws PortletException;


    /**
     * Get catalog applications identifiers.
     *
     * @param portalControllerContext portal controller context
     * @param catalogId               catalog identifier
     * @return applications identifiers
     */
    String[] getApplicationsIdentifiers(PortalControllerContext portalControllerContext, String catalogId) throws PortletException;


    /**
     * Reorder catalog applications.
     *
     * @param portalControllerContext portal controller context
     * @param catalogId               catalog identifier
     * @param applicationsIds         applications identifiers
     */
    void reorderApplications(PortalControllerContext portalControllerContext, String catalogId, String[] applicationsIds) throws PortletException;


    /**
     * Check if application is starred.
     *
     * @param portalControllerContext portal controller context
     * @param catalogId               catalog identifier
     * @param applicationId           application identifier
     * @return true if application is starred
     */
    boolean isApplicationStarred(PortalControllerContext portalControllerContext, String catalogId, String applicationId) throws PortletException;


    /**
     * Set application starred indicator.
     *
     * @param portalControllerContext portal controller context
     * @param catalogId               catalog identifier
     * @param applicationId           application identifier
     * @param starred                 starred application indicator
     */
    void setApplicationStarred(PortalControllerContext portalControllerContext, String catalogId, String applicationId, boolean starred) throws PortletException;


    /**
     * Get catalog starred applications.
     *
     * @param portalControllerContext portal controller context
     * @param catalogId               catalog identifier
     * @return applications
     */
    CuaApplication[] getStarredApplications(PortalControllerContext portalControllerContext, String catalogId) throws PortletException;


    /**
     * Get catalog starred applications identifiers.
     *
     * @param portalControllerContext portal controller context
     * @param catalogId               catalog identifier
     * @return applications identifiers
     */
    String[] getStarredApplicationsIdentifiers(PortalControllerContext portalControllerContext, String catalogId) throws PortletException;


    /**
     * Reorder catalog starred applications.
     *
     * @param portalControllerContext portal controller context
     * @param catalogId               catalog identifier
     * @param applicationsIds         applications identifiers
     */
    void reorderStarredApplications(PortalControllerContext portalControllerContext, String catalogId, String[] applicationsIds) throws PortletException;


    /**
     * Get catalog synchronization.
     *
     * @param portalControllerContext portal controller context
     * @param catalogId               catalog identifier
     * @return synchronization
     */
    CuaSynchronization getSynchronization(PortalControllerContext portalControllerContext, String catalogId) throws PortletException;


    /**
     * Synchronize catalog.
     *
     * @param portalControllerContext portal controller context
     * @param catalogId               catalog identifier
     * @param identityVector          identity vector
     */
    void synchronize(PortalControllerContext portalControllerContext, String catalogId, CuaIdentityVector identityVector) throws PortletException;

}
