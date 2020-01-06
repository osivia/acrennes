package fr.toutatice.portail.acrennes.rss.portlet.controller;

import java.io.IOException;
import java.util.List;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.internationalization.IBundleFactory;
import org.osivia.portal.api.notifications.INotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
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
@SessionAttributes(value= "ItemRssModel")
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
    
    /**
     * Constructor.
     */
    public ViewPlayerRssController() {
        super();
    }


    /**
     * View container render mapping
     *
     * @param request render request
     * @param response render response
     * @throws PortletException
     */
    @RenderMapping
    public String view(RenderRequest request, RenderResponse response, @ModelAttribute("settings") RssSettings settings)
            throws PortletException {
		
		String view = "viewListe";
		if (settings.getViewRss() != null && !settings.getViewRss().isEmpty()) {
			if(settings.getViewRss().equalsIgnoreCase("liste")) {
				view = "viewListe";
			} else {
				view = "viewSlider";			
			}			
		}

        return view;
    }

    @ModelAttribute("items")
    public List<ItemRssModel> getItems(PortletRequest request, PortletResponse response) throws PortletException, IOException
    {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);
        return this.service.getListItem(portalControllerContext);
    }
    
    @ModelAttribute("settings")
    public RssSettings getSettings(PortletRequest request, PortletResponse response) throws PortletException, IOException
    {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);
		return this.service.getSettings(portalControllerContext);
    }    
    
}
