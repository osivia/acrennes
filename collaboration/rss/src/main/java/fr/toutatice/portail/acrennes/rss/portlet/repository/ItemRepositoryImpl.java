package fr.toutatice.portail.acrennes.rss.portlet.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;

import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.nuxeo.ecm.automation.client.model.PaginableDocuments;
import org.nuxeo.ecm.automation.client.model.PropertyList;
import org.nuxeo.ecm.automation.client.model.PropertyMap;
import org.osivia.portal.api.context.PortalControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import fr.toutatice.portail.acrennes.rss.portlet.command.ContainerListCommand;
import fr.toutatice.portail.acrennes.rss.portlet.command.ItemCreatCommand;
import fr.toutatice.portail.acrennes.rss.portlet.command.ItemListCommand;
import fr.toutatice.portail.acrennes.rss.portlet.model.Container;
import fr.toutatice.portail.acrennes.rss.portlet.model.Containers;
import fr.toutatice.portail.acrennes.rss.portlet.model.Feed;
import fr.toutatice.portail.acrennes.rss.portlet.model.ItemRssModel;
import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoController;
import fr.toutatice.portail.cms.nuxeo.api.domain.DocumentDTO;
import fr.toutatice.portail.cms.nuxeo.api.services.dao.DocumentDAO;

/**
 * Item repository Nuxeo command.
 * 
 * @author Frédéric Boudan
 */
@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemRepositoryImpl implements ItemRepository{

    /** Document DAO. */
    @Autowired
    private DocumentDAO documentDAO;
	/** Application context. */
    @Autowired
    private ApplicationContext applicationContext;
    
	/** FEEDS RSS */
	String FEEDS_PROPERTY = "rssc:feeds";
	/** Display Name RSS */
	String DISPLAY_NAME_PROPERTY = "displayName";	
	/** url du flux RSS */
	String URL_PROPERTY = "url";
	/** Id sync flux RSS */
	String ID_PROPERTY = "syncId";
    /** WebId Nuxeo document property. */
    private static final String WEB_ID_PROPERTY = "ttc:webid";
    /** Vignette Nuxeo document property. */
    private static final String VIGNETTE_PROPERTY = "ttc:vignette";
    /** Description Nuxeo document property. */
    private static final String DESCRIPTION_PROPERTY = "dc:description";
    
    /**
     * Constructor.
     */
    public ItemRepositoryImpl() {
        super();
    }
	
    /**
     * Create Item RSS
     */
    public void creatItem(PortalControllerContext portalControllerContext, ItemRssModel model) throws PortletException {
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext);

        // Nuxeo command
        INuxeoCommand command;
        command = this.applicationContext.getBean(ItemCreatCommand.class, model);

        nuxeoController.executeNuxeoCommand(command);
    }

	@Override
	public List<ItemRssModel> getListItemRss(PortalControllerContext portalControllerContext, String syncid) throws PortletException {
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext);

        List<ItemRssModel> items;
        
        // Nuxeo command
        INuxeoCommand nuxeoCommand = this.applicationContext.getBean(ItemListCommand.class, syncid);
        Documents documents = (Documents) nuxeoController.executeNuxeoCommand(nuxeoCommand);
        items = new ArrayList<ItemRssModel>(documents.size());
        
        for (Document document : documents) {
        	ItemRssModel item = fillItem(document, nuxeoController);
        	items.add(item);
        }        
        
		return items;
	}

	private ItemRssModel fillItem(Document document, NuxeoController nuxeoController) {
	    String id = document.getString(CONTENEUR_PROPERTY);
	    String title = document.getString(TITLE_PROPERTY);	    
	    String link = document.getString(LINK_PROPERTY);
	    String description = document.getString(DESCRIPTION_PROPERTY);
	    String autor = document.getString(AUTHOR_PROPERTY);
	    String category = document.getString(CATEGORY_PROPERTY);
	    String enclosure = document.getString(ENCLOSURE_PROPERTY);
	    Date pubdate =  document.getDate(PUBDATE_PROPERTY);
	    String guid =  document.getString(GUID_PROPERTY);
	    String sources =  document.getString(SOURCES_PROPERTY);
	    
	    ItemRssModel item = new ItemRssModel();
	    item.setIdConteneur(id);
	    item.setTitle(title);
	    item.setLink(link);
	    item.setDescription(description);
	    item.setAuthor(autor);
	    item.setCategory(category);
	    item.setEnclosure(enclosure);
	    item.setPubDate(pubdate);
	    item.setGuid(guid);
	    item.setSourceRss(sources);
	    return item;
	}
	
	@Override
	public void creatItems(PortalControllerContext portalControllerContext, List<ItemRssModel> items)
			throws PortletException {
		
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext);

        // Nuxeo command
        INuxeoCommand command;

        for (ItemRssModel item : items) {
        	item.setPath(nuxeoController.getCurrentDocumentContext().getCmsPath()); 
        	command = this.applicationContext.getBean(ItemCreatCommand.class, item);
	        nuxeoController.executeNuxeoCommand(command);
        }
	}

	@Override
	public void removeItems(PortalControllerContext portalControllerContext, List<ItemRssModel> items)
			throws PortletException {
		
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext);

        // Nuxeo command
        INuxeoCommand command;
        
