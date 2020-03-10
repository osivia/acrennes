package fr.toutatice.portail.acrennes.rss.portlet.repository;

import fr.toutatice.portail.acrennes.directory.service.ToutaticeGroupService;
import fr.toutatice.portail.acrennes.rss.portlet.command.GetContainersCommand;
import fr.toutatice.portail.acrennes.rss.portlet.command.GetItemsCommand;
import fr.toutatice.portail.acrennes.rss.portlet.model.*;
import fr.toutatice.portail.acrennes.rss.portlet.model.comparator.TitleItemComparator;
import fr.toutatice.portail.acrennes.rss.portlet.service.ItemService;
import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.nuxeo.ecm.automation.client.model.PropertyList;
import org.nuxeo.ecm.automation.client.model.PropertyMap;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.directory.v2.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.portlet.PortletException;
import java.util.*;

/**
 * Item repository Nuxeo command.
 *
 * @author Frédéric Boudan
 */
@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemRepositoryImpl implements ItemRepository {

    /**
     * FEEDS RSS
     */
    private static String FEEDS_PROPERTY = "rssc:feeds";
    /**
     * Display Name RSS
     */
    private static String DISPLAY_NAME_PROPERTY = "displayName";
    /**
     * Logo RSS
     */
    private static String LOGO_PROPERTY = "logos";
    /**
     * Image RSS
     */
    private static String PICTURE_PROPERTY = "rssi:picture";
    /**
     * Id sync flux RSS
     */
    private static String ID_PROPERTY = "syncId";
    /**
     * Description flux RSS
     */
    private static String DESCRIPTION_PROPERTY = "dc:description";

    /**
     * Application context.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Directory group service.
     */
    @Autowired
    private ToutaticeGroupService groupService;


    /**
     * Constructor.
     */
    public ItemRepositoryImpl() {
        super();
    }


    /**
     * getList feed RSS
     */
    @Override
    public Containers getListFeedRss(PortalControllerContext portalControllerContext, RssPicture picture) throws PortletException {

        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext);

        // search all container and after fill feed
        // Nuxeo command
        INuxeoCommand nuxeoCommand = this.applicationContext.getBean(GetContainersCommand.class);
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
    public void fillFeeds(PortalControllerContext portalControllerContext, List<RssWindowPropertiesFeed> feeds) throws PortletException {
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext);

        if (CollectionUtils.isNotEmpty(feeds)) {
            // Feed identifiers
            List<String> identifiers = new ArrayList<>(feeds.size());
            for (RssWindowPropertiesFeed feed : feeds) {
                identifiers.add(feed.getId());
            }

            // Containers
            Documents containers = this.getContainers(nuxeoController, identifiers);

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
                        this.fillFeed(nuxeoController, container, i, feed);
                    }
                }
            }
        }
    }

    @Override
    public List<RssPlayerFeed> getFeeds(PortalControllerContext portalControllerContext, List<String> identifiers, int limit, boolean slider) throws PortletException {
        // Nuxeo controller
        NuxeoController nuxeoController = new NuxeoController(portalControllerContext);

        // Feeds
        List<RssPlayerFeed> feeds;
        if (CollectionUtils.isEmpty(identifiers)) {
            feeds = null;
        } else {
            Map<String, RssPlayerFeed> feedsMap = new HashMap<>(identifiers.size());

            // Containers
            Documents containers = this.getContainers(nuxeoController, identifiers);

            for (Document container : containers.list()) {
                PropertyList feedList = container.getProperties().getList(FEEDS_PROPERTY);

                for (int i = 0; i < feedList.size(); i++) {
                    PropertyMap feedMap = feedList.getMap(i);

                    String feedId = feedMap.getString(ID_PROPERTY);

                    if (identifiers.contains(feedId)) {
                        // Feed
                        RssPlayerFeed feed = this.applicationContext.getBean(RssPlayerFeed.class);
                        this.fillFeed(nuxeoController, container, i, feed);

                        feedsMap.put(feedId, feed);
                    }
                }
            }


            // RSS items, sorted by feed identifier
            Map<String, List<RssPlayerFeedItem>> itemsMap = this.getItems(nuxeoController, feedsMap, identifiers, limit, slider);

            // Feeds
            if (slider) {
                feeds = new ArrayList<>(feedsMap.size());
            } else {
                feeds = new ArrayList<>(feedsMap.size() + 1);

                // Aggregated feed
                RssPlayerFeed aggregatedFeed = this.applicationContext.getBean(RssPlayerFeed.class);
                aggregatedFeed.setId(ItemService.AGGREGATED_FEEDS_ID);
                aggregatedFeed.setItems(itemsMap.get(ItemService.AGGREGATED_FEEDS_ID));

                feeds.add(aggregatedFeed);
            }

            for (String id : identifiers) {
                RssPlayerFeed feed = feedsMap.get(id);
                if (feed != null) {
                    // RSS items
                    List<RssPlayerFeedItem> items = itemsMap.get(id);
                    feed.setItems(items);

                    feeds.add(feed);
                }
            }
        }

        return feeds;
    }


    /**
     * Get containers.
     *
     * @param nuxeoController Nuxeo controller
     * @param identifiers     feeds identifiers
     * @return containers
     */
    private Documents getContainers(NuxeoController nuxeoController, List<String> identifiers) {
        // Nuxeo command
        INuxeoCommand command = this.applicationContext.getBean(GetContainersCommand.class, identifiers);

        return (Documents) nuxeoController.executeNuxeoCommand(command);
    }


    /**
     * Fill feed.
     *
     * @param nuxeoController Nuxeo controller
     * @param container       container Nuxeo document
     * @param index           property index
     * @param feed            feed
     */
    private void fillFeed(NuxeoController nuxeoController, Document container, int index, RssFeed feed) {
        // Property list
        PropertyList list = container.getProperties().getList(FEEDS_PROPERTY);
        // Property map
        PropertyMap map = list.getMap(index);

        // Identifier
        String id = map.getString(ID_PROPERTY);
        feed.setId(id);

        // Display name
        String displayName = map.getString(DISPLAY_NAME_PROPERTY);
        feed.setDisplayName(displayName);

        // Picture URL
        String url;
        if (map.get(LOGO_PROPERTY) == null) {
            url = null;
        } else {
            url = nuxeoController.createFileLink(container, FEEDS_PROPERTY + "/" + index + "/" + LOGO_PROPERTY);
        }
        feed.setPictureUrl(url);
    }


    /**
     * Get RSS items.
     *
     * @param nuxeoController Nuxeo controller
     * @param feeds           feeds
     * @param identifiers     feeds ordered identifiers
     * @param limit           limit
     * @param slider          slider indicator
     * @return RSS items
     */
    private Map<String, List<RssPlayerFeedItem>> getItems(NuxeoController nuxeoController, Map<String, RssPlayerFeed> feeds, List<String> identifiers, int limit, boolean slider) {
        // Nuxeo command
        GetItemsCommand command = this.applicationContext.getBean(GetItemsCommand.class, identifiers, true);
        // RSS items documents
        Documents documents = (Documents) nuxeoController.executeNuxeoCommand(command);

        Map<String, List<RssPlayerFeedItem>> items;
        if (documents == null) {
            items = null;
        } else {
            if (slider) {
                items = new HashMap<>(identifiers.size());
            } else {
                items = new HashMap<>(identifiers.size() + 1);
                items.put(ItemService.AGGREGATED_FEEDS_ID, new ArrayList<>(limit));
            }

            Map<String, Boolean> limitReached = new HashMap<>(identifiers.size());
            for (String identifier : identifiers) {
                limitReached.put(identifier, false);
            }

            Iterator<Document> iterator = documents.iterator();
            while (limitReached.containsValue(false) && iterator.hasNext()) {
                Document document = iterator.next();
                String identifier = document.getString(CONTENEUR_PROPERTY);
                if (!limitReached.get(identifier)) {
                    List<RssPlayerFeedItem> list = items.get(identifier);
                    if (list == null) {
                        list = new ArrayList<>(limit);
                        items.put(identifier, list);
                    }

                    // Feed display name
                    String feedDisplayName;
                    RssPlayerFeed feed = feeds.get(identifier);
                    if (feed == null) {
                        feedDisplayName = null;
                    } else {
                        feedDisplayName = feed.getDisplayName();
                    }

                    RssPlayerFeedItem item = this.convert(document, nuxeoController, feedDisplayName);

                    if (!slider || StringUtils.isNotEmpty(item.getPictureUrl())) {
                        list.add(item);

                        if (list.size() >= limit) {
                            limitReached.put(identifier, true);
                        }

                        if (!slider) {
                            List<RssPlayerFeedItem> aggregatedItems = items.get(ItemService.AGGREGATED_FEEDS_ID);
                            if (aggregatedItems.size() < limit) {
                                aggregatedItems.add(item);
                            }
                        }
                    }
                }
            }
        }

        return items;
    }


    /**
     * Convert RSS item.
     *
     * @param document Nuxeo document
     * @return RSS item
     */
    private RssPlayerFeedItem convert(Document document, NuxeoController nuxeoController, String feedDisplayName) {
        RssPlayerFeedItem item = this.applicationContext.getBean(RssPlayerFeedItem.class);

        // Title
        String title = document.getTitle();
        item.setTitle(title);

        // Description
        String description = document.getString(DESCRIPTION_PROPERTY);
        item.setDescription(description);

        // Picture URL
        String url;
        if (document.getString(ENCLOSURE_PROPERTY) == null) {
            url = null;
        } else {
            url = nuxeoController.createFileLink(document, PICTURE_PROPERTY);
        }
        item.setPictureUrl(url);

        // Link
        String link = document.getString(LINK_PROPERTY);
        item.setLink(link);

        // pubDate
        Date pubDate = document.getDate(PUBDATE_PROPERTY);
        item.setPubDate(pubDate);

        // Feed display name
        item.setFeedDisplayName(feedDisplayName);

        return item;
    }

}
