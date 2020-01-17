package fr.toutatice.portail.acrennes.rss.portlet.model;

import java.util.ArrayList;
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
    
    /** List personn rights. */
    private List<String> rightsPersonn;

    /** List Feeds.*/
    private List<String> feeds;
    
    /** Map feed --> right(s). */
    private Containers containersDisplays;

    /** Map feed --> right(s). */
    private Map<List<String>, List<String>> mapFeeds;
    
    /** title. */
    private String title;
    
    /** feed. */
    private String flux;
    
    /** indicator for active button  . */
    private int ind;  
    
    /** partner . */
    private String partner;
    
    /** Liste partner. */
    private ArrayList<Boolean> partners;
    
    /** Picture for the slider buttons. */	
    private Picture2 picture;
    
    /** Number of button for slider*/
    private int numberButton;
    
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getRightsDisplay() {
		return rightsDisplay;
	}

	public void setRightsDisplay(List<String> rightsDisplay) {
		this.rightsDisplay = rightsDisplay;
	}

	public List<String> getFeeds() {
		return feeds;
	}

	public void setFeeds(List<String> feeds) {
		this.feeds = feeds;
	}

	public String getFlux() {
		return flux;
	}

	public void setFlux(String flux) {
		this.flux = flux;
	}

	public Containers getContainersDisplays() {
		return containersDisplays;
	}

	public void setContainersDisplays(Containers containersDisplays) {
		this.containersDisplays = containersDisplays;
	}

	public Map<List<String>, List<String>> getMapFeeds() {
		return mapFeeds;
	}

	public void setMapFeeds(Map<List<String>, List<String>> mapFeeds) {
		this.mapFeeds = mapFeeds;
	}

	public int getInd() {
		return ind;
	}

	public void setInd(int ind) {
		this.ind = ind;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public ArrayList<Boolean> getPartners() {
		return partners;
	}

	public void setPartners(ArrayList<Boolean> partners) {
		this.partners = partners;
	}

	public List<String> getRightsPersonn() {
		return rightsPersonn;
	}

	public void setRightsPersonn(List<String> rightsPersonn) {
		this.rightsPersonn = rightsPersonn;
	}

	public Picture2 getPicture() {
		return picture;
	}

	public void setPicture(Picture2 picture) {
		this.picture = picture;
	}

	public int getNumberButton() {
		return numberButton;
	}

	public void setNumberButton(int numberButton) {
		this.numberButton = numberButton;
	}
}