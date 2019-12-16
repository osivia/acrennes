package fr.toutatice.portail.acrennes.cua.client.portlet.service;

import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaForm;
import org.osivia.portal.api.context.PortalControllerContext;

import javax.portlet.PortletException;

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
    CuaForm getForm(PortalControllerContext portalControllerContext) throws PortletException;

}
