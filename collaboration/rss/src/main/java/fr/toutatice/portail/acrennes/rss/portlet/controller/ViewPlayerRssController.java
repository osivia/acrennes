package fr.toutatice.portail.acrennes.rss.portlet.controller;

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
import org.osivia.portal.api.windows.PortalWindow;
import org.osivia.portal.api.windows.WindowFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import fr.toutatice.portail.acrennes.rss.portlet.model.ItemRssModel;
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
    public String view(RenderRequest request, RenderResponse response)
            throws PortletException {

        return "viewListe";
    }

    @ModelAttribute("items")
    public List<ItemRssModel> getItems(PortletRequest request, PortletResponse response) throws PortletException
    {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);
        List<ItemRssModel> items = this.service.getListItem(portalControllerContext);
        return items;
    }
    
	public void doAdmin(PortalControllerContext portalControllerContext) throws PortletException {
		// Request
		PortletRequest request = portalControllerContext.getRequest();

		// Current window
		PortalWindow window = WindowFactory.getWindow(request);
//		String nbItems = window.getProperty(LABEL_COOPERATIVE);
//		request.setAttribute("departementLabel", departementLabel);
//		String target = window.getProperty(TARGET);
//		request.setAttribute("target", target);
//		boolean flag = BooleanUtils.toBoolean(window.getProperty(FLAG_TRIANGLE));
//		request.setAttribute("flag", flag);
	}

	/**
	 * {@inheritDoc}
	 */
//	@Override
//	public void processAction(PortalControllerContext portalControllerContext) throws PortletException {
//		// Request
//		PortletRequest request = portalControllerContext.getRequest();
//
//		if ("admin".equals(request.getPortletMode().toString())
//				&& "save".equals(request.getParameter(ActionRequest.ACTION_NAME))) {
//			// Current window
//			PortalWindow window = WindowFactory.getWindow(request);
////			window.setProperty(LABEL_COOPERATIVE, StringUtils.trimToNull(request.getParameter("departementLabel")));
////			System.out.println("fred :" + request.getParameter("target"));
////			window.setProperty(TARGET, StringUtils.trimToNull(request.getParameter("target")));
////
////			boolean flag = BooleanUtils.toBoolean(request.getParameter("flag"));
////			window.setProperty(FLAG_TRIANGLE, String.valueOf(flag));
//
//		}
//	}
//
//	@Override
//	public boolean isDisplayedInAdmin() {
//		return true;
//	}    
}
