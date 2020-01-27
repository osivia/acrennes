package fr.toutatice.portail.acrennes.rss.portlet.repository;

import java.util.*;

import javax.portlet.PortletException;

import fr.toutatice.portail.acrennes.rss.portlet.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.nuxeo.ecm.automation.client.model.PropertyList;
import org.nuxeo.ecm.automation.client.model.PropertyMap;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.directory.v2.DirServiceFactory;
import org.osivia.portal.api.directory.v2.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import fr.toutatice.portail.acrennes.directory.service.ToutaticeGroupService;
import fr.toutatice.portail.acrennes.rss.portlet.command.ContainerListCommand;
import fr.toutatice.portail.acrennes.rss.portlet.command.ItemListCommand;
import fr.toutatice.portail.acrennes.rss.portlet.model.comparator.TitleItemComparator;
import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoController;

/**
 * Item repository Nuxeo command.
 * 
 * @author Frédéric Boudan
 */
@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemRepositoryImpl implements ItemRepository {

	/** Application context. */
	@Autowired
	private ApplicationContext applicationContext;
	
	/**
     * Directory group service.
     */
    private final ToutaticeGroupService groupService;	

	/** FEEDS RSS */
	String FEEDS_PROPERTY = "rssc:feeds";
	/** Display Name RSS */
	String DISPLAY_NAME_PROPERTY = "displayName";
	/** Logo RSS */
	String LOGO_PROPERTY = "logos";	
	/** url du flux RSS */
	String URL_PROPERTY = "url";
	/** Id sync flux RSS */
	String ID_PROPERTY = "syncId";
	/** Description Nuxeo document property. */
	private static final String DESCRIPTION_PROPERTY = "dc:description";
	

	/**
	 * Constructor.
	 */
	public ItemRepositoryImpl() {
		super();
		this.groupService = DirServiceFactory.getService(ToutaticeGroupService.class);
	}

	public List<ItemRssModel> getListItemRss(PortalControllerContext portalControllerContext, HashMap<String, List<String>> map, int nbItems, String view)
			throws PortletException {
		// Nuxeo controller
		NuxeoController nuxeoController = new NuxeoController(portalControllerContext);

		List<ItemRssModel> items;

		// Nuxeo command
		INuxeoCommand nuxeoCommand = this.applicationContext.getBean(ItemListCommand.class, map);
		Documents documents = (Documents) nuxeoController.executeNuxeoCommand(nuxeoCommand);
		items = new ArrayList<ItemRssModel>(documents.size());
		
		int max = 1;
		for (Document document : documents) {
			ItemRssModel item = fillItem(document, nuxeoController);
			if(view.contentEquals("liste") || (item.getEnclosure() != null && view.contentEquals("slider"))) {
				items.add(item);
				if(max >= nbItems) {
					break;
				}
				max++;				
			}
		}

		return items;
	}

	private ItemRssModel fillItem(Document document, NuxeoController nuxeoController) {
		String id = document.getString(CONTENEUR_PROPERTY);
		String title = document.getString(TITLE_PROPERTY);
		String link = document.getString(LINK_PROPERTY);
		String description = document.getString(DESCRIPTION_PROPERTY);
		
		// TODO: mettre en place un sanitasier pour épurer le code
		if(description != null && description.contains("<img")) {
			if(description.contains("<a")) {
				description = description.replaceAll("<a.*a>","");
			}else {
				description = description.replaceAll("<img.*>","");	
			}
		}
		String autor = document.getString(AUTHOR_PROPERTY);
		String category = document.getString(CATEGORY_PROPERTY);
		String enclosure = document.getString(ENCLOSURE_PROPERTY);
		Date pubdate = document.getDate(PUBDATE_PROPERTY);
		String guid = document.getString(GUID_PROPERTY);
		String sources = document.getString(SOURCES_PROPERTY);

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

	/**
	 * getList feed RSS
	 */
	public Containers getListFeedRss(PortalControllerContext portalControllerContext, RssPicture picture) throws PortletException {

		// Nuxeo controller
		NuxeoController nuxeoController = new NuxeoController(portalControllerContext);

		// search all container and after fill feed
		// Nuxeo command
		INuxeoCommand nuxeoCommand = this.applicationContext.getBean(ContainerListCommand.class);
		Documents documents = (Documents) nuxeoController.executeNuxeoCommand(nuxeoCommand);
		Containers listContainers = this.applicationContext.getBean(Containers.class);

		for (Document document : documents) {
			fillContainers(document, nuxeoController, listContainers, picture);
		}

		return listContainers;
	}

	private void fillContainers(Document document, NuxeoController nuxeoController, Containers listContainers, RssPicture picture) {

		PropertyList propertyList = (PropertyList) document.getProperties().get(FEEDS_PROPERTY);
		Container container = this.applicationContext.getBean(Container.class);
		List<Feed> list = null;
		if (propertyList != null && !propertyList.isEmpty()) {
			Feed feedNuxeo;
			list = new ArrayList<>(propertyList.size());
			for (int i = 0; i < propertyList.size(); i++) {
				PropertyMap map = propertyList.getMap(i);
				feedNuxeo = this.applicationContext.getBean(Feed.class);
				feedNuxeo.setId(map.getString(ID_PROPERTY));
				feedNuxeo.setTitle(map.getString(DISPLAY_NAME_PROPERTY));
				list.add(feedNuxeo);
			}
		}
	
		if (CollectionUtils.isNotEmpty(list)) {
			// Comparator
			TitleItemComparator comparator = this.applicationContext.getBean(TitleItemComparator.class);
			Collections.sort(list, comparator);
		}
		container.setFeeds(list);
		container.setTitle(document.getTitle());
		
		if (CollectionUtils.isNotEmpty(listContainers.getContainers())) {
			listContainers.getContainers().add(container);
		} else {
			List<Container> listContainer = new ArrayList<>();
			listContainer.add(container);
			listContainers.setContainers(listContainer);
		}
	}
	
	@Override
	public List<Group> searchGroups(PortalControllerContext portalControllerContext, String filter)
			throws PortletException {

        // Groups
        Group criteria = this.groupService.getEmptyGroup();
        // Stripped filter
        String strippedFilter = StringUtils.strip(StringUtils.trim(filter), "*");
        // Tokenized filter
        String tokenizedFilter;
        if (StringUtils.isBlank(strippedFilter)) {
            tokenizedFilter = "*";
        } else {
            tokenizedFilter = strippedFilter + "*";
        }
        criteria.setCn(tokenizedFilter);
        return this.groupService.search(criteria);
	}

	@Override
	public Map<String, String> searchDisplayName(PortalControllerContext portalControllerContext, String id) throws PortletException {

        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext);
        
        // Nuxeo command
        INuxeoCommand nuxeoCommand = this.applicationContext.getBean(ContainerListCommand.class);
        Documents documents = (Documents) nuxeoController.executeNuxeoCommand(nuxeoCommand);
        Map<String, String> feed = new HashMap<>();
        for (Document document : documents) {
        	fillFeed(document, nuxeoController, feed);
        }
        
		return feed;
	}
	
	private void fillFeed(Document document, NuxeoController nuxeoController, Map<String, String> feed) {
		
        PropertyList propertyList = (PropertyList) document.getProperties().get(FEEDS_PROPERTY);
        if (propertyList != null) {
            for (int i = 0; i < propertyList.size(); i++) {
                PropertyMap map = propertyList.getMap(i);
				feed.put(map.getString(ID_PROPERTY), map.getString(DISPLAY_NAME_PROPERTY));
            }        	
        }
	}


	@Override
	public void fillFeeds(PortalControllerContext portalControllerContext, List<RssWindowPropertiesFeed> feeds) throws PortletException {
		// Nuxeo controller
		NuxeoController nuxeoController = new NuxeoController(portalControllerContext);

		if (CollectionUtils.isNotEmpty(feeds)) {
			// Feed identifiers
			List<String> ids = new ArrayList<>(feeds.size());
			for (RssWindowPropertiesFeed feed : feeds) {
				ids.add(feed.getId());
			}

			// Containers
			INuxeoCommand command = this.applicationContext.getBean(ContainerListCommand.class, ids);
			Documents containers = (Documents) nuxeoController.executeNuxeoCommand(command);

			for (Document container : containers.list()) {
				PropertyList feedList = container.getProperties().getList(FEEDS_PROPERTY);
				
				for (int i = 0; i < feedList.size(); i++) {
					PropertyMap feedMap = feedList.getMap(i);

					String feedId = feedMap.getString(ID_PROPERTY);

					// Feed
					RssWindowPropertiesFeed feed = null;
					Iterator<RssWindowPropertiesFeed> iterator = feeds.iterator();
					while ((feed == null) && iterator.hasNext()) {
						RssWindowPropertiesFeed item = iterator.next();
						if (StringUtils.equals(feedId, item.getId())) {
							feed = item;
						}
					}

					if (feed != null) {
						// Display name
						String displayName = feedMap.getString(DISPLAY_NAME_PROPERTY);
						feed.setDisplayName(displayName);

						// Picture URL
						String url;
						if (feedMap.get(LOGO_PROPERTY) == null) {
							url = null;
						} else {
							url = nuxeoController.createFileLink(container, FEEDS_PROPERTY + "/" + i + "/" + LOGO_PROPERTY);
						}
						feed.setPictureUrl(url);
					}
				}
			}
		}
	}
}
