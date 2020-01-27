package fr.toutatice.portail.acrennes.cua.client.portlet.model;

import org.osivia.portal.api.portlet.Refreshable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * CUA client form java-bean.
 *
 * @author Cédric Krommenhoek
 * @see AbstractCuaClientForm
 */
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
@Refreshable
public class CuaClientForm extends AbstractCuaClientForm {

    /**
     * Loaded indicator.
     */
    private boolean loaded;
    /**
     * Other applications loaded indicator.
     */
    private boolean otherApplicationsLoaded;


    /**
     * Constructor.
     */
    public CuaClientForm() {
        super();
    }


    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isOtherApplicationsLoaded() {
        return otherApplicationsLoaded;
    }

    public void setOtherApplicationsLoaded(boolean otherApplicationsLoaded) {
        this.otherApplicationsLoaded = otherApplicationsLoaded;
    }
}