//		for (ItemRssModel item : items) {
//	        command = this.applicationContext.getBean(ItemRemoveCommand.class, item);
//	        nuxeoController.executeNuxeoCommand(command);
//		}
		
	}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public PaginableDocuments searchDocuments(PortalControllerContext portalControllerContext, String basePath, String filter, int page) {
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext);

        // Nuxeo command
//        INuxeoCommand command = this.applicationContext.getBean(SearchDocumentsCommand.class, basePath, filter, page);

//        return (PaginableDocuments) nuxeoController.executeNuxeoCommand(command);
          return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getDocumentProperties(PortalControllerContext portalControllerContext, Document document) throws PortletException {
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext);
        // Document DTO
        DocumentDTO dto = this.documentDAO.toDTO(portalControllerContext, document);

        // Vignette property map
        PropertyMap vignettePropertyMap = document.getProperties().getMap(VIGNETTE_PROPERTY);
        // Vignette URL
        String vignetteUrl;
        if ((vignettePropertyMap == null) || vignettePropertyMap.isEmpty()) {
            vignetteUrl = null;
        } else {
            vignetteUrl = nuxeoController.createFileLink(document, VIGNETTE_PROPERTY);
        }

        // Icon
        String icon = dto.getIcon();

        // Document properties
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("id", document.getString(WEB_ID_PROPERTY));
        properties.put("title", document.getTitle());
        properties.put("vignette", vignetteUrl);
        properties.put("icon", icon);
        properties.put("description", document.getString(DESCRIPTION_PROPERTY));

        return properties;
    }       
    
    /**
     * getList feed RSS
     */
    public Containers getListFeedRss(PortalControllerContext portalControllerContext) throws PortletException {
        
    	// Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext);

        // search all container and after fill feed 
        // Nuxeo command
        INuxeoCommand nuxeoCommand = this.applicationContext.getBean(ContainerListCommand.class);
        Documents documents = (Documents) nuxeoController.executeNuxeoCommand(nuxeoCommand);
        Containers listContainers = null;
        
        for (Document document : documents) {
        	fillContainers(document, nuxeoController, listContainers);
        }        
    	
        return listContainers;
    }    
    
	private void fillContainers(Document document, NuxeoController nuxeoController, Containers listContainers) {
		
        PropertyList propertyList = (PropertyList) document.getProperties().get(FEEDS_PROPERTY);
        Container container = this.applicationContext.getBean(Container.class);  
        if (propertyList != null && !propertyList.isEmpty()) {
        	Feed feedNuxeo;
            for (int i = 0; i < propertyList.size(); i++) {
                PropertyMap map = propertyList.getMap(i);
				feedNuxeo = this.applicationContext.getBean(Feed.class);
				feedNuxeo.setId(map.getString(ID_PROPERTY));
				feedNuxeo.setTitle(map.getString(DISPLAY_NAME_PROPERTY));
				container.getFeeds().add(feedNuxeo);
            }
            container.setTitle(document.getTitle());
            listContainers.getContainers().add(container);
        }
        
	}	    
}
