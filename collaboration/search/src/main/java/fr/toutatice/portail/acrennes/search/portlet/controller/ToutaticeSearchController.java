package fr.toutatice.portail.acrennes.search.portlet.controller;

import fr.toutatice.portail.acrennes.search.portlet.model.ToutaticeSearchForm;
import fr.toutatice.portail.acrennes.search.portlet.service.ToutaticeSearchService;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.*;
import java.io.IOException;

/**
 * Toutatice search portlet controller.
 *
 * @author Cédric Krommenhoek
 */
@Controller
@RequestMapping("VIEW")
public class ToutaticeSearchController {

    /**
     * Portlet context.
     */
    @Autowired
    private PortletContext portletContext;

    /**
     * Portlet service.
     */
    @Autowired
    private ToutaticeSearchService service;


    /**
     * Constructor.
     */
    public ToutaticeSearchController() {
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
        return "view";
    }


    /**
     * Search action mapping.
     *
     * @param request  action request
     * @param response action response
     * @param form     search form model attribute
     */
    @ActionMapping("submit")
    public void search(ActionRequest request, ActionResponse response, @ModelAttribute("form") ToutaticeSearchForm form) throws PortletException, IOException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        // Redirection URL
        String redirectionUrl = this.service.search(portalControllerContext, form);
        response.sendRedirect(redirectionUrl);
    }


    /**
     * Get search form model attribute.
     *
     * @param request  portlet request
     * @param response portlet response
     * @return form
     */
    @ModelAttribute("form")
    public ToutaticeSearchForm getForm(PortletRequest request, PortletResponse response) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        return this.service.getForm(portalControllerContext);
    }

}
