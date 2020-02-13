package fr.toutatice.portail.acrennes.layout.selector.portlet.model;

import org.osivia.portal.api.ui.layout.LayoutItem;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Layout selector administration form item java-bean.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LayoutSelectorAdminFormItem implements LayoutItem {

    /**
     * Identifier
     */
    private String id;
    /**
     * Label.
     */
    private String label;
    /**
     * Icon.
     */
    private String icon;
    /**
     * Profiles.
     */
    private List<String> profiles;
    /**
     * Order.
     */
    private int order;


    /**
     * Constructor.
     */
    public LayoutSelectorAdminFormItem() {
        super();
    }


    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }

    @Override
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public List<String> getProfiles() {
        return this.profiles;
    }

    public void setProfiles(List<String> profiles) {
        this.profiles = profiles;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
