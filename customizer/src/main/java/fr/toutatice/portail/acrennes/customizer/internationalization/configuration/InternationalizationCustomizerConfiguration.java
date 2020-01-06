package fr.toutatice.portail.acrennes.customizer.internationalization.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Internationalization customizer configuration.
 *
 * @author Cédric Krommenhoek
 */
@Configuration
@ComponentScan(basePackages = "fr.toutatice.portail.acrennes.customizer.internationalization")
public class InternationalizationCustomizerConfiguration {

    /**
     * Constructor.
     */
    public InternationalizationCustomizerConfiguration() {
        super();
    }


    /**
     * Get message source.
     *
     * @return message source
     */
    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("customizer", "toolbar");
        return messageSource;
    }

}
