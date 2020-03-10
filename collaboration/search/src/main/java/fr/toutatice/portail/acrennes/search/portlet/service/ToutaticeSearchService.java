package fr.toutatice.portail.acrennes.search.portlet.service;

import fr.toutatice.portail.acrennes.search.portlet.model.ToutaticeSearchForm;
import org.osivia.portal.api.context.PortalControllerContext;

import javax.portlet.PortletException;

/**
 * Toutatice search portlet service interface.
 *
 * @author Cédric Krommenhoek
 */
public interface ToutaticeSearchService {

    /**
     * Get search form.
     *
     * @param portalControllerContext portal controller context
     * @return form
     */
    ToutaticeSearchForm getForm(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Search.
     *
     * @param portalControllerContext portal controller context
     * @param form                    search form
     * @return search redirection URL
     */
    String search(PortalControllerContext portalControllerContext, ToutaticeSearchForm form) throws PortletException;

}
