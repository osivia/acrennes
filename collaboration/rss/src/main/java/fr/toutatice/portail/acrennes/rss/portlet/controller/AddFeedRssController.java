package fr.toutatice.portail.acrennes.rss.portlet.controller;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import fr.toutatice.portail.acrennes.rss.portlet.model.RssSettings;
import fr.toutatice.portail.acrennes.rss.portlet.service.ItemService;

/**
 * Admin Template Rss controller.
 *
 * @author Frédéric Boudan
 */
@Controller
@RequestMapping(value = "ADMIN", params="add=feed")
public class AddFeedRssController {

    /** Portlet context. */
    @Autowired
    private PortletContext portletContext;

    /** Portlet service. */
    @Autowired
    private ItemService service;


    /**
     * Constructor.
     */
    public AddFeedRssController() {
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
        return "add-feed";
    }


    /**
     * Save portlet settings action mapping.
     * @param request action request
     * @param response action response
     * @param settings portlet settings model attribute
     * @throws PortletException
     */
    @ActionMapping(value="add")
    public void add(ActionRequest request, ActionResponse response, @ModelAttribute("form") RssSettings settings, SessionStatus status) throws PortletException {
		// Portal controller context
		PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request,
				response);

		this.service.save(portalControllerContext, settings);

		response.setWindowState(WindowState.NORMAL);
	}

    /**
     * Get portlet settings model attribute.
     * 
     * @param request portlet request
     * @param response portlet response
     * @return portlet settings
     * @throws PortletException
     * @throws IOException 
     */
    @ModelAttribute("form")
    public RssSettings getSettings(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);
        
        return this.service.getList(portalControllerContext);
    }
}
