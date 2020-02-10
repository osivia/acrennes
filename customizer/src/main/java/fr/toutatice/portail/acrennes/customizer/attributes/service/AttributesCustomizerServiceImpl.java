package fr.toutatice.portail.acrennes.customizer.attributes.service;

import fr.toutatice.portail.acrennes.customizer.attributes.model.ToutaticeAttributesBundle;
import fr.toutatice.portail.acrennes.customizer.commons.service.CustomizerServiceImpl;
import fr.toutatice.portail.cms.nuxeo.api.services.NuxeoConnectionProperties;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.portal.common.invocation.Scope;
import org.jboss.portal.core.controller.ControllerContext;
import org.jboss.portal.core.model.portal.command.render.RenderPageCommand;
import org.jboss.portal.core.theme.PageRendition;
import org.jboss.portal.theme.impl.render.dynamic.DynaRenderOptions;
import org.osivia.portal.api.PortalException;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.customization.CustomizationContext;
import org.osivia.portal.api.theming.IAttributesBundle;
import org.osivia.portal.api.urls.IPortalUrlFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Attributes customizer service implementation.
 *
 * @author Cédric Krommenhoek
 * @see CustomizerServiceImpl
 */
@Service
public class AttributesCustomizerServiceImpl extends CustomizerServiceImpl implements AttributesCustomizerService {

    /**
     * Customizer name.
     */
    private static final String CUSTOMIZER_NAME = "toutatice.customizer.attributes";


    /**
     * Log.
     */
    private final Log log;

    /**
     * Attribute names.
     */
    private final Set<String> attributeNames;

    /** SSO applications. */
    private final List<String> ssoApplications;


    /**
     * Portal URL factory.
     */
    @Autowired
    private IPortalUrlFactory portalUrlFactory;

    /**
     * Attributes bundle.
     */
    @Autowired
    private ToutaticeAttributesBundle attributesBundle;


    /**
     * Constructor.
     */
    public AttributesCustomizerServiceImpl() {
        super();

        // Log
        this.log = LogFactory.getLog(this.getClass());

        // Attribute names
        this.attributeNames = new HashSet<>();
        this.attributeNames.add(SSO_APPLICATIONS);
        this.attributeNames.add(RSS_CONTAINERS_ADMINISTRATION_URL);

        // SSO applications
        this.ssoApplications = new ArrayList<>();
        this.ssoApplications.add("/toutatice-application-menuapplication-portail/calledLogout.jsp");
        this.ssoApplications.add("/wayf/Ctrl?action=logout");
        this.ssoApplications.add(System.getProperty("cas.logout"));
        this.ssoApplications.add(NuxeoConnectionProperties.getPublicBaseUri().toString() + "/logout");
    }


    @Override
    protected String getCustomizerName() {
        return CUSTOMIZER_NAME;
    }


    @Override
    protected String getCustomizerId() {
        return IAttributesBundle.CUSTOMIZER_ID;
    }


    @Override
    public void customize(CustomizationContext customizationContext) {
        Map<String, Object> attributes = customizationContext.getAttributes();
        String name = (String) attributes.get(IAttributesBundle.CUSTOMIZER_ATTRIBUTE_NAME);

        if (this.attributesBundle.getAttributeNames().contains(name)) {
            attributes.put(IAttributesBundle.CUSTOMIZER_ATTRIBUTE_RESULT, this.attributesBundle);
        }
    }


    @Override
    public Set<String> getAttributeNames() {
        return this.attributeNames;
    }


    @Override
    public void fill(RenderPageCommand renderPageCommand, PageRendition pageRendition, Map<String, Object> attributes) {
        // Controller context
        ControllerContext controllerContext = renderPageCommand.getControllerContext();
        // Portal controller context
        PortalControllerContext portalControllerContext = new PortalControllerContext(controllerContext);

        // Administrator indicator
        boolean admin = BooleanUtils.isTrue((Boolean) controllerContext.getAttribute(Scope.PRINCIPAL_SCOPE, "osivia.isAdmin"));


        // SSO applications
        attributes.put(SSO_APPLICATIONS, this.ssoApplications);


        if (admin) {
            // RSS containers administration URL
            this.setRssContainersAdministrationUrl(portalControllerContext, attributes);
        }
    }


    /**
     * Set RSS containers administration URL.
     *
     * @param portalControllerContext portal controller context
     * @param attributes              attributes
     */
    private void setRssContainersAdministrationUrl(PortalControllerContext portalControllerContext, Map<String, Object> attributes) {
        // Portlet instance
        String instance = "osivia-services-rss-container-instance";

        // Window properties
        Map<String, String> properties = new HashMap<>();
        properties.put("osivia.hideTitle", "1");
        properties.put(DynaRenderOptions.PARTIAL_REFRESH_ENABLED, String.valueOf(true));
        properties.put("osivia.ajaxLink", "1");


        // URL
        String url;
        try {
            url = this.portalUrlFactory.getStartPortletInNewPage(portalControllerContext, "admin", "Administration", instance, properties, null);
        } catch (PortalException e) {
            url = null;
            this.log.error(e.getLocalizedMessage());
        }

        attributes.put(RSS_CONTAINERS_ADMINISTRATION_URL, url);
    }

}
