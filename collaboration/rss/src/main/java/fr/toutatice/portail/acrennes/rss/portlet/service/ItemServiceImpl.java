package fr.toutatice.portail.acrennes.rss.portlet.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.naming.Name;
import javax.portlet.PortletException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osivia.portal.api.Constants;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.directory.v2.model.Group;
import org.osivia.portal.api.directory.v2.model.Person;
import org.osivia.portal.api.internationalization.Bundle;
import org.osivia.portal.api.internationalization.IBundleFactory;
import org.osivia.portal.api.windows.PortalWindow;
import org.osivia.portal.api.windows.WindowFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.toutatice.portail.acrennes.rss.portlet.model.Container;
import fr.toutatice.portail.acrennes.rss.portlet.model.Containers;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssPlayer;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssPlayerFeed;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssPlayerFeedItem;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssView;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssWindowProperties;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssWindowPropertiesFeed;
import fr.toutatice.portail.acrennes.rss.portlet.repository.ItemRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * RSS service interface
 *
 * @author Frédéric Boudan
 */
@Service
public class ItemServiceImpl implements ItemService {

    /**
     * Select2 results page size.
     */
    public final static int SELECT2_RESULTS_PAGE_SIZE = 10;
    /**
     * logger
     */
    protected static final Log logger = LogFactory.getLog(ItemServiceImpl.class);
    /**
     * View RSS window property (slider, liste).
     */
    private static final String VIEW_RSS_WINDOW_PROPERTY = "form.viewRss";
    /**
     * Nb Items window property.
     */
    private static final String NBITEMS_WINDOW_PROPERTY = "form.nbItems";
    /**
     * MAP feeds window property.
     */
    private static final String FEEDS_WINDOW_PROPERTY = "form.mapFeeds";

    /**
     * Log.
     */
    private final Log log;


    /**
     * Repository Item.
     */
    @Autowired
    private ItemRepository repository;
    /**
     * Application context.
     */
    @Autowired
    private ApplicationContext applicationContext;
    /**
     * Bundle factory.
     */
    @Autowired
    private IBundleFactory bundleFactory;


    /**
     * Constructor.
     */
    public ItemServiceImpl() {
        super();

        // Log
        this.log = LogFactory.getLog(this.getClass());
    }


    @Override
    public JSONObject searchGroups(PortalControllerContext portalControllerContext, String filter, int page)
            throws PortletException {

        // Bundle
        Bundle bundle = this.bundleFactory.getBundle(portalControllerContext.getRequest().getLocale());

        // Groups
        List<Group> groups = this.repository.searchGroups(portalControllerContext, filter);

        // JSON objects
        List<JSONObject> objects = new ArrayList<>();
        for (Group group : groups) {
            // Search result
            JSONObject object = this.getSearchResult(group, bundle);
            objects.add(object);
        }

        // Results JSON object
        JSONObject results = new JSONObject();

        // Items JSON array
        JSONArray items = new JSONArray();

        // Paginated results
        int begin = (page - 1) * SELECT2_RESULTS_PAGE_SIZE;
        int end = Math.min(objects.size(), begin + SELECT2_RESULTS_PAGE_SIZE);
        for (int i = begin; i < end; i++) {
            JSONObject object = objects.get(i);
            items.add(object);
        }

        // Pagination informations
        results.put("page", page);
        results.put("pageSize", SELECT2_RESULTS_PAGE_SIZE);
        results.put("items", items);
        results.put("total", objects.size());

        return results;
    }

    /**
     * Get search result JSON Object.
     *
     * @param bundle internationalization bundle
     * @return JSON object
     */
    protected JSONObject getSearchResult(Group group, Bundle bundle) {
        JSONObject object = new JSONObject();
        object.put("id", group.getCn());
        object.put("text", group.getCn());

        return object;
    }


