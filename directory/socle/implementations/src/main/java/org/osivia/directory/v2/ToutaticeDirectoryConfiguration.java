package org.osivia.directory.v2;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Toutatice directory configuration.
 *
 * @author Cédric Krommenhoek
 * @see AppConfig
 */
@Configuration
@Primary
@EnableTransactionManagement
@ComponentScan(basePackages = "fr.toutatice.portail.acrennes.directory")
public class ToutaticeDirectoryConfiguration extends AppConfig {

    /**
     * Constructor.
     */
    public ToutaticeDirectoryConfiguration() {
        super();
    }

}
