package fr.toutatice.portail.acrennes.customizer.attributes.service;

import fr.toutatice.portail.acrennes.customizer.commons.service.CustomizerService;
import org.jboss.portal.core.model.portal.command.render.RenderPageCommand;
import org.jboss.portal.core.theme.PageRendition;

import java.util.Map;
import java.util.Set;

/**
 * Attributes customizer service interface.
 *
 * @author Cédric Krommenhoek
 * @see CustomizerService
 */
public interface AttributesCustomizerService extends CustomizerService {

    /**
     * SSO applications.
     */
    String SSO_APPLICATIONS = "toutatice.sso.applications";

    /**
     * RSS containers administration URL.
     */
    String RSS_CONTAINERS_ADMINISTRATION_URL = "toutatice.rss-containers.administration.url";


    /**
     * Get attribute names.
     *
     * @return attribute names
     */
    Set<String> getAttributeNames();


    /**
     * Fill attributes.
     *
     * @param renderPageCommand render page command
     * @param pageRendition     page rendition
     * @param attributes        attributes
     */
    void fill(RenderPageCommand renderPageCommand, PageRendition pageRendition, Map<String, Object> attributes);

}
