package fr.toutatice.portail.acrennes.rss.portlet.model;

import org.osivia.portal.api.portlet.Refreshable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Set;

/**
 * RSS player.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
@Refreshable
public class RssPlayer {

    /**
     * Loaded indicator.
     */
    private boolean loaded;
    /**
     * Anonymous user indicator.
     */
    private boolean anonymous;
    /**
     * Feeds.
     */
    private List<RssPlayerFeed> feeds;
    /**
     * Selected feed identifier.
     */
    private String selectedId;
    /**
     * Active feeds.
     */
    private Set<String> activeFeeds;
    /**
     * Displayed items.
     */
    private List<RssPlayerFeedItem> displayedItems;


    /**
     * Constructor.
     */
    public RssPlayer() {
        super();
    }


    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public List<RssPlayerFeed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<RssPlayerFeed> feeds) {
        this.feeds = feeds;
    }

    public String getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(String selectedId) {
        this.selectedId = selectedId;
    }

    public Set<String> getActiveFeeds() {
        return activeFeeds;
    }

    public void setActiveFeeds(Set<String> activeFeeds) {
        this.activeFeeds = activeFeeds;
    }

    public List<RssPlayerFeedItem> getDisplayedItems() {
        return displayedItems;
    }

    public void setDisplayedItems(List<RssPlayerFeedItem> displayedItems) {
        this.displayedItems = displayedItems;
    }
}
