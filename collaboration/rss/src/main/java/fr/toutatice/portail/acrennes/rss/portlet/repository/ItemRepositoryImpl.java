package fr.toutatice.portail.acrennes.rss.portlet.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;

import org.apache.commons.collections.CollectionUtils;
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

	/** FEEDS RSS */
	String FEEDS_PROPERTY = "rssc:feeds";
	/** Display Name RSS */
	String DISPLAY_NAME_PROPERTY = "displayName";
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
	public List<ItemRssModel> getListItemRss(PortalControllerContext portalControllerContext, String syncid)
			throws PortletException {
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
		Containers listContainers = this.applicationContext.getBean(Containers.class);

		for (Document document : documents) {
			fillContainers(document, nuxeoController, listContainers);
		}

		return listContainers;
	}

	private void fillContainers(Document document, NuxeoController nuxeoController, Containers listContainers) {

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
	public PaginableDocuments searchDocuments(PortalControllerContext portalControllerContext, String basePath,
			String filter, int page) throws PortletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getDocumentProperties(PortalControllerContext portalControllerContext, Document document)
			throws PortletException {
		// TODO Auto-generated method stub
		return null;
	}

}
