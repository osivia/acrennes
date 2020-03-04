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
     * Partially loaded indicator.
     */
    private boolean partiallyLoaded;
    /**
     * Fully loaded indicator.
     */
    private boolean fullyLoaded;


    /**
     * Constructor.
     */
    public CuaClientForm() {
        super();
    }


    public boolean isPartiallyLoaded() {
        return partiallyLoaded;
    }

    public void setPartiallyLoaded(boolean partiallyLoaded) {
        this.partiallyLoaded = partiallyLoaded;
    }

    public boolean isFullyLoaded() {
        return fullyLoaded;
    }

    public void setFullyLoaded(boolean fullyLoaded) {
        this.fullyLoaded = fullyLoaded;
    }
}
