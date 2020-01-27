package fr.toutatice.portail.acrennes.rss.portlet.service;

import fr.toutatice.portail.acrennes.rss.portlet.model.Container;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssPlayer;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssWindowProperties;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssWindowPropertiesFeed;
import net.sf.json.JSONObject;
import org.osivia.portal.api.context.PortalControllerContext;

import javax.portlet.PortletException;
import java.util.List;

/**
 * Item RSS service interface
 *
 * @author Frédéric Boudan
 */
public interface ItemService {

    /**
     * Search groups.
     *
     * @param portalControllerContext portal controller context
     * @param filter                  search filter
     * @param page                    search page
     */
    JSONObject searchGroups(PortalControllerContext portalControllerContext, String filter, int page) throws PortletException;


    /**
     * Get window properties.
     *
     * @param portalControllerContext portal controller context
     * @return window properties
     */
    RssWindowProperties getWindowProperties(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Save window properties.
     *
     * @param portalControllerContext portal controller context
     * @param form                    form
     */
    void saveWindowProperties(PortalControllerContext portalControllerContext, RssWindowProperties form) throws PortletException;


    /**
     * Get feed form.
     *
     * @param portalControllerContext portal controller context
     * @return form
     */
    RssWindowPropertiesFeed getFeedForm(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Get feed form.
     *
     * @param portalControllerContext portal controller context
     * @param windowProperties        window properties
     * @param id                      feed identifier
     * @return form
     */
    RssWindowPropertiesFeed getFeedForm(PortalControllerContext portalControllerContext, RssWindowProperties windowProperties, String id) throws PortletException;


    /**
     * Add feed.
     *
     * @param portalControllerContext portal controller context
     * @param windowProperties        window properties
     * @param feed                    feed
     */
    void addFeed(PortalControllerContext portalControllerContext, RssWindowProperties windowProperties, RssWindowPropertiesFeed feed) throws PortletException;


    /**
     * Edit feed.
     *
     * @param portalControllerContext portal controller context
     * @param windowProperties        window properties
     * @param feed                    feed
     */
    void editFeed(PortalControllerContext portalControllerContext, RssWindowProperties windowProperties, RssWindowPropertiesFeed feed) throws PortletException;


    /**
     * Delete feed.
     *
     * @param portalControllerContext portal controller context
     * @param windowProperties        window properties
     * @param id                      feed identifier
     * @throws PortletException
     */
    void deleteFeed(PortalControllerContext portalControllerContext, RssWindowProperties windowProperties, String id) throws PortletException;


    /**
     * Get RSS containers.
     *
     * @param portalControllerContext portal controller context
     * @return RSS containers
     */
    List<Container> getContainers(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Get view path.
     *
     * @param portalControllerContext portal controller context
     * @return view path
     */
    String getViewPath(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Get RSS player.
     *
     * @param portalControllerContext portal controller context
     * @return RSS player
     */
    RssPlayer getPlayer(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Select feed.
     *
     * @param portalControllerContext portal controller context
     * @param player                  RSS player
     * @param id                      feed identifier
     */
    void selectFeed(PortalControllerContext portalControllerContext, RssPlayer player, String id) throws PortletException;

}
