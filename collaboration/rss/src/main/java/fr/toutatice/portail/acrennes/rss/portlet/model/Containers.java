package fr.toutatice.portail.acrennes.rss.portlet.model;

import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.toutatice.portail.acrennes.rss.portlet.model.comparator.TitleItemComparator;

/**
 * Rss settings.
 * 
 * @author Frédéric Boudan
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Containers {

	@Autowired
	private TitleItemComparator comparator;
	
	private final SortedSet<Container> containers; 
	
	public Containers() {
		super();
		this.containers = new TreeSet<Container>(comparator);
	}

	public SortedSet<Container> getContainers() {
		return containers;
	}

}
