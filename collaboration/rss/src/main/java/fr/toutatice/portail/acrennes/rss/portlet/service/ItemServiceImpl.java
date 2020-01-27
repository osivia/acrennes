package fr.toutatice.portail.acrennes.rss.portlet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.toutatice.portail.acrennes.rss.portlet.controller.ViewPlayerRssController;
import fr.toutatice.portail.acrennes.rss.portlet.model.*;
import fr.toutatice.portail.acrennes.rss.portlet.model.comparator.MapComparator;
import fr.toutatice.portail.acrennes.rss.portlet.repository.ItemRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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

import javax.naming.Name;
import javax.portlet.PortletException;
import java.io.IOException;
import java.util.*;

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
     * Nb Items window property.
     */
    private static final String LOGOS_WINDOW_PROPERTY = "form.logos";
    /**
     * MAP feeds window property.
     */
    private static final String FEEDS_WINDOW_PROPERTY = "form.mapFeeds";
    /**
     * index for slider buttons Items window property.
     */
    private static final String INDEX_WINDOW_PROPERTY = "form.index";
    /**
     * part for slider buttons Items window property.
     */
    private static final String PART_WINDOW_PROPERTY = "form.part";


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


    public static JSONArray setJSONArray(RssSettings settings) {
        // Message JSON object
        JSONObject id = new JSONObject();
        JSONObject rights = new JSONObject();
        JSONArray items = new JSONArray();
        for (HashMap.Entry<String, List<String>> entry : settings.getMapFeeds().entrySet()) {
            id.put("id", entry.getKey());
            items.add(id);
            if (entry.getValue() == null) {
                rights.put("rights", "");
            } else {
                rights.put("rights", entry.getValue());
            }

            items.add(rights);
            ;
        }
        return items;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    @SuppressWarnings({"unused"})
    public List<ItemRssModel> getListItem(PortalControllerContext portalControllerContext) throws PortletException, IOException {
        String result = null;

        RssSettings settings = getSettings(portalControllerContext);

        if (settings.getMapFeeds() == null || settings.getMapFeeds().isEmpty() || settings.getNbItems() == null) {
            return null;
        }

        HashMap<String, List<String>> mapFeedRight = new HashMap<>();
        // Fill the map of feed with the personn have the rights to see them
        for (HashMap.Entry<List<String>, List<String>> entry : settings.getSortFeeds().entrySet()) {
            // if ind = 0 --> search all partner (index is for slider rss)
            if (settings.getInd() != 0) {
                if (entry.getKey().get(1).contentEquals(settings.getPartner())) {
                    if (entry.getValue().size() < 2 || !Collections.disjoint(entry.getValue(), settings.getRightsPersonn())) {
                        mapFeedRight.put(entry.getKey().get(0), entry.getValue());
                        break;
                    }
                }
            } else {
                // Check if the personn have the right to see the feed
                if (entry.getValue().size() == 1 || !Collections.disjoint(entry.getValue(), settings.getRightsPersonn())) {
                    mapFeedRight.put(entry.getKey().get(0), entry.getValue());
                }
            }
        }

        if (mapFeedRight == null || mapFeedRight.isEmpty()) {
            return null;
        }

        return this.repository.getListItemRss(portalControllerContext, mapFeedRight, Integer.parseInt(settings.getNbItems()), settings.getViewRss());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(PortalControllerContext portalControllerContext, RssSettings settings) throws PortletException {
        // Window
        PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        Map<String, List<String>> map = new HashMap<>();
        if (settings.getMapFeeds() != null) {
            for (HashMap.Entry<String, List<String>> entry : settings.getMapFeeds().entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }
        }

        // Search Title into list container
        List<Container> containers = settings.getContainers().getContainers();
        boolean ok = false;
        for (Container container : containers) {
            for (Feed feed : container.getFeeds()) {
                if (settings.getFlux().equalsIgnoreCase(feed.getId())) {
                    map.put(feed.getId(), settings.getRights());
                    ok = true;
                    break;
                }
                if (ok) {
                    break;
                }
            }
        }

        settings.setMapFeeds(map);
        settings.setFlux(null);

        JSONArray items = setJSONArray(settings);

        window.setProperty(FEEDS_WINDOW_PROPERTY, items.toString());
        window.setProperty(NBITEMS_WINDOW_PROPERTY, settings.getNbItems());
        window.setProperty(VIEW_RSS_WINDOW_PROPERTY, settings.getViewRss());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveSettings(PortalControllerContext portalControllerContext, RssSettings settings) throws PortletException {
        // Window
        PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        window.setProperty(NBITEMS_WINDOW_PROPERTY, settings.getNbItems());
        window.setProperty(VIEW_RSS_WINDOW_PROPERTY, settings.getViewRss());
    }

    @Override
    public RssSettings getSettings(PortalControllerContext portalControllerContext) throws PortletException, IOException {
        // Window
        PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        // Portlet settings
        RssSettings settings = this.applicationContext.getBean(RssSettings.class);

        // Nb Items
        String nbItems = window.getProperty(NBITEMS_WINDOW_PROPERTY);
        settings.setNbItems(nbItems);

        // View Rss
        String viewRss = window.getProperty(VIEW_RSS_WINDOW_PROPERTY);
        settings.setViewRss(viewRss);

        // List Feeds
        getFeed(settings, window, portalControllerContext);

        // Index
        String index = window.getProperty(INDEX_WINDOW_PROPERTY);
        if (index == null) {
            settings.setInd(0);
        } else {
            settings.setInd(Integer.parseInt(index));
        }

        // part
        String part = window.getProperty(PART_WINDOW_PROPERTY);
        settings.setPartner(part);

        // getCurretPerson to build a new map of feeds
        Person person = (Person) portalControllerContext.getRequest().getAttribute(Constants.ATTR_LOGGED_PERSON_2);
        List<String> rights;
        if (person == null) {
            rights = null;
        } else {
            List<Name> names = person.getProfiles();
            if (CollectionUtils.isEmpty(names)) {
                rights = null;
            } else {
                rights = new ArrayList<>(names.size());
                for (Name name : names) {
                    String cn = StringUtils.substringAfter(name.get(name.size() - 1), "=");
                    rights.add(cn);
                }
            }
        }
        settings.setRightsPersonn(rights);

        if (ViewPlayerRssController.VIEW_SLIDER.equals(settings.getViewRss())) {
            // enable partner button for slider
            setButtonSlider(rights, settings);
        }

        return settings;
    }

    @Override
    public RssSettings getList(PortalControllerContext portalControllerContext) throws PortletException, IOException {
        // Window
        PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        // Portlet settings
        RssSettings settings = this.applicationContext.getBean(RssSettings.class);

        RssPicture picture = this.applicationContext.getBean(RssPicture.class);

        // List Feeds View
        Containers containers = this.repository.getListFeedRss(portalControllerContext, picture);
        settings.setContainers(containers);

        settings.setPicture(picture);

        // List Feeds
        getFeed(settings, window, portalControllerContext);

        // Nb Items
        String nbItems = window.getProperty(NBITEMS_WINDOW_PROPERTY);
        settings.setNbItems(nbItems);

        // View Rss
        String viewRss = window.getProperty(VIEW_RSS_WINDOW_PROPERTY);
        settings.setViewRss(viewRss);

        return settings;
    }

    @Override
    public void delFeeds(PortalControllerContext portalControllerContext, RssWindowProperties form, String id)
            throws PortletException {
        if (CollectionUtils.isNotEmpty(form.getFeeds())) {
            boolean found = false;
            Iterator<RssWindowPropertiesFeed> iterator = form.getFeeds().iterator();
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
    public void mod(PortalControllerContext portalControllerContext, RssSettings settings) throws PortletException {
        // Window
        PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        Map<String, List<String>> map = new HashMap<>();
        boolean mod = false;
        if (settings.getMapFeeds() != null) {
            for (HashMap.Entry<String, List<String>> entry : settings.getMapFeeds().entrySet()) {

                if (entry.getKey().equalsIgnoreCase(settings.getFeeds().get(0))) {
                    mod = true;
                }
                if (mod) {
                    map.put(entry.getKey(), settings.getRights());
                    mod = false;
                } else {
                    map.put(entry.getKey(), entry.getValue());
                }
            }
        }

        settings.setMapFeeds(map);

        JSONArray items = setJSONArray(settings);

        window.setProperty(FEEDS_WINDOW_PROPERTY, items.toString());
        window.setProperty(NBITEMS_WINDOW_PROPERTY, settings.getNbItems());
        window.setProperty(VIEW_RSS_WINDOW_PROPERTY, settings.getViewRss());
        window.setProperty(LOGOS_WINDOW_PROPERTY, settings.getPicture().getUrl());
    }

    public void getFeed(RssSettings settings, PortalWindow window, PortalControllerContext portalControllerContext) throws PortletException {
        String json = window.getProperty(FEEDS_WINDOW_PROPERTY);

        if (json != null && !json.isEmpty() && !json.equalsIgnoreCase("{}")) {
            JSONArray items = JSONArray.fromObject(json);

            JSONArray jArray = (JSONArray) items;
            List<String> rights = new ArrayList<String>();
            Map<String, List<String>> map = new HashMap<>();
            Map<String, String> mapFeed = new HashMap<>();
            MapComparator comparateur = new MapComparator(mapFeed);
            TreeMap<String, String> sortMap = new TreeMap<>(comparateur);
            String name = null;
            String id = null;
            Map<String, String> displayName = this.repository.searchDisplayName(portalControllerContext, null);
            if (jArray != null && !jArray.isEmpty()) {
                for (int i = 0; i < jArray.size(); i++) {
                    JSONObject home = jArray.getJSONObject(i);
                    if (home.containsKey("id")) {
                        id = home.getString("id");
                        name = displayName.get(home.getString("id"));
                        mapFeed.put(id, name);
                    }
                    if (home.containsKey("rights")) {
                        String val = home.getString("rights").replace("[", "");
                        val = val.replace("]", "");
                        val = val.replaceAll("\"", "");
                        List<String> strings = Arrays.asList(val.split(","));
                        rights.addAll(strings);
                        map.put(id, rights);
                        rights = new ArrayList<String>();
                    }
                }
            }
            settings.setMapFeeds(map);
            List<String> list = new ArrayList<String>();
            sortMap.putAll(mapFeed);
            Map<List<String>, List<String>> map10 = new LinkedHashMap<List<String>, List<String>>();
            // Sort Map
            for (HashMap.Entry<String, String> entry : sortMap.entrySet()) {
                list.add(entry.getKey());
                list.add(entry.getValue());
                map10.put(list, settings.getMapFeeds().get(entry.getKey()));
                list = new ArrayList<String>();
            }

            settings.setSortFeeds(map10);
        }
    }

    /**
     * Enable Button for the slider Rss
     * it's a list of rights so you have change the test with the list of rihts of the feed
     *
     * @throws IOException
     */
    public void setButtonSlider(List<String> right, RssSettings settings) throws IOException {

        ArrayList<Boolean> list = new ArrayList<Boolean>();
        // for each Button feed, check if the personn have the rigths to see this feed
        for (HashMap.Entry<List<String>, List<String>> entry : settings.getSortFeeds().entrySet()) {
            // if feed has no right so enable button
            if (entry.getValue().toString().length() < 3 || !Collections.disjoint(entry.getValue(), right)) {
                list.add(true);
            } else {
                list.add(false);
            }
        }
        settings.setPartners(list);
        settings.setNumberButton(Collections.frequency(list, true));
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
    public void viewPart(PortalControllerContext portalControllerContext, int index, String part)
            throws PortletException {
        // Window
        PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());
        window.setProperty(PART_WINDOW_PROPERTY, part);
        window.setProperty(INDEX_WINDOW_PROPERTY, Integer.toString(index));
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

}

