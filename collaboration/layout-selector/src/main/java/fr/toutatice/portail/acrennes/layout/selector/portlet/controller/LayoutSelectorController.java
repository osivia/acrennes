package fr.toutatice.portail.acrennes.layout.selector.portlet.controller;

import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorForm;
import fr.toutatice.portail.acrennes.layout.selector.portlet.service.LayoutSelectorService;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.*;

/**
 * Layout selector portlet controller.
 *
 * @author Cédric Krommenhoek
 */
@Controller
@RequestMapping("VIEW")
public class LayoutSelectorController {

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
    public LayoutSelectorController() {
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
        return "view";
    }


    /**
     * Select action mapping.
     *
     * @param request  action request
     * @param response action response
     * @param id       selected layout item identifier request parameter
     * @param form     form model attribute
     */
    @ActionMapping("select")
    public void select(ActionRequest request, ActionResponse response, @RequestParam("id") String id, @ModelAttribute("form") LayoutSelectorForm form) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        this.service.select(portalControllerContext, form, id);
    }


    /**
     * Get form model attribute.
     *
     * @param request  portlet request
     * @param response portlet response
     * @return form
     */
    @ModelAttribute("form")
    public LayoutSelectorForm getForm(PortletRequest request, PortletResponse response) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        return this.service.getForm(portalControllerContext);
    }

}
