package fr.toutatice.portail.acrennes.layout.selector.portlet.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Layout selector administration form java-bean.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LayoutSelectorAdminForm {

    /**
     * Items.
     */
    private List<LayoutSelectorAdminFormItem> items;


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
}
