package fr.toutatice.portail.acrennes.rss.portlet.repository;

import java.util.HashMap;
import java.util.List;

import javax.portlet.PortletException;

import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.directory.v2.model.Group;

import fr.toutatice.portail.acrennes.rss.portlet.model.Containers;
import fr.toutatice.portail.acrennes.rss.portlet.model.ItemRssModel;
import fr.toutatice.portail.acrennes.rss.portlet.model.Picture2;

/**
 * RSS repository interface.
 *
 * @author Frédéric Boudan
 */
public interface ItemRepository {

	/** RSS document type name. */
	String DOCUMENT_TYPE_EVENEMENT = "RssItem";
	/** Id Conteneur RSS */
	String CONTENEUR_PROPERTY = "rssi:syncId";
	/** title Item Nuxeo property. */
	String TITLE_PROPERTY = "rssi:title";
	String NAME_PROPERTY = "dc:title";
	/** link Nuxeo property. */
	String LINK_PROPERTY = "rssi:link";
	/** Description Nuxeo property. */
	String DESCRIPTION_PROPERTY = "rssi:description";
	String DESC_PROPERTY = "dc:description"; 
	/** title Item Nuxeo property. */
	String AUTHOR_PROPERTY = "rssi:author";
	/** Category Nuxeo property. */
	String CATEGORY_PROPERTY = "rssi:category";
	/** enclosure Nuxeo property. */
	String ENCLOSURE_PROPERTY = "rssi:enclosure";
	/** GUID Nuxeo property. */
	String GUID_PROPERTY = "rssi:guid";
	/** pubDate Nuxeo property. */
	String PUBDATE_PROPERTY = "rssi:pubDate";
	/** source Nuxeo property. */
	String SOURCES_PROPERTY = "rssi:source";	 
	
    /** Select2 results page size. */
    int SELECT2_RESULTS_PAGE_SIZE = 10;	
   
	/**
	 * get feeds list RSS.
	 *
	 * @param portalControllerContext portal controller context
	 * @param picture for slider 
	 * @throws PortletException
	 */
	Containers getListFeedRss(PortalControllerContext portalControllerContext, Picture2 picture) throws PortletException;

	/**
	 * get items RSS.
	 *
	 * @param portalControllerContext portal controller context
	 * @param map
	 * @param nbItems
	 * @param view
	 * @throws PortletException
	 */
	List<ItemRssModel> getListItemRss(PortalControllerContext portalControllerContext, HashMap<List<String>, List<String>> map, int nbItems, String view)
			throws PortletException;
	
    /**
     * Search Group
     * @param PortalControllerContext
     * @param filter
     * 
     * @throws PortletException
     */
    List<Group> searchGroups(PortalControllerContext portalControllerContext, String filter) throws PortletException ;
}