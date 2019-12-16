package fr.toutatice.portail.acrennes.cua.client.portlet.service;

import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaApplication;
import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaForm;
import fr.toutatice.portail.acrennes.cua.client.portlet.repository.CuaClientRepository;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.portlet.PortletException;
import java.util.SortedSet;

/**
 * CUA client portlet service implementation.
 *
 * @author Cédric Krommenhoek
 * @see CuaClientService
 */
@Service
public class CuaClientServiceImpl implements CuaClientService {

    /**
     * Application context.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Portlet repository.
     */
    @Autowired
    private CuaClientRepository repository;


    /**
     * Constructor.
     */
    public CuaClientServiceImpl() {
        super();
    }


    @Override
    public CuaForm getForm(PortalControllerContext portalControllerContext) throws PortletException {
        // Form
        CuaForm form = this.applicationContext.getBean(CuaForm.class);

        // CUA applications
        SortedSet<CuaApplication> applications = this.repository.getApplications(portalControllerContext);
        form.setApplications(applications);

        return form;
    }

}
