package fr.toutatice.portail.acrennes.rss.portlet.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Rss settings.
 * 
 * @author Frédéric Boudan
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RssSettings {

    /** Nb Items. */
    private String nbItems;
    
    /** view RSS, List or Slider. */
    private String viewRss;

    /** List containers. */
    private Containers containers;
    
    /** List users rights. */
    private List<String> rights;
    
    /** List users rights. */
    private List<String> rightsDisplay;    

    /** List Feeds.*/
    private List<String> feeds;
    
    /** Map feed --> right(s). */
    private Map<String, List<String>> mapFeedsDisplay;

    /** Map feed --> right(s). */
    private Map<String, List<String>> mapFeeds;
    
    /** title. */
    private String title;
    
    /** title. */
    private Feed Feed;
    
    /**
     * Constructor.
     */
    public RssSettings() {
        super();
    }

	public String getNbItems() {
		return nbItems;
	}

	public void setNbItems(String nbItems) {
		this.nbItems = nbItems;
	}

	public String getViewRss() {
		return viewRss;
	}

	public void setViewRss(String viewRss) {
		this.viewRss = viewRss;
	}

	public Containers getContainers() {
		return containers;
	}

	public void setContainers(Containers containers) {
		this.containers = containers;
	}

	public List<String> getRights() {
		return rights;
	}

	public void setRights(List<String> rights) {
		this.rights = rights;
	}

	public Map<String, List<String>> getMapFeeds() {
		return mapFeeds;
	}

	public void setMapFeeds(Map<String, List<String>> mapFeeds) {
		this.mapFeeds = mapFeeds;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Feed getFeed() {
		return Feed;
	}

	public void setFeed(Feed feed) {
		Feed = feed;
	}

	public List<String> getRightsDisplay() {
		return rightsDisplay;
	}

	public void setRightsDisplay(List<String> rightsDisplay) {
		this.rightsDisplay = rightsDisplay;
	}

	public Map<String, List<String>> getMapFeedsDisplay() {
		return mapFeedsDisplay;
	}

	public void setMapFeedsDisplay(Map<String, List<String>> mapFeedsDisplay) {
		this.mapFeedsDisplay = mapFeedsDisplay;
	}

	public List<String> getFeeds() {
		return feeds;
	}

	public void setFeeds(List<String> feeds) {
		this.feeds = feeds;
	}

}