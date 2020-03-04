package fr.toutatice.portail.acrennes.layout.selector.portlet.controller;

import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorAdminForm;
import fr.toutatice.portail.acrennes.layout.selector.portlet.model.validation.LayoutSelectorAdminFormItemValidator;
import fr.toutatice.portail.acrennes.layout.selector.portlet.service.LayoutSelectorService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.math.NumberUtils;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import javax.portlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Layout selector portlet administration controller abstract super-class.
 *
 * @author Cédric Krommenhoek
 */
public abstract class AbstractLayoutSelectorAdminController {

    /**
     * Portlet context.
     */
    @Autowired
    private PortletContext portletContext;

    /**
     * Portlet service.
     */
    @Autowired
    private LayoutSelectorService service;

    /**
     * Layout item form validator.
     */
    @Autowired
    private LayoutSelectorAdminFormItemValidator validator;


    /**
     * Constructor.
     */
    public AbstractLayoutSelectorAdminController() {
        super();
    }


    /**
     * Load profiles resource mapping.
     *
     * @param request  resource request
     * @param response resource response
     * @param filter   search filter request parameter
     * @param page     search page request parameter
     */
    @ResourceMapping("load-profiles")
    public void loadProfiles(ResourceRequest request, ResourceResponse response, @RequestParam(name = "filter", required = false) String filter, @RequestParam(value = "page", required = false) String page) throws PortletException, IOException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        // Search results
        JSONObject results = this.service.loadProfiles(portalControllerContext, filter, NumberUtils.toInt(page, 1));

        // Content type
        response.setContentType("application/json");

        // Content
        PrintWriter printWriter = new PrintWriter(response.getPortletOutputStream());
        printWriter.write(results.toString());
        printWriter.close();
    }


    /**
     * Get administration form model attribute.
     *
     * @param request  portlet request
     * @param response portlet response
     * @return administration form
     */
    @ModelAttribute("adminForm")
    public LayoutSelectorAdminForm getForm(PortletRequest request, PortletResponse response) throws PortletException {
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(this.portletContext, request, response);

        return this.service.getAdminForm(portalControllerContext);
    }


    /**
     * Layout item form init binder.
     *
     * @param binder portlet request data binder
     */
    @InitBinder("itemForm")
    public void itemFormInitBinder(PortletRequestDataBinder binder) {
        binder.addValidators(this.validator);
    }

}
