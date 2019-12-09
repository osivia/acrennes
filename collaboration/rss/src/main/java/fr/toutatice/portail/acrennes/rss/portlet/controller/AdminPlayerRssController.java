package fr.toutatice.portail.acrennes.rss.portlet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import org.apache.commons.lang.math.NumberUtils;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import fr.toutatice.portail.acrennes.rss.portlet.model.RssSettings;
import fr.toutatice.portail.acrennes.rss.portlet.service.ItemService;
import net.sf.json.JSONObject;

/**
 * Admin Template Rss controller.
 *
 * @author Frédéric Boudan
 */
@Controller
@RequestMapping(value = "ADMIN")
public class AdminPlayerRssController {

    /** Portlet context. */
    @Autowired
    private PortletContext portletContext;

    /** Portlet service. */
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
     * @param request render request
     * @param response render response
     * @return view path
     */
    @RenderMapping
    public String view(RenderRequest request, RenderResponse response) {
        return "admin";
    }


    /**
     * Save portlet settings action mapping.
     * @param request action request
     * @param response action response
     * @param settings portlet settings model attribute
     * @throws PortletException
     */
    @ActionMapping("save")
    public void save(ActionRequest request, ActionResponse response, @ModelAttribute("form") RssSettings settings) throws PortletException {
     // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);
        
        this.service.save(portalControllerContext, settings);
        
        response.setWindowState(WindowState.NORMAL);
        response.setPortletMode(PortletMode.VIEW);
    }

    /**
     * Search documents resource mapping.
     *
     * @param request  resource request
     * @param response resource response
     * @param filter   search filter
     * @param page     search pagination page number
     * @throws PortletException
     * @throws IOException
     */
    @ResourceMapping("search")
    public void search(ResourceRequest request, ResourceResponse response, @ModelAttribute("form") RssSettings form, @RequestParam(value = "filter", required = false) String filter, @RequestParam
            (value = "page", required = false) String page) throws PortletException, IOException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        // Search results
        JSONObject results = this.service.searchDocuments(portalControllerContext, filter, NumberUtils.toInt(page, 1));

        // Content type
        response.setContentType("application/json");

        // Content
        PrintWriter printWriter = new PrintWriter(response.getPortletOutputStream());
        printWriter.write(results.toString());
        printWriter.close();
    }
    
    /**
     * Get portlet settings model attribute.
     * 
     * @param request portlet request
     * @param response portlet response
     * @return portlet settings
     * @throws PortletException
     */
    @ModelAttribute("form")
    public RssSettings getSettings(PortletRequest request, PortletResponse response) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);
        
        return this.service.getSettings(portalControllerContext);
    }
}
