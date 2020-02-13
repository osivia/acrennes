package fr.toutatice.portail.acrennes.layout.selector.portlet.controller;

import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorAdminForm;
import fr.toutatice.portail.acrennes.layout.selector.portlet.service.LayoutSelectorService;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.*;

/**
 * Layout selector portlet administration controller.
 *
 * @author Cédric Krommenhoek
 */
@Controller
@RequestMapping("ADMIN")
@SessionAttributes("adminForm")
public class LayoutSelectorAdminController {

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
    public LayoutSelectorAdminController() {
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
        return "admin";
    }


    /**
     * Reorder action mapping.
     *
     * @param request  action request
     * @param response action response
     * @param form     administration form model attribute
     */
    @ActionMapping(name = "submit", params = "reorder")
    public void reorder(ActionRequest request, ActionResponse response, @ModelAttribute("adminForm") LayoutSelectorAdminForm form) {
        // TODO
    }


    /**
     * Cancel action mapping.
     *
     * @param request       action request
     * @param response      action response
     * @param form          administration form model attribute
     * @param sessionStatus session status
     */
    @ActionMapping(name = "submit", params = "cancel")
    public void cancel(ActionRequest request, ActionResponse response, @ModelAttribute("adminForm") LayoutSelectorAdminForm form, SessionStatus sessionStatus) throws PortletException {
        sessionStatus.setComplete();

        response.setWindowState(WindowState.NORMAL);
        response.setPortletMode(PortletMode.VIEW);
    }


    /**
     * Save action mapping.
     *
     * @param request       action request
     * @param response      action response
     * @param form          administration form model attribute
     * @param sessionStatus session status
     */
    @ActionMapping(name = "submit", params = "save")
    public void save(ActionRequest request, ActionResponse response, @ModelAttribute("adminForm") LayoutSelectorAdminForm form, SessionStatus sessionStatus) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        this.service.saveAdministration(portalControllerContext, form);

        sessionStatus.setComplete();

        response.setWindowState(WindowState.NORMAL);
        response.setPortletMode(PortletMode.VIEW);
    }


    /**
     * Get administration form model attribute.
     *
     * @param request  portlet request
     * @param response portlet response
     * @return administration form
     */
    @ModelAttribute("adminForm")
    public LayoutSelectorAdminForm getForm(PortletRequest request, PortletResponse response) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        return this.service.getAdminForm(portalControllerContext);
    }

}
