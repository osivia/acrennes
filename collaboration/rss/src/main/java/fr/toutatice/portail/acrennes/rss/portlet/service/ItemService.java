package fr.toutatice.portail.acrennes.rss.portlet.service;

import java.util.List;

import javax.portlet.PortletException;

import org.nuxeo.ecm.automation.client.model.Document;
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
     */
	List<ItemRssModel> getListItem(PortalControllerContext portalControllerContext) throws PortletException;
	
    /**
     * Get current Nuxeo document.
     * 
     * @param portalControllerContext portal controller context
     * @return Nuxeo document
     * @throws PortletException
     */
    Document getCurrentDocument(PortalControllerContext portalControllerContext) throws PortletException;	
    
    /**
     * Get portlet settings.
     * 
     * @param portalControllerContext portal controller context
     * @return portlet settings
     * @throws PortletException
     */
    RssSettings getSettings(PortalControllerContext portalControllerContext) throws PortletException;


    /**
     * Save portlet settings.
     * 
     * @param portalControllerContext portal controller context
     * @param settings portlet settings
     * @throws PortletException
     */
    void save(PortalControllerContext portalControllerContext, RssSettings settings) throws PortletException;

    /**
     * Search documents.
     *
     * @param portalControllerContext portal controller context
     * @param filter search filter
     * @param page search pagination page number
     * @return search result JSON object
     * @throws PortletException
     */
    JSONObject searchDocuments(PortalControllerContext portalControllerContext, String filter, int page) throws PortletException;
}
