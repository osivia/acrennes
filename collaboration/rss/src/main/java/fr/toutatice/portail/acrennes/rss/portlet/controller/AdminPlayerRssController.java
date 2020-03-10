package fr.toutatice.portail.acrennes.rss.portlet.controller;

import fr.toutatice.portail.acrennes.rss.portlet.model.RssView;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssWindowProperties;
import fr.toutatice.portail.acrennes.rss.portlet.service.ItemService;
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
import java.util.Arrays;
import java.util.List;

/**
 * Admin Template Rss controller.
 *
 * @author Frédéric Boudan
 */
@Controller
@RequestMapping("ADMIN")
@SessionAttributes("windowPropertiesForm")
public class AdminPlayerRssController {

    /**
     * Portlet context.
     */
    @Autowired
    private PortletContext portletContext;

    /**
     * Portlet service.
     */
    @Autowired
    private ItemService service;

    /**
     * Constructor.
     */
    public AdminPlayerRssController() {
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
     * Save window properties.
     *
     * @param request  action request
     * @param response action response
     * @param form form model attribute
	 * @param status session status
     */
    @ActionMapping(name = "submit", params = "save")
    public void save(ActionRequest request, ActionResponse response, @ModelAttribute("windowPropertiesForm") RssWindowProperties form, SessionStatus status)
            throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request,
                response);

        this.service.saveWindowProperties(portalControllerContext, form);

        status.setComplete();

        response.setWindowState(WindowState.NORMAL);
        response.setPortletMode(PortletMode.VIEW);
    }


    /**
     * Delete feed.
     *
     * @param request action request
     * @param response action response
	 * @param id feed identifier request parameter
     * @param form form model attribute
     */
    @ActionMapping(name = "submit", params = "del")
    public void del(ActionRequest request, ActionResponse response, @RequestParam("del") String id, @ModelAttribute("windowPropertiesForm") RssWindowProperties form) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        this.service.deleteFeed(portalControllerContext, form, id);
    }


    /**
     * Edit feed.
     *
     * @param request action request
     * @param response action response
	 * @param id feed identifier request parameter
     * @param form form model attribute
     */
    @ActionMapping(name = "submit", params = "edit")
    public void edit(ActionRequest request, ActionResponse response, @RequestParam("edit") String id, @ModelAttribute("windowPropertiesForm") RssWindowProperties form) {
        response.setRenderParameter("id", id);
        response.setRenderParameter("edit", "feed");
    }


    /**
     * Add feed.
     *
     * @param request action request
     * @param response action response
     * @param form form model attribute
     */
    @ActionMapping(name = "submit", params = "add")
    public void add(ActionRequest request, ActionResponse response, @ModelAttribute("windowPropertiesForm") RssWindowProperties form) {
        response.setRenderParameter("add", "feed");
    }


    /**
     * Reorder feed action mapping.
     *
     * @param request  action request
     * @param response action response
     * @param form     administration form model attribute
     */
    @ActionMapping(name = "submit", params = "reorder")
    public void reorder(ActionRequest request, ActionResponse response, @ModelAttribute("windowPropertiesForm") RssWindowProperties form) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        this.service.reorder(portalControllerContext, form);
    }
    
    
    /**
     * Get form model attribute.
     *
     * @param request  portlet request
     * @param response portlet response
     * @return form
     */
    @ModelAttribute("windowPropertiesForm")
    public RssWindowProperties getForm(PortletRequest request, PortletResponse response) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        return this.service.getWindowProperties(portalControllerContext);
    }


    @ModelAttribute("views")
    public List<RssView> getViews() {
        return Arrays.asList(RssView.values());
    }

}
