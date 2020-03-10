package fr.toutatice.portail.acrennes.rss.portlet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * RSS window properties feed.
 *
 * @author Cédric Krommenhoek
 * @see RssFeed
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RssWindowPropertiesFeed implements RssFeed {

    @JsonProperty("id")
    private String id;

    @JsonIgnore
    private String displayName;

    @JsonIgnore
    private String pictureUrl;

    @JsonProperty("rights")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> rights;

    /**
     * Order.
     */
    @JsonIgnore
    private int order;
    
    /**
     * Constructor.
     */
    public RssWindowPropertiesFeed() {
        super();
    }


    @Override
    public String getId() {
        return this.id;
    }


    @Override
    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String getDisplayName() {
        return this.displayName;
    }


    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    @Override
    public String getPictureUrl() {
        return this.pictureUrl;
    }


    @Override
    public void setPictureUrl(String url) {
        this.pictureUrl = url;
    }


    public List<String> getRights() {
        return rights;
    }

    public void setRights(List<String> rights) {
        this.rights = rights;
    }


	public int getOrder() {
		return order;
	}


	public void setOrder(int order) {
		this.order = order;
	}
}
