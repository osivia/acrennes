package fr.toutatice.portail.acrennes.customizer.commons.controller;

import fr.toutatice.portail.acrennes.customizer.commons.service.CustomizerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Customizer controller.
 *
 * @author Cédric Krommenhoek
 */
public abstract class CustomizerController {

    /**
     * Customizer service.
     */
    @Autowired
    private CustomizerService service;


    /**
     * Constructor.
     */
    public CustomizerController() {
        super();
    }


    /**
     * Post-construct.
     */
    @PostConstruct
    public void postConstruct() {
        this.service.register();
    }


    /**
     * Pre-destroy.
     */
    @PreDestroy
    public void preDestroy() {
        this.service.unregister();
    }

}
