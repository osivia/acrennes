package fr.toutatice.portail.acrennes.cua.client.portlet.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * CUA client applications container.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CuaClientApplicationsContainer {

    /**
     * Starred applications.
     */
    private List<CuaClientApplication> starredApplications;


    /**
     * Constructor.
     */
    public CuaClientApplicationsContainer() {
        super();
    }


    public List<CuaClientApplication> getStarredApplications() {
        return starredApplications;
    }

    public void setStarredApplications(List<CuaClientApplication> starredApplications) {
        this.starredApplications = starredApplications;
    }
}
