package fr.toutatice.portail.acrennes.rss.portlet.model;

import java.util.List;

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
    
    /** . */
    private List<String> rights;
    
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

}