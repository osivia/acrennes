package fr.toutatice.portail.acrennes.search.portlet.service;

import fr.toutatice.portail.acrennes.search.portlet.model.ToutaticeSearchForm;
import org.osivia.portal.api.PortalException;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.urls.IPortalUrlFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.portlet.PortletException;

/**
 * Toutatice search portlet service implementation.
 *
 * @author Cédric Krommenhoek
 * @see ToutaticeSearchService
 */
@Service
public class ToutaticeSearchServiceImpl implements ToutaticeSearchService {

    /**
     * Application context.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Portal URL factory.
     */
    @Autowired
    private IPortalUrlFactory portalUrlFactory;


    /**
     * Constructor.
     */
    public ToutaticeSearchServiceImpl() {
        super();
    }


    @Override
    public ToutaticeSearchForm getForm(PortalControllerContext portalControllerContext) {
        return this.applicationContext.getBean(ToutaticeSearchForm.class);
    }


    @Override
    public String search(PortalControllerContext portalControllerContext, ToutaticeSearchForm form) throws PortletException {
        // Search redirection URL
        String url;

        try {
            url = this.portalUrlFactory.getAdvancedSearchUrl(portalControllerContext, form.getQuery(), false);
        } catch (PortalException e) {
            throw new PortletException(e);
        }

        return url;
    }

}
