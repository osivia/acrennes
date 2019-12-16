package fr.toutatice.portail.acrennes.cua.client.portlet.repository;

import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaApplication;
import fr.toutatice.portail.acrennes.cua.client.portlet.model.comparator.CuaApplicationsComparator;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.portlet.PortletException;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * CUA client portlet repository implementation.
 *
 * @author Cédric Krommenhoek
 * @see CuaClientRepository
 */
@Repository
public class CuaClientRepositoryImpl implements CuaClientRepository {

    /**
     * Application context.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * CUA applications comparator.
     */
    @Autowired
    private CuaApplicationsComparator applicationsComparator;


    public CuaClientRepositoryImpl() {
        super();
    }


    @Override
    public SortedSet<CuaApplication> getApplications(PortalControllerContext portalControllerContext) throws PortletException {
        // CUA applications
        SortedSet<CuaApplication> applications = new TreeSet<>(this.applicationsComparator);


        // Application
        // FIXME stub
        CuaApplication application = this.applicationContext.getBean(CuaApplication.class);
        application.setId("test");
        application.setTitle("Application de test");
        application.setDescription("Description de l'application de test");
        application.setUrl("http://www.example.com/test");
        application.setOrder(1);
        application.setDisabled(false);
        applications.add(application);


        return applications;
    }

}
