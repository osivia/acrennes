package fr.toutatice.portail.acrennes.rss.portlet.service;

import java.io.IOException;
import java.util.List;

import javax.portlet.PortletException;

import org.osivia.portal.api.context.PortalControllerContext;

import fr.toutatice.portail.acrennes.rss.portlet.model.ItemRssModel;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssSettings;
import net.sf.json.JSONObject;

/**
 * Item RSS service interface
 * 
 * @author Frédéric Boudan
 *
 */
public interface ItemService {

    /** Base path window property. */
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
     * @param settings portlet settings
     * @throws PortletException
     */
    void save(PortalControllerContext portalControllerContext, RssSettings settings) throws PortletException;

    /**
     * Save portlet settings.
     * 
     * @param portalControllerContext portal controller context
     * @param settings portlet settings
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
     * @param settings portlet settings
     * @param id Id
     * @throws PortletException
     */
    void delFeeds(PortalControllerContext portalControllerContext, RssSettings settings, String id) throws PortletException;
    
    /**
     * Modication portlet settings.
     * 
     * @param portalControllerContext portal controller context
     * @param settings portlet settings
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
     * @param index int 
     * @param partner String 
     * @return List<ContainerRssModel>
     * @throws PortletException
     * @throws IOException 
     */
    void viewPart(PortalControllerContext portalControllerContext, int index, String part)  throws PortletException;
		
}
