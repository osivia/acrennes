package fr.toutatice.portail.acrennes.cua.client.portlet.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * CUA client application.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CuaClientApplication {

    /**
     * Identifier.
     */
    private String id;
    /**
     * Order.
     */
    private int order;
    /**
     * Title.
     */
    private String title;
    /**
     * Description.
     */
    private String description;
    /**
     * URL.
     */
    private String url;
    /**
     * Starred indicator.
     */
    private boolean starred;
    /**
     * Starred indicator original value.
     */
    private boolean starredOriginalValue;


    /**
     * Constructor.
     */
    public CuaClientApplication() {
        super();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CuaClientApplication that = (CuaClientApplication) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public boolean isStarredOriginalValue() {
        return starredOriginalValue;
    }

    public void setStarredOriginalValue(boolean starredOriginalValue) {
        this.starredOriginalValue = starredOriginalValue;
    }
}
