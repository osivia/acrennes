package fr.toutatice.portail.acrennes.search.portlet.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Toutatice search form java-bean.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ToutaticeSearchForm {

    /** Search query. */
    private String query;


    /**
     * Constructor.
     */
    public ToutaticeSearchForm() {
        super();
    }


    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
