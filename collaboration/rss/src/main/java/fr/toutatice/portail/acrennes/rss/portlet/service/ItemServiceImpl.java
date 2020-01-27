package fr.toutatice.portail.acrennes.rss.portlet.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.Name;
import javax.portlet.PortletException;

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

import fr.toutatice.portail.acrennes.rss.portlet.controller.ViewPlayerRssController;
import fr.toutatice.portail.acrennes.rss.portlet.model.Container;
import fr.toutatice.portail.acrennes.rss.portlet.model.Containers;
import fr.toutatice.portail.acrennes.rss.portlet.model.Feed;
import fr.toutatice.portail.acrennes.rss.portlet.model.ItemRssModel;
import fr.toutatice.portail.acrennes.rss.portlet.model.Picture2;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssSettings;
import fr.toutatice.portail.acrennes.rss.portlet.model.comparator.MapComparator;
import fr.toutatice.portail.acrennes.rss.portlet.repository.ItemRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * RSS service interface
 * 
 * @author Frédéric Boudan
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

    /** Repository Item. */
    @Autowired
    private ItemRepository repository;
    
    /** Application context. */
    @Autowired
    private ApplicationContext applicationContext;      
    
    /** Bundle factory. */
    @Autowired
    private IBundleFactory bundleFactory;    
    
    /** logger */
	protected static final Log logger = LogFactory.getLog(ItemServiceImpl.class);
	
    /** View RSS window property (slider, liste). */
    private static final String VIEW_RSS_WINDOW_PROPERTY = "form.viewRss";    
    
    /** Nb Items window property. */
    private static final String NBITEMS_WINDOW_PROPERTY = "form.nbItems";

    /** Nb Items window property. */
    private static final String LOGOS_WINDOW_PROPERTY  = "form.logos";
        
    /** Select2 results page size. */
	public final static int SELECT2_RESULTS_PAGE_SIZE = 10;    
    
    /** MAP feeds window property. */
    private static final String FEEDS_WINDOW_PROPERTY = "form.mapFeeds";
    
    /** index for slider buttons Items window property. */
    private static final String INDEX_WINDOW_PROPERTY = "form.index";
    
    /** part for slider buttons Items window property. */
    private static final String PART_WINDOW_PROPERTY = "form.part";    
    
    /**
     * {@inheritDoc}
     * @throws IOException 
     */
	@SuppressWarnings({ "unused"})
	public List<ItemRssModel> getListItem(PortalControllerContext portalControllerContext) throws PortletException, IOException {
    	String result = null;
    	
    	RssSettings settings = getSettings(portalControllerContext);
    	
    	if(settings.getMapFeeds() == null || settings.getMapFeeds().isEmpty() || settings.getNbItems() == null) {
    		return null;
    	}
    	
		HashMap<String, List<String>> mapFeedRight = new HashMap<>();
		// Fill the map of feed with the personn have the rights to see them
		for(HashMap.Entry<List<String>, List<String>> entry : settings.getSortFeeds().entrySet()){
				// if ind = 0 --> search all partner (index is for slider rss)
				if(settings.getInd() != 0) {
					if(entry.getKey().get(1).contentEquals(settings.getPartner()) ) {
						if(entry.getValue().size() <2 || !Collections.disjoint(entry.getValue(), settings.getRightsPersonn())){
							mapFeedRight.put(entry.getKey().get(0), entry.getValue());
							break;
						}			
					}
				} else {
					// Check if the personn have the right to see the feed
					if(entry.getValue().size() == 1 || !Collections.disjoint(entry.getValue(), settings.getRightsPersonn())){
						mapFeedRight.put(entry.getKey().get(0), entry.getValue());
					}			
				}
		}
		
		if(mapFeedRight == null || mapFeedRight.isEmpty()) {
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
        if(settings.getMapFeeds() != null) {
        	for (HashMap.Entry<String, List<String>> entry : settings.getMapFeeds().entrySet()) {
                map.put(entry.getKey(), entry.getValue());
              }        	
        }
        
        // Search Title into list container
        List<Container> containers = settings.getContainers().getContainers();
        boolean ok = false;
        for(Container container: containers) {
        	for(Feed feed: container.getFeeds()) {
        		if(settings.getFlux().equalsIgnoreCase(feed.getId())) {
        			map.put(feed.getId(), settings.getRights());
					ok = true;
					break;
        		}
        		if(ok) {
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
		if(index == null) {
			settings.setInd(0);	
		}else {
			settings.setInd(Integer.parseInt(index));
		}
		
		// part 
		String part = window.getProperty(PART_WINDOW_PROPERTY);
		settings.setPartner(part);
		
    	// getCurretPerson to build a new map of feeds
    	Person person = (Person) portalControllerContext.getRequest().getAttribute(Constants.ATTR_LOGGED_PERSON_2);
		List<Name> name = person.getProfiles();
		List<String> right = new ArrayList<String>();
		for (Name cn : name) {
			String conv = cn.get(cn.size() - 1).replace("cn=", "");
			right.add(conv);
		}		
		settings.setRightsPersonn(right);
		
		if(settings.getViewRss().contentEquals(ViewPlayerRssController.VIEW_SLIDER)) {
			// enable partner button for slider
			setButtonSlider(right, settings);	
		}	
		
		return settings;
	}
	
	@Override
	public RssSettings getList(PortalControllerContext portalControllerContext) throws PortletException, IOException {
		// Window
		PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

		// Portlet settings
		RssSettings settings = this.applicationContext.getBean(RssSettings.class);

		Picture2 picture = this.applicationContext.getBean(Picture2.class);
		
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
	public void delFeeds(PortalControllerContext portalControllerContext, RssSettings settings, String id)
			throws PortletException {
		// Window
		PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        Map<String, List<String>> map = new HashMap<>();
        boolean del = false;
        if(settings.getMapFeeds() != null) {
			for (HashMap.Entry<String, List<String>> entry : settings.getMapFeeds().entrySet()) {
				if (entry.getKey().equalsIgnoreCase(id)) {
					del = true;
					break;
				}
				if (!del) {
					map.put(entry.getKey(), entry.getValue());
				} else {
					del = false;
				}
			}       	
        }
        
        settings.setMapFeeds(map);
        
        JSONArray json = setJSONArray(settings);
        window.setProperty(FEEDS_WINDOW_PROPERTY, json.toString());
        window.setProperty(NBITEMS_WINDOW_PROPERTY, settings.getNbItems());
        window.setProperty(VIEW_RSS_WINDOW_PROPERTY, settings.getViewRss());        
	}

	@Override
	public void mod(PortalControllerContext portalControllerContext, RssSettings settings) throws PortletException {
		// Window
		PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        Map<String, List<String>> map = new HashMap<>();
        boolean mod = false;
        if(settings.getMapFeeds() != null) {
        	for (HashMap.Entry<String, List<String>> entry : settings.getMapFeeds().entrySet()) {
               
				if (entry.getKey().equalsIgnoreCase(settings.getFeeds().get(0))) {
					mod = true;
				}
        		if(mod) {
                	map.put(entry.getKey(), settings.getRights());
                	mod = false;
                }else {
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
        
        if(json != null && !json.isEmpty() && !json.equalsIgnoreCase("{}")) {
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
    					val = val.replaceAll("\"","");
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
            Map<List<String>, List<String>> map10 = new LinkedHashMap<List<String>,List<String>>(); 
            // Sort Map
            for(HashMap.Entry<String, String> entry : sortMap.entrySet()) {
            	list.add(entry.getKey());
            	list.add(entry.getValue());
            	map10.put(list, settings.getMapFeeds().get(entry.getKey()));
            	list = new ArrayList<String>();
            }

            settings.setSortFeeds(map10);
        }
	}	
	
	public static JSONArray setJSONArray(RssSettings settings) {
        // Message JSON object
        JSONObject id = new JSONObject();
        JSONObject rights = new JSONObject();
        JSONArray items = new JSONArray();
        for(HashMap.Entry<String, List<String>> entry : settings.getMapFeeds().entrySet()){
        	id.put("id", entry.getKey());            		
            items.add(id);
            if(entry.getValue() == null) {
                rights.put("rights", "");            	
            } else {
                rights.put("rights", entry.getValue());
            }

            items.add(rights);;
        }	
        return items; 
	}	
	
    /**
     * Enable Button for the slider Rss
     * it's a list of rights so you have change the test with the list of rihts of the feed
     * @throws IOException 
     * 
    */	
	public void setButtonSlider(List<String> right, RssSettings settings) throws IOException {

		ArrayList<Boolean> list = new ArrayList<Boolean>();
		// for each Button feed, check if the personn have the rigths to see this feed
		for(HashMap.Entry<List<String>, List<String>> entry : settings.getSortFeeds().entrySet()) {
			// if feed has no right so enable button
			if (entry.getValue().toString().length() <3 || !Collections.disjoint(entry.getValue(), right)) {
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
     * @param person person
     * @param alreadyMember already member indicator
     * @param existingRequest existing request indicator
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
    	window.setProperty(INDEX_WINDOW_PROPERTY, Integer.toString(index) );
    }

}

