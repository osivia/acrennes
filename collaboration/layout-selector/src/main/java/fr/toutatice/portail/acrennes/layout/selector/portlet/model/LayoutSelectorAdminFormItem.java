package fr.toutatice.portail.acrennes.layout.selector.portlet.model;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osivia.portal.api.ui.layout.LayoutItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Layout selector administration form item java-bean.
 *
 * @author Cédric Krommenhoek
 * @see LayoutItem
 * @see Cloneable
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LayoutSelectorAdminFormItem implements LayoutItem, Cloneable {

    /**
     * Log.
     */
    private final Log log;


    /**
     * Application context.
     */
    @Autowired
    private ApplicationContext applicationContext;


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

        // Log
        this.log = LogFactory.getLog(this.getClass());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LayoutSelectorAdminFormItem that = (LayoutSelectorAdminFormItem) o;

        return id.equals(that.id);
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }


    @Override
    public LayoutSelectorAdminFormItem clone() {
        LayoutSelectorAdminFormItem clone = this.applicationContext.getBean(LayoutSelectorAdminFormItem.class);
        try {
            BeanUtils.copyProperties(clone, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            this.log.error(e.getLocalizedMessage());
        }
        return clone;
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
