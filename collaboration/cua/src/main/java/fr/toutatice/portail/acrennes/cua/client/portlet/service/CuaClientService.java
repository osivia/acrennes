package fr.toutatice.portail.acrennes.cua.client.portlet.service;

import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaClientForm;
import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaClientSettingsForm;
import org.osivia.portal.api.context.PortalControllerContext;

import javax.portlet.PortletException;
import java.io.IOException;

/**
 * CUA client portlet service interface.
 *
 * @author Cédric Krommenhoek
 */
public interface CuaClientService {

    /**
     * Portlet instance.
     */
    String PORTLET_INSTANCE = "toutatice-cua-client-instance";


    /**
     * Get form.
     *
     * @param portalControllerContext portal controller context
     * @return form
     */
    CuaClientForm getForm(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Load starred applications.
     *
     * @param portalControllerContext portal controller context
     */
    void loadStarredApplications(PortalControllerContext portalControllerContext) throws PortletException, IOException;


    /**
     * Load other applications.
     *
     * @param portalControllerContext portal controller context
     */
    void loadOtherApplications(PortalControllerContext portalControllerContext) throws PortletException, IOException;


    /**
     * Get settings form.
     *
     * @param portalControllerContext portal controller context
     * @return form
     */
    CuaClientSettingsForm getSettingsForm(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Add application to starred applications.
     *
     * @param portalControllerContext portal controller context
     * @param form                    settings form
     * @param applicationId           application identifier
     */
    void addStar(PortalControllerContext portalControllerContext, CuaClientSettingsForm form, String applicationId) throws PortletException;


    /**
     * Remove application from starred applications.
     *
     * @param portalControllerContext portal controller context
     * @param form                    settings form
     * @param applicationId           application identifier
     */
    void removeStar(PortalControllerContext portalControllerContext, CuaClientSettingsForm form, String applicationId) throws PortletException;


    /**
     * Reorder applications.
     *
     * @param portalControllerContext portal controller context
     * @param form                    settings form
     */
    void reorder(PortalControllerContext portalControllerContext, CuaClientSettingsForm form) throws PortletException;


    /**
     * Save settings.
     *
     * @param portalControllerContext portal controller context
     * @param form                    settings form
     */
    void saveSettings(PortalControllerContext portalControllerContext, CuaClientSettingsForm form) throws PortletException;

}
