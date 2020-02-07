package fr.toutatice.portail.acrennes.customizer.attributes.configuration;

import org.osivia.portal.api.locator.Locator;
import org.osivia.portal.api.urls.IPortalUrlFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Attributes customizer configuration.
 *
 * @author Cédric Krommenhoek
 */
@Configuration
@ComponentScan(basePackages = "fr.toutatice.portail.acrennes.customizer.attributes")
public class AttributesCustomizerConfiguration {

    /**
     * Constructor.
     */
    public AttributesCustomizerConfiguration() {
        super();
    }


    /**
     * Get portal URL factory.
     *
     * @return portal URL factory
     */
    @Bean
    public IPortalUrlFactory getPortalUrlFactory() {
        return Locator.findMBean(IPortalUrlFactory.class, IPortalUrlFactory.MBEAN_NAME);
    }

}
