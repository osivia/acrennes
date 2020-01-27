package fr.toutatice.portail.acrennes.rss.portlet.service;

import fr.toutatice.portail.acrennes.rss.portlet.model.*;
import net.sf.json.JSONObject;
import org.osivia.portal.api.context.PortalControllerContext;

import javax.portlet.PortletException;
import java.io.IOException;
import java.util.List;

/**
 * Item RSS service interface
 *
 * @author Frédéric Boudan
 */
public interface ItemService {

    /**
     * Base path window property.
     */
    String BASE_PATH_PROPERTY = "osivia.rss.basePath";

    /**
     * get List Item.
     *
     * @param portalControllerContext portal controller context
     * @return List<ContainerRssModel>
     * @throws PortletException
     * @throws IOException
     */
    List<ItemRssModel> getListItem(PortalControllerContext portalControllerContext) throws PortletException, IOException;

    /**
     * Get portlet settings.
     *
     * @param portalControllerContext portal controller context
     * @return portlet settings
     * @throws PortletException
     */
    RssSettings getSettings(PortalControllerContext portalControllerContext) throws PortletException, IOException;


    /**
     * Save portlet settings.
     *
     * @param portalControllerContext portal controller context
     * @param settings                portlet settings
     * @throws PortletException
     */
    void save(PortalControllerContext portalControllerContext, RssSettings settings) throws PortletException;

    /**
     * Save portlet settings.
     *
     * @param portalControllerContext portal controller context
     * @param settings                portlet settings
     * @throws PortletException
     */
    void saveSettings(PortalControllerContext portalControllerContext, RssSettings settings) throws PortletException;

    /**
     * Get portlet settings.
     *
     * @param portalControllerContext portal controller context
     * @return portlet settings
     * @throws PortletException
     */
    RssSettings getList(PortalControllerContext portalControllerContext) throws PortletException, IOException;

    /**
     * delete feeds.
     *
     * @param portalControllerContext portal controller context
     * @param form                    form
     * @param id                      Id
     * @throws PortletException
     */
    void delFeeds(PortalControllerContext portalControllerContext, RssWindowProperties form, String id) throws PortletException;

    /**
     * Modication portlet settings.
     *
     * @param portalControllerContext portal controller context
     * @param settings                portlet settings
     * @throws PortletException
     */
    void mod(PortalControllerContext portalControllerContext, RssSettings settings) throws PortletException;

    /**
     * Search Groups
     *
     * @param portalControllerContext portal controller context
     * @param filter
     * @param page
     * @throws PortletException
     */
    JSONObject searchGroups(PortalControllerContext portalControllerContext, String filter, int page) throws PortletException;

    /**
     * get List Item.
     *
     * @param portalControllerContext portal controller context
     * @param index                   int
     * @param partner                 String
     * @return List<ContainerRssModel>
     * @throws PortletException
     * @throws IOException
     */
    void viewPart(PortalControllerContext portalControllerContext, int index, String part) throws PortletException;


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
     * Get RSS containers.
     *
     * @param portalControllerContext portal controller context
     * @return RSS containers
     */
    List<Container> getContainers(PortalControllerContext portalControllerContext) throws PortletException;

}
