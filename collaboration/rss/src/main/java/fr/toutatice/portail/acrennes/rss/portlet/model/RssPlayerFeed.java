package fr.toutatice.portail.acrennes.rss.portlet.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * RSS player feed.
 *
 * @author Cédric Krommenhoek
 * @see RssFeed
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RssPlayerFeed implements RssFeed {

    /**
     * Feed identifier.
     */
    private String id;
    /**
     * Feed display name.
     */
    private String displayName;
    /**
     * Feed picture URL.
     */
    private String pictureUrl;
    /**
     * Feed items.
     */
    private List<RssPlayerFeedItem> items;


    /**
     * Constructor.
     */
    public RssPlayerFeed() {
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


    public List<RssPlayerFeedItem> getItems() {
        return items;
    }

    public void setItems(List<RssPlayerFeedItem> items) {
        this.items = items;
    }
}
