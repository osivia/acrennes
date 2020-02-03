package fr.toutatice.portail.acrennes.rss.portlet.controller;

import fr.toutatice.portail.acrennes.rss.portlet.model.RssPlayer;
import fr.toutatice.portail.acrennes.rss.portlet.service.ItemService;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.*;

/**
 * View Template Rss controller.
 *
 * @author Frédéric Boudan
 */
@Controller
@RequestMapping("VIEW")
public class ViewPlayerRssController {

    /**
     * Application context.
     */
    @Autowired
    public ApplicationContext applicationContext;

    /**
     * Portlet context.
     */
    @Autowired
    protected PortletContext portletContext;

    /**
     * Item RSS service.
     */
    @Autowired
    protected ItemService service;

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
    public String view(RenderRequest request, RenderResponse response) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        return this.service.getViewPath(portalControllerContext);
    }

    /**
     * Select feed action mapping.
     *
     * @param request  action request
     * @param response action response
     * @param id       selected feed identifier
     * @param player   RSS player model attribute
     */
    @ActionMapping("select")
    public void select(ActionRequest request, ActionResponse response, @RequestParam(name = "id", required = false) String id, @ModelAttribute("player") RssPlayer player) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        this.service.selectFeed(portalControllerContext, player, id);
    }


    /**
     * Get RSS player model attribute.
     *
     * @param request  portlet request
     * @param response portlet response
     * @return RSS player
     */
    @ModelAttribute("player")
    public RssPlayer getPlayer(PortletRequest request, PortletResponse response) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        return this.service.getPlayer(portalControllerContext);
    }

}
