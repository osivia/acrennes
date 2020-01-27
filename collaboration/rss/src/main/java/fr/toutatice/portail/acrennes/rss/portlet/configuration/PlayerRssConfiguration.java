package fr.toutatice.portail.acrennes.rss.portlet.configuration;

import fr.toutatice.portail.acrennes.directory.service.ToutaticeGroupService;
import fr.toutatice.portail.cms.nuxeo.api.CMSPortlet;
import fr.toutatice.portail.cms.nuxeo.api.services.dao.DocumentDAO;
import org.osivia.portal.api.directory.v2.DirServiceFactory;
import org.osivia.portal.api.internationalization.IBundleFactory;
import org.osivia.portal.api.internationalization.IInternationalizationService;
import org.osivia.portal.api.locator.Locator;
import org.osivia.portal.api.notifications.INotificationsService;
import org.osivia.portal.api.portlet.PortletAppUtils;
import org.osivia.portal.api.urls.IPortalUrlFactory;
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
import javax.portlet.PortletException;

@Configuration
@ComponentScan(basePackages = {"fr.toutatice.portail.acrennes.rss.portlet"})
public class PlayerRssConfiguration extends CMSPortlet implements PortletConfigAware {

    /**
     * Application context.
     */
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setCache(true);
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/playerRss/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean(name = {"messageSource"})
    public ResourceBundleMessageSource getMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("Resource");
        return messageSource;
    }

    @Bean
    public IBundleFactory getBundleFactory() {
        IInternationalizationService internationalizationService = (IInternationalizationService) Locator.findMBean(IInternationalizationService.class, "osivia:service=InternationalizationService");

        return internationalizationService.getBundleFactory(getClass().getClassLoader());
    }

    @Bean
    public INotificationsService getNotificationService() {
        return (INotificationsService) Locator.findMBean(INotificationsService.class, "osivia:service=NotificationsService");
    }

    @Override
    public void setPortletConfig(PortletConfig portletConfig) {
        try {
            super.init(portletConfig);
        } catch (PortletException e) {
            throw new RuntimeException(e);
        }
        PortletAppUtils.registerApplication(portletConfig, applicationContext);
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

    /**
     * Get document DAO.
     *
     * @return DAO
     */
    @Bean
    public DocumentDAO getDocumentDao() {
        return DocumentDAO.getInstance();
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
