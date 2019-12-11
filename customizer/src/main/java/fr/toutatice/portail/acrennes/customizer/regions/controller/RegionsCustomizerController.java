package fr.toutatice.portail.acrennes.customizer.regions.controller;

import fr.toutatice.portail.acrennes.customizer.commons.service.CustomizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.portlet.PortletContext;

@Controller
public class RegionsCustomizerController {

    /**
     * Portlet context.
     */
    @Autowired
    private PortletContext portletContext;

    /**
     * Customizer service.
     */
    @Autowired
    private CustomizerService service;


    /**
     * Constructor.
     */
    public RegionsCustomizerController() {
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
