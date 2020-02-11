package fr.toutatice.portail.acrennes.layout.selector.portlet.service;

import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorForm;
import org.osivia.portal.api.context.PortalControllerContext;

import javax.portlet.PortletException;

/**
 * Layout selector portlet service interface.
 *
 * @author Cédric Krommenhoek
 */
public interface LayoutSelectorService {

    /**
     * Get form.
     *
     * @param portalControllerContext portal controller context
     * @return form
     */
    LayoutSelectorForm getForm(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Select layout item.
     *
     * @param portalControllerContext portal controller context
     * @param form                    form
     * @param id                      layout item identifier
     */
    void select(PortalControllerContext portalControllerContext, LayoutSelectorForm form, String id) throws PortletException;


    void save(PortalControllerContext portalControllerContext) throws PortletException;

}
