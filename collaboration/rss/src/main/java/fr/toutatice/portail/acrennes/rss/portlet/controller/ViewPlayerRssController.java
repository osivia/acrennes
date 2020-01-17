package fr.toutatice.portail.acrennes.rss.portlet.controller;

import java.io.IOException;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.internationalization.IBundleFactory;
import org.osivia.portal.api.notifications.INotificationsService;
import org.osivia.portal.api.urls.IPortalUrlFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import fr.toutatice.portail.acrennes.rss.portlet.model.ItemRssModel;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssSettings;
import fr.toutatice.portail.acrennes.rss.portlet.service.ItemService;

/**
 * View Template Rss controller.
 *
 * @author Frédéric Boudan
 */
@Controller
@RequestMapping(value = "VIEW")
public class ViewPlayerRssController {

	/** Portlet context. */
	@Autowired
	protected PortletContext portletContext;

	/** Application context. */
	@Autowired
	public ApplicationContext applicationContext;

	/** Item RSS service. */
	@Autowired
	protected ItemService service;

	/** Bundle factory. */
	@Autowired
	protected IBundleFactory bundleFactory;

	/** Notifications service. */
	@Autowired
	protected INotificationsService notificationsService;
	
    /** Portal URL factory. */
    @Autowired
    private IPortalUrlFactory urlFactory;

    /** View slider. */
    public static final String VIEW_SLIDER = "slider";
    
    /** View slider. */
    public static final String VIEW_LISTE = "liste";    
    
    /** View Liste. */
    private static final String VIEW_LISTE_JSP = "view-liste";
    
    /** View Liste. */
    private static final String VIEW_SLIDER_JSP = "view-slider";    
	
	/**
	 * Constructor.
	 */
	public ViewPlayerRssController() {
		super();
	}

	/**
	 * View container render mapping
	 *
	 * @param request  render request
	 * @param response render response
	 * @throws PortletException
	 */
	@RenderMapping
	public String view(RenderRequest request, RenderResponse response, @ModelAttribute("settings") RssSettings settings)
			throws PortletException {

		String view = VIEW_SLIDER_JSP;
		
		if (settings.getViewRss() != null && !settings.getViewRss().isEmpty()) {
			if (settings.getViewRss().equalsIgnoreCase(VIEW_LISTE)) {
				view = VIEW_LISTE_JSP;
			} 
		}

		return view;
	}

	/**
	 * View partner: Slider View for all partner, partner 1 to n.
	 * 
	 * @param request  action request
	 * @param response action response
	 * @param settings portlet settings model attribute
	 * @throws PortletException
	 * @throws IOException
	 */
	@ActionMapping(value = "viewPart") 
	public void viewPart(ActionRequest request, ActionResponse response, @RequestParam(value = "index", required = true) int index, @RequestParam(value = "part", required = true) String part, SessionStatus status)
			throws PortletException, IOException {

		// Portal controller context
		PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request,
				response);

		this.service.viewPart(portalControllerContext, index, part);
        // Redirect to refresh the page
        response.sendRedirect(this.urlFactory.getRefreshPageUrl(portalControllerContext));
		
	}
	
	@ModelAttribute("settings")
	public RssSettings getSettings(PortletRequest request, PortletResponse response)
			throws PortletException, IOException {
		// Portal controller context
		PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request,
				response);
		return this.service.getSettings(portalControllerContext);
	}
	
	@ModelAttribute("items")
	public List<ItemRssModel> getItems(PortletRequest request, PortletResponse response)
			throws PortletException, IOException {
		// Portal controller context
		PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request,
				response);
		return this.service.getListItem(portalControllerContext);
	}

}
