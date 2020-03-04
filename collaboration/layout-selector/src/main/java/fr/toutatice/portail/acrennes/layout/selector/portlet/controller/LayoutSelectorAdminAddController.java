package fr.toutatice.portail.acrennes.layout.selector.portlet.controller;

import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorAdminForm;
import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorAdminFormItem;
import fr.toutatice.portail.acrennes.layout.selector.portlet.service.LayoutSelectorService;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.*;

/**
 * Layout selector portlet administration add layout item controller.
 *
 * @author Cédric Krommenhoek
 * @see AbstractLayoutSelectorAdminController
 */
@Controller
@RequestMapping(path = "ADMIN", params = "view=add")
public class LayoutSelectorAdminAddController extends AbstractLayoutSelectorAdminController {

    /**
     * Portlet context.
     */
    @Autowired
    private PortletContext portletContext;

    /**
     * Portlet service.
     */
    @Autowired
    private LayoutSelectorService service;


    /**
     * Constructor.
     */
    public LayoutSelectorAdminAddController() {
        super();
    }


    /**
     * View render mapping.
     *
     * @param request  render request
     * @param response render response
     * @return view path
     */
    @RenderMapping
    public String view(RenderRequest request, RenderResponse response) {
        return "admin-add";
    }


    /**
     * Add layout item action mapping.
     *
     * @param request       action request
     * @param response      action response
     * @param itemForm      added layout item form model attribute
     * @param bindingResult binding result
     * @param adminForm     administration form model attribute
     */
    @ActionMapping("submit")
    public void add(ActionRequest request, ActionResponse response, @Validated @ModelAttribute("itemForm") LayoutSelectorAdminFormItem itemForm, BindingResult bindingResult, @ModelAttribute("adminForm") LayoutSelectorAdminForm adminForm) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        if (bindingResult.hasErrors()) {
            response.setRenderParameter("view", "add");
        } else {
            this.service.addItem(portalControllerContext, adminForm, itemForm);
        }
    }


    /**
     * Get added layout item form model attribute.
     *
     * @param request  portlet request
     * @param response portlet response
     * @return layout item form
     */
    @ModelAttribute("itemForm")
    public LayoutSelectorAdminFormItem getAddItemForm(PortletRequest request, PortletResponse response) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        return this.service.getAddedItemForm(portalControllerContext);
    }

}
