package fr.toutatice.portail.acrennes.rss.portlet.controller;

import fr.toutatice.portail.acrennes.rss.portlet.model.RssWindowProperties;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssWindowPropertiesFeed;
import fr.toutatice.portail.acrennes.rss.portlet.service.ItemService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.math.NumberUtils;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import javax.portlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Admin Template Rss controller.
 *
 * @author Frédéric Boudan
 */
@Controller
@RequestMapping(value = "ADMIN", params = "edit=feed")
@SessionAttributes("windowPropertiesForm")
public class EditFeedRssController {

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
    public EditFeedRssController() {
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
        return "edit-feed";
    }


    /**
     * Edit feed action mapping.
     *
     * @param request              action request
     * @param response             action response
     * @param feedForm             feed form model attribute
     * @param windowPropertiesForm window properties form model attribute
     */
    @ActionMapping("submit")
    public void edit(ActionRequest request, ActionResponse response, @ModelAttribute("feedForm") RssWindowPropertiesFeed feedForm, @ModelAttribute("windowPropertiesForm") RssWindowProperties windowPropertiesForm) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        this.service.editFeed(portalControllerContext, windowPropertiesForm, feedForm);
    }


    /**
     * Get feed form model attribute.
     *
     * @param request              portlet request
     * @param response             portlet response
     * @param id                   feed identifier request parameter
     * @param windowPropertiesForm window properties form model attribute
     * @return feed form
     */
    @ModelAttribute("feedForm")
    public RssWindowPropertiesFeed getFeedForm(PortletRequest request, PortletResponse response, @RequestParam("id") String id, @ModelAttribute("windowPropertiesForm") RssWindowProperties windowPropertiesForm) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        return this.service.getFeedForm(portalControllerContext, windowPropertiesForm, id);
    }


    /**
     * Get window properties form model attribute.
     *
     * @param request  portlet request
     * @param response portlet response
     * @return window properties form
     */
    @ModelAttribute("windowPropertiesForm")
    public RssWindowProperties getWindowPropertiesForm(PortletRequest request, PortletResponse response) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        return this.service.getWindowProperties(portalControllerContext);
    }


    @ResourceMapping("loadGroup")
    public void loadGroup(ResourceRequest request, ResourceResponse response, @RequestParam(name = "filter", required = false) String filter, @RequestParam(value = "page", required = false) String page) throws PortletException, IOException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        // Search results
        JSONObject results = this.service.searchGroups(portalControllerContext, filter, NumberUtils.toInt(page, 1));

        // Content type
        response.setContentType("application/json");

        // Content
        PrintWriter printWriter = new PrintWriter(response.getPortletOutputStream());
        printWriter.write(results.toString());
        printWriter.close();
    }

}
