package fr.toutatice.portail.acrennes.layout.selector.portlet.model;

import org.osivia.portal.api.ui.layout.LayoutItem;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Layout selector form java-bean.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LayoutSelectorForm {

    /**
     * Layout items.
     */
    private List<LayoutItem> items;


    /**
     * Constructor.
     */
    public LayoutSelectorForm() {
        super();
    }


    public List<LayoutItem> getItems() {
        return items;
    }

    public void setItems(List<LayoutItem> items) {
        this.items = items;
    }

}
