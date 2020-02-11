package fr.toutatice.portail.acrennes.layout.selector.portlet.configuration;

import org.osivia.portal.api.locator.Locator;
import org.osivia.portal.api.portlet.PortletAppUtils;
import org.osivia.portal.api.ui.layout.LayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.portlet.context.PortletConfigAware;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.portlet.PortletConfig;

/**
 * Layout selector portlet configuration.
 *
 * @author Cédric Krommenhoek
 * @see PortletConfigAware
 */
@Configuration
@ComponentScan(basePackages = "fr.toutatice.portail.acrennes.layout.selector.portlet")
public class LayoutSelectorConfiguration implements PortletConfigAware {

    /**
     * Application context.
     */
    @Autowired
    private ApplicationContext applicationContext;


    /**
     * Constructor.
     */
    public LayoutSelectorConfiguration() {
        super();
    }


    @Override
    public void setPortletConfig(PortletConfig portletConfig) {
        // Register portlet application context
        PortletAppUtils.registerApplication(portletConfig, this.applicationContext);
    }


    /**
     * Get view resolver.
     *
     * @return view resolver
     */
    @Bean
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setCache(true);
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }


    /**
     * Get message source.
     *
     * @return message source
     */
    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("layout-selector");
        return messageSource;
    }


    /**
     * Get layout service.
     *
     * @return layout service
     */
    @Bean
    public LayoutService getLayoutService() {
        return Locator.findMBean(LayoutService.class, LayoutService.MBEAN_NAME);
    }

}
