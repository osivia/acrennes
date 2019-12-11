package fr.toutatice.portail.acrennes.customizer.regions.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Regions customizer configuration.
 *
 * @author Cédric Krommenhoek
 */
@Configuration
@ComponentScan(basePackages = "fr.toutatice.portail.acrennes.customizer.regions")
public class RegionsCustomizerConfiguration {

    /**
     * Constructor.
     */
    public RegionsCustomizerConfiguration() {
        super();
    }

}
