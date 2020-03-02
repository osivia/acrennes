package fr.toutatice.portail.acrennes.layout.selector.portlet.model;

import org.osivia.portal.api.portlet.Refreshable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * Layout selector administration form java-bean.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
@Refreshable
public class LayoutSelectorAdminForm {

    /**
     * Items.
     */
    private List<LayoutSelectorAdminFormItem> items;
    /**
     * Loaded indicator.
     */
    private boolean loaded;


    /**
     * Constructor.
     */
    public LayoutSelectorAdminForm() {
        super();
    }


    public List<LayoutSelectorAdminFormItem> getItems() {
        return items;
    }

    public void setItems(List<LayoutSelectorAdminFormItem> items) {
        this.items = items;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
