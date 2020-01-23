package fr.toutatice.portail.acrennes.cua.client.portlet.controller;

import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaClientForm;
import fr.toutatice.portail.acrennes.cua.client.portlet.service.CuaClientService;
import org.apache.commons.lang.StringUtils;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import javax.portlet.*;
import java.io.IOException;

/**
 * CUA client portlet controller.
 *
 * @author Cédric Krommenhoek
 */
@Controller
@RequestMapping("VIEW")
public class CuaClientController {

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
    public CuaClientController() {
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
        // Empty response
        if (StringUtils.isEmpty(request.getRemoteUser())) {
            request.setAttribute("osivia.emptyResponse", String.valueOf(1));
        }

        return "view";
    }


    /**
     * Get form model attribute.
     *
     * @param request  portlet request
     * @param response portlet response
     * @return form
     */
    @ModelAttribute("form")
    public CuaClientForm getForm(PortletRequest request, PortletResponse response) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        return this.service.getForm(portalControllerContext);
    }


    /**
     * Load CUA applications resource mapping.
     *
     * @param request  resource request
     * @param response resource response
     */
    @ResourceMapping("load")
    public void load(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        // Content type
        response.setContentType("text/html");

        this.service.loadStarredApplications(portalControllerContext);
    }


    /**
     * Load CUA other applications resource mapping.
     *
     * @param request  resource request
     * @param response resource response
     */
    @ResourceMapping("load-other-applications")
    public void loadOtherApplications(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        // Content type
        response.setContentType("text/html");

        this.service.loadOtherApplications(portalControllerContext);
    }


}