    @Override
    public RssWindowProperties getWindowProperties(PortalControllerContext portalControllerContext) throws PortletException {
        // Window
        PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        // Form
        RssWindowProperties form = this.applicationContext.getBean(RssWindowProperties.class);

        // Nb items
        int nbItems = NumberUtils.toInt(window.getProperty(NBITEMS_WINDOW_PROPERTY));
        form.setNbItems(nbItems);

        // View
        RssView view = RssView.fromId(window.getProperty(VIEW_RSS_WINDOW_PROPERTY));
        form.setView(view);

        // Feeds
        String feedsProperty = window.getProperty(FEEDS_WINDOW_PROPERTY);
        List<RssWindowPropertiesFeed> feeds;
        if (StringUtils.isEmpty(feedsProperty)) {
            feeds = null;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                RssWindowPropertiesFeed[] array = objectMapper.readValue(feedsProperty, RssWindowPropertiesFeed[].class);
                feeds = new ArrayList<>(Arrays.asList(array));
            } catch (JsonProcessingException e) {
                feeds = null;
                this.log.error(e.getLocalizedMessage());
            }
        }
        this.repository.fillFeeds(portalControllerContext, feeds);
        form.setFeeds(feeds);

        return form;
    }


    @Override
    public void saveWindowProperties(PortalControllerContext portalControllerContext, RssWindowProperties form) throws PortletException {
        // Window
        PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        // Nb items
        window.setProperty(NBITEMS_WINDOW_PROPERTY, String.valueOf(form.getNbItems()));

        // View
        String view;
        if (form.getView() == null) {
            view = null;
        } else {
            view = form.getView().name();
        }
        window.setProperty(VIEW_RSS_WINDOW_PROPERTY, view);

        // Feeds
        String feeds;
        if (CollectionUtils.isEmpty(form.getFeeds())) {
            feeds = null;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                feeds = objectMapper.writeValueAsString(form.getFeeds());
            } catch (JsonProcessingException e) {
                feeds = null;
                this.log.error(e.getLocalizedMessage());
            }
        }
        window.setProperty(FEEDS_WINDOW_PROPERTY, feeds);
    }


    @Override
    public RssWindowPropertiesFeed getFeedForm(PortalControllerContext portalControllerContext) throws PortletException {
        return this.getFeedForm(portalControllerContext, null, null);
    }

    @Override
    public RssWindowPropertiesFeed getFeedForm(PortalControllerContext portalControllerContext, RssWindowProperties windowProperties, String id) throws PortletException {
        RssWindowPropertiesFeed feed = this.applicationContext.getBean(RssWindowPropertiesFeed.class);

        if (StringUtils.isNotEmpty(id)) {
            feed.setId(id);
            this.repository.fillFeeds(portalControllerContext, Collections.singletonList(feed));

            if ((windowProperties != null) && CollectionUtils.isNotEmpty(windowProperties.getFeeds())) {
                boolean found = false;
                Iterator<RssWindowPropertiesFeed> iterator = windowProperties.getFeeds().iterator();
                while (!found && iterator.hasNext()) {
                    RssWindowPropertiesFeed item = iterator.next();
                    if (StringUtils.equals(id, item.getId())) {
                        found = true;
                        feed.setRights(item.getRights());
                    }
                }
            }
        }

        return feed;
    }


    @Override
    public void addFeed(PortalControllerContext portalControllerContext, RssWindowProperties windowProperties, RssWindowPropertiesFeed feed) throws PortletException {
        List<RssWindowPropertiesFeed> feeds = windowProperties.getFeeds();
        if (feeds == null) {
            feeds = new ArrayList<>(1);
            windowProperties.setFeeds(feeds);
        }

        this.repository.fillFeeds(portalControllerContext, Collections.singletonList(feed));

        feeds.add(feed);
    }


    @Override
    public void editFeed(PortalControllerContext portalControllerContext, RssWindowProperties windowProperties, RssWindowPropertiesFeed feed) throws PortletException {
        if (CollectionUtils.isNotEmpty(windowProperties.getFeeds())) {
            boolean found = false;
            Iterator<RssWindowPropertiesFeed> iterator = windowProperties.getFeeds().iterator();
            while (!found && iterator.hasNext()) {
                RssWindowPropertiesFeed item = iterator.next();
                if (StringUtils.equals(feed.getId(), item.getId())) {
                    found = true;
                    item.setRights(feed.getRights());
                }
            }
        }
    }


    @Override
    public void deleteFeed(PortalControllerContext portalControllerContext, RssWindowProperties windowProperties, String id) throws PortletException {
        if (CollectionUtils.isNotEmpty(windowProperties.getFeeds())) {
            boolean found = false;
            Iterator<RssWindowPropertiesFeed> iterator = windowProperties.getFeeds().iterator();
            while (!found && iterator.hasNext()) {
                RssWindowPropertiesFeed feed = iterator.next();
                if (StringUtils.equalsIgnoreCase(id, feed.getId())) {
                    found = true;
                    iterator.remove();
                }
            }
        }
    }


    @Override
    public List<Container> getContainers(PortalControllerContext portalControllerContext) throws PortletException {
        List<Container> result;

        Containers containers = this.repository.getListFeedRss(portalControllerContext, null);
        if (containers == null) {
            result = null;
        } else {
            result = containers.getContainers();
        }

        return result;
    }


    @Override
    public String getViewPath(PortalControllerContext portalControllerContext) throws PortletException {
        // Window properties
        RssWindowProperties windowProperties = this.getWindowProperties(portalControllerContext);

        // View
        RssView view = windowProperties.getView();
        if (view == null) {
            view = RssView.DEFAULT;
        }

        return "view-" + StringUtils.lowerCase(view.getId());
    }


    @Override
    public RssPlayer getPlayer(PortalControllerContext portalControllerContext) throws PortletException {
        // RSS player
        RssPlayer player = this.applicationContext.getBean(RssPlayer.class);

        if (!player.isLoaded()) {
            // Window properties
            RssWindowProperties windowProperties = this.getWindowProperties(portalControllerContext);

            // Person rights
            Person person = (Person) portalControllerContext.getRequest().getAttribute(Constants.ATTR_LOGGED_PERSON_2);
            List<String> rights;
            if (person == null) {
                rights = new ArrayList<>(0);
            } else {
                List<Name> names = person.getProfiles();
                if (CollectionUtils.isEmpty(names)) {
                    rights = new ArrayList<>(0);
                } else {
                    rights = new ArrayList<>(names.size());
                    for (Name name : names) {
                        String cn = StringUtils.substringAfter(name.get(name.size() - 1), "=");
                        rights.add(cn);
                    }
                }
            }

            // Loaded indicator
            player.setLoaded(true);

            // Feeds
            List<RssWindowPropertiesFeed> properties = windowProperties.getFeeds();
            List<RssPlayerFeed> feeds;
            if (CollectionUtils.isEmpty(properties)) {
                feeds = null;
            } else {
                // Feed identifiers
                List<String> identifiers = new ArrayList<>(properties.size());
                for (RssWindowPropertiesFeed property : properties) {
                    if (CollectionUtils.isEmpty(property.getRights()) || CollectionUtils.containsAny(rights, property.getRights())) {
                        identifiers.add(property.getId());
                    }
                }
                feeds = this.repository.getFeeds(portalControllerContext, identifiers, windowProperties.getNbItems());
            }
            player.setFeeds(feeds);

            // Displayed items
            List<RssPlayerFeedItem> displayedItems;
            if (CollectionUtils.isEmpty(feeds)) {
                displayedItems = null;
            } else {
                displayedItems = new ArrayList<>(feeds.size());
                for (RssPlayerFeed feed : feeds) {
                    List<RssPlayerFeedItem> items = feed.getItems();
                    if (CollectionUtils.isNotEmpty(items)) {
                        RssPlayerFeedItem item = items.get(0);
                        displayedItems.add(item);
                    }
                }
            }
            player.setDisplayedItems(displayedItems);
        }

        return player;
    }

    @Override
    public void selectFeed(PortalControllerContext portalControllerContext, RssPlayer player, String id) throws PortletException {
        List<RssPlayerFeedItem> displayedItems;
        if (CollectionUtils.isEmpty(player.getFeeds())) {
            displayedItems = null;
        }else {
            displayedItems = new ArrayList<>();
        	for(RssPlayerFeed feed: player.getFeeds()) {
        		if(id == null) {
                    List<RssPlayerFeedItem> items = feed.getItems();
                    if (CollectionUtils.isNotEmpty(items)) {
                        RssPlayerFeedItem item = items.get(0);
                        displayedItems.add(item);
                    }        			
        			player.setDisplayedItems(displayedItems);
        		} else {
            		if(feed.getId().equalsIgnoreCase(id)) {
            			List<RssPlayerFeedItem> items = feed.getItems();
                        if (CollectionUtils.isNotEmpty(items)) {
                        	for( RssPlayerFeedItem item : items) {
                                displayedItems.add(item);                    		
                        	}
                        }    			
            			player.setDisplayedItems(displayedItems);
            		}        			
        		}
        	}
        }
    }

}

