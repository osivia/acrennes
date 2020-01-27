package fr.toutatice.portail.acrennes.cua.client.portlet.controller;

import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaClientSettingsForm;
import fr.toutatice.portail.acrennes.cua.client.portlet.service.CuaClientService;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.*;

/**
 * CUA client settings portlet controller.
 *
 * @author Cédric Krommenhoek
 */
@Controller
@RequestMapping(path = "VIEW", params = "view=settings")
@SessionAttributes("settingsForm")
public class CuaClientSettingsController {

    /**
     * Portlet context.
     */
    @Autowired
    private PortletContext portletContext;

    /**
     * Portlet service.
     */
    @Autowired
    private CuaClientService service;


    /**
     * Constructor.
     */
    public CuaClientSettingsController() {
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
        return "settings";
    }


    /**
     * Add star action mapping.
     *
     * @param request       action request
     * @param response      action response
     * @param form          settings form model attribute
     * @param applicationId application identifier request parameter
     */
    @ActionMapping("add-star")
    public void addStar(ActionRequest request, ActionResponse response, @ModelAttribute("settingsForm") CuaClientSettingsForm form, @RequestParam("id") String applicationId) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        this.service.addStar(portalControllerContext, form, applicationId);

        // Copy render parameter
        this.copyRenderParameter(response);
    }


    /**
     * Remove star action mapping.
     *
     * @param request       action request
     * @param response      action response
     * @param form          settings form model attribute
     * @param applicationId application identifier request parameter
     */
    @ActionMapping("remove-star")
    public void removeStar(ActionRequest request, ActionResponse response, @ModelAttribute("settingsForm") CuaClientSettingsForm form, @RequestParam("id") String applicationId) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        this.service.removeStar(portalControllerContext, form, applicationId);

        // Copy render parameter
        this.copyRenderParameter(response);
    }


    /**
     * Reorder applications action mapping.
     *
     * @param request  action request
     * @param response action response
     * @param form     settings form model attribute
     */
    @ActionMapping(name = "submit", params = "reorder")
    public void reorder(ActionRequest request, ActionResponse response, @ModelAttribute("settingsForm") CuaClientSettingsForm form) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        this.service.reorder(portalControllerContext, form);

        // Copy render parameter
        this.copyRenderParameter(response);
    }


    /**
     * Cancel action mapping.
     *
     * @param request       action request
     * @param response      action response
     * @param sessionStatus session status
     */
    @ActionMapping(name = "submit", params = "cancel")
    public void cancel(ActionRequest request, ActionResponse response, SessionStatus sessionStatus) {
        sessionStatus.setComplete();
    }


    /**
     * Save action mapping.
     *
     * @param request       action request
     * @param response      action response
     * @param form          settings form model attribute
     * @param sessionStatus session status
     */
    @ActionMapping(name = "submit", params = "save")
    public void save(ActionRequest request, ActionResponse response, @ModelAttribute("settingsForm") CuaClientSettingsForm form, SessionStatus sessionStatus) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        this.service.saveSettings(portalControllerContext, form);

        sessionStatus.setComplete();
    }


    /**
     * Copy render parameter.
     *
     * @param response action response
     */
    private void copyRenderParameter(ActionResponse response) {
        response.setRenderParameter("view", "settings");
    }


    /**
     * Get settings form model attribute.
     *
     * @param request  portlet request
     * @param response portlet response
     * @return settings form
     */
    @ModelAttribute("settingsForm")
    public CuaClientSettingsForm getForm(PortletRequest request, PortletResponse response) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        return this.service.getSettingsForm(portalControllerContext);
    }

}
