package fr.toutatice.portail.acrennes.layout.selector.portlet.configuration;

import fr.toutatice.portail.acrennes.directory.service.ToutaticeGroupService;
import org.osivia.portal.api.directory.v2.DirServiceFactory;
import org.osivia.portal.api.internationalization.IBundleFactory;
import org.osivia.portal.api.internationalization.IInternationalizationService;
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
        messageSource.setBasenames("layout-selector", "layout-selector-validation");
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


    /**
     * Get internationalization bundle factory.
     *
     * @return internationalization bundle factory
     */
    @Bean
    public IBundleFactory getBundleFactory() {
        IInternationalizationService internationalizationService = Locator.findMBean(IInternationalizationService.class, IInternationalizationService.MBEAN_NAME);
        return internationalizationService.getBundleFactory(this.getClass().getClassLoader(), this.applicationContext);
    }


    /**
     * Get group service.
     *
     * @return group service
     */
    @Bean
    public ToutaticeGroupService getGroupService() {
        return DirServiceFactory.getService(ToutaticeGroupService.class);
    }

}
