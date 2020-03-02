package fr.toutatice.portail.acrennes.layout.selector.portlet.service;

import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorAdminForm;
import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorAdminFormItem;
import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorForm;
import net.sf.json.JSONObject;
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


    /**
     * Get administration form.
     *
     * @param portalControllerContext portal controller context
     * @return administration form
     */
    LayoutSelectorAdminForm getAdminForm(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Reorder layout items.
     *
     * @param portalControllerContext portal controller context
     * @param form                    administration form
     */
    void reorder(PortalControllerContext portalControllerContext, LayoutSelectorAdminForm form) throws PortletException;


    /**
     * Remove layout item.
     *
     * @param portalControllerContext portal controller context
     * @param form                    administration form
     * @param id                      layout item identifier
     */
    void remove(PortalControllerContext portalControllerContext, LayoutSelectorAdminForm form, String id) throws PortletException;


    /**
     * Save administration.
     *
     * @param portalControllerContext portal controller context
     * @param form                    administration form
     */
    void saveAdministration(PortalControllerContext portalControllerContext, LayoutSelectorAdminForm form) throws PortletException;


    /**
     * Get added layout item form.
     *
     * @param portalControllerContext portal controller context
     * @return layout item form
     */
    LayoutSelectorAdminFormItem getAddedItemForm(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Add layout item.
     *
     * @param portalControllerContext portal controller context
     * @param adminForm               administration form
     * @param itemForm                added layout item form
     */
    void addItem(PortalControllerContext portalControllerContext, LayoutSelectorAdminForm adminForm, LayoutSelectorAdminFormItem itemForm) throws PortletException;


    /**
     * Get edited layout item form.
     *
     * @param portalControllerContext portal controller context
     * @param id                      layout item identifier
     * @return layout item form
     */
    LayoutSelectorAdminFormItem getEditedItemForm(PortalControllerContext portalControllerContext, String id) throws PortletException;


    /**
     * Edit layout item
     *
     * @param portalControllerContext portal controller context
     * @param adminForm               administration form
     * @param itemForm                edited layout item form
     */
    void editItem(PortalControllerContext portalControllerContext, LayoutSelectorAdminForm adminForm, LayoutSelectorAdminFormItem itemForm) throws PortletException;


    /**
     * Load profiles.
     *
     * @param portalControllerContext portal controller context
     * @param filter                  search filter
     * @param page                    search page
     * @return search result JSON object
     */
    JSONObject loadProfiles(PortalControllerContext portalControllerContext, String filter, int page) throws PortletException;

}
