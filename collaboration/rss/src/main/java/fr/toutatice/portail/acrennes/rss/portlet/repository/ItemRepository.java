package fr.toutatice.portail.acrennes.rss.portlet.repository;

import fr.toutatice.portail.acrennes.rss.portlet.model.Containers;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssPicture;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssPlayerFeed;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssWindowPropertiesFeed;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.directory.v2.model.Group;

import javax.portlet.PortletException;
import java.util.List;

/**
 * RSS repository interface.
 *
 * @author Frédéric Boudan
 */
public interface ItemRepository {

    /**
     * RSS document type name.
     */
    String DOCUMENT_TYPE_EVENEMENT = "RssItem";
    /**
     * Id Conteneur RSS
     */
    String CONTENEUR_PROPERTY = "rssi:syncId";
    /**
     * title Item Nuxeo property.
     */
    String TITLE_PROPERTY = "rssi:title";
    String NAME_PROPERTY = "dc:title";
    /**
     * link Nuxeo property.
     */
    String LINK_PROPERTY = "rssi:link";
    /**
     * Description Nuxeo property.
     */
    String DESCRIPTION_PROPERTY = "rssi:description";
    String DESC_PROPERTY = "dc:description";
    /**
     * title Item Nuxeo property.
     */
    String AUTHOR_PROPERTY = "rssi:author";
    /**
     * Category Nuxeo property.
     */
    String CATEGORY_PROPERTY = "rssi:category";
    /**
     * enclosure Nuxeo property.
     */
    String ENCLOSURE_PROPERTY = "rssi:enclosure";
    /**
     * GUID Nuxeo property.
     */
    String GUID_PROPERTY = "rssi:guid";
    /**
     * pubDate Nuxeo property.
     */
    String PUBDATE_PROPERTY = "rssi:pubDate";
    /**
     * source Nuxeo property.
     */
    String SOURCES_PROPERTY = "rssi:source";


    /**
     * get feeds list RSS.
     *
     * @param portalControllerContext portal controller context
     * @param picture                 for slider
     * @throws PortletException
     */
    Containers getListFeedRss(PortalControllerContext portalControllerContext, RssPicture picture) throws PortletException;


    /**
     * Search groups.
     *
     * @param portalControllerContext portal controller context
     * @param filter                  search filter
     */
    List<Group> searchGroups(PortalControllerContext portalControllerContext, String filter) throws PortletException;


    /**
     * Fill feeds.
     *
     * @param portalControllerContext portal controller context
     * @param feeds                   feeds
     */
    void fillFeeds(PortalControllerContext portalControllerContext, List<RssWindowPropertiesFeed> feeds) throws PortletException;


    /**
     * Get feeds.
     *
     * @param portalControllerContext portal controller context
     * @param identifiers             feeds identifiers
     * @param limit                   limit
     * @return feeds
     */
    List<RssPlayerFeed> getFeeds(PortalControllerContext portalControllerContext, List<String> identifiers, int limit) throws PortletException;

}