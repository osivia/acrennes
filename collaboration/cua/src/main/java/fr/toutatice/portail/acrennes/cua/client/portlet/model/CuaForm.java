package fr.toutatice.portail.acrennes.cua.client.portlet.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.SortedSet;

/**
 * CUA form java-bean.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CuaForm {

    /**
     * CUA applications.
     */
    private SortedSet<CuaApplication> applications;


    /**
     * Constructor.
     */
    public CuaForm() {
        super();
    }


    public SortedSet<CuaApplication> getApplications() {
        return applications;
    }

    public void setApplications(SortedSet<CuaApplication> applications) {
        this.applications = applications;
    }
}
