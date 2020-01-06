package fr.toutatice.portail.acrennes.rss.portlet.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Name;
import javax.portlet.PortletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osivia.portal.api.Constants;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.directory.v2.model.Person;
import org.osivia.portal.api.windows.PortalWindow;
import org.osivia.portal.api.windows.WindowFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import fr.toutatice.portail.acrennes.rss.portlet.model.Container;
import fr.toutatice.portail.acrennes.rss.portlet.model.Containers;
import fr.toutatice.portail.acrennes.rss.portlet.model.Feed;
import fr.toutatice.portail.acrennes.rss.portlet.model.ItemRssModel;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssSettings;
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
    
    /** logger */
	protected static final Log logger = LogFactory.getLog(ItemServiceImpl.class);
	
    /** View RSS window property (slider, liste). */
    private static final String VIEW_RSS_WINDOW_PROPERTY = "form.viewRss";    
    
    /** Nb Items window property. */
    private static final String NBITEMS_WINDOW_PROPERTY = "form.nbItems";
    
    /** Select2 results page size. */
	public final static int SELECT2_RESULTS_PAGE_SIZE = 10;    

    /** Default properties file name. */
    private static final String PROPERTIES_FILE_NAME = "rights.properties";	
    
    /** Nb Items window property. */
    private static final String FEEDS_WINDOW_PROPERTY = "form.mapFeeds";   
    
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
    	
    	// getCurretPerson to build a new map of feeds  
    	Person person = (Person) portalControllerContext.getRequest().getAttribute(Constants.ATTR_LOGGED_PERSON_2);
		List<Name> name = person.getProfiles();
		List<String> right = new ArrayList<String>();
		HashMap<List<String>, List<String>> mapFeedRight = new HashMap<>();;
		for (Name cn : name) {
			String conv = cn.get(cn.size() - 1).replace("cn=", "");
			right.add(conv);
		}
		for(HashMap.Entry<List<String>, List<String>> entry : settings.getMapFeeds().entrySet()){
			if(entry.getValue().isEmpty() || !Collections.disjoint(entry.getValue(), right)){
				mapFeedRight.put(entry.getKey(), entry.getValue());				
			}
		}
		if(mapFeedRight == null || mapFeedRight.isEmpty()) {
			return null;
		}
		
		return this.repository.getListItemRss(portalControllerContext, mapFeedRight, Integer.parseInt(settings.getNbItems()));         
    }
	
    /**
     * {@inheritDoc}
     */
	@Override
    public void save(PortalControllerContext portalControllerContext, RssSettings settings) throws PortletException {
        // Window
        PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        Map<List<String>, List<String>> map = new HashMap<>();
        if(settings.getMapFeeds() != null) {
        	for (HashMap.Entry<List<String>, List<String>> entry : settings.getMapFeeds().entrySet()) {
                map.put(entry.getKey(), entry.getValue());
              }        	
        }
        
        // Search Title into list container
        List<Container> containers = settings.getContainers().getContainers();
        boolean ok = false;
        List<String> list = new ArrayList<String>();
        for(Container container: containers) {
        	for(Feed feed: container.getFeeds()) {
        		if(settings.getFlux().equalsIgnoreCase(feed.getId())) {
        			list.add(feed.getId());
        			list.add(feed.getTitle());
        			map.put(list, settings.getRights());
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
		getFeed(settings, window);
		
		return settings;
	}
	
	@Override
	public RssSettings getList(PortalControllerContext portalControllerContext) throws PortletException, IOException {
		// Window
		PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

		// Portlet settings
		RssSettings settings = this.applicationContext.getBean(RssSettings.class);

		// List Feeds View
		Containers containers = this.repository.getListFeedRss(portalControllerContext);
		settings.setContainers(containers);
		
		// List Rights
		getListRight(settings);
		
		// List Feeds
		getFeed(settings, window);
		
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

        Map<List<String>, List<String>> map = new HashMap<>();
        boolean del = false;
        if(settings.getMapFeeds() != null) {
			for (HashMap.Entry<List<String>, List<String>> entry : settings.getMapFeeds().entrySet()) {
				for (String feed : entry.getKey()) {
					if (feed.equalsIgnoreCase(id)) {
						del = true;
						break;
					}
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
	}

	@Override
	public void mod(PortalControllerContext portalControllerContext, RssSettings settings) throws PortletException {
		// Window
		PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        Map<List<String>, List<String>> map = new HashMap<>();
        boolean mod = false;
        if(settings.getMapFeeds() != null) {
        	for (HashMap.Entry<List<String>, List<String>> entry : settings.getMapFeeds().entrySet()) {
                for(String feed: entry.getKey()) {
                	if(feed.equalsIgnoreCase(settings.getFeeds().get(0))){
                		mod = true;
                	}
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
	}	

	public void getFeed(RssSettings settings, PortalWindow window) {
        String json = window.getProperty(FEEDS_WINDOW_PROPERTY);
        
        if(json != null && !json.isEmpty() && !json.equalsIgnoreCase("{}")) {
            JSONArray items = JSONArray.fromObject(json);
            
            JSONArray jArray = (JSONArray) items;
            List<String> feeds = new ArrayList<String>();      
            List<String> rights = new ArrayList<String>();
            Map<List<String>, List<String>> map = new HashMap<>();
            if (jArray != null && !jArray.isEmpty()) {
    			for (int i = 0; i < jArray.size(); i++) {
    				JSONObject home = jArray.getJSONObject(i);
    				if (home.containsKey("id")) {
    					feeds.add(home.getString("id"));
    				}
    				if (home.containsKey("displayName")) {
    					feeds.add(home.getString("displayName"));
    				}
    				if (home.containsKey("rights")) {
    					String val = home.getString("rights").replace("[", "");
    					val = val.replace("]", "");
    					val = val.replaceAll("\"","");
    					List<String> strings = Arrays.asList(val.split(","));
    					rights.addAll(strings);
    					map.put(feeds, rights);
    					rights = new ArrayList<String>();
    					feeds = new ArrayList<String>();
    				}
    			}
    		}
            settings.setMapFeeds(map);
            settings.setFeeds(feeds); 
            settings.setRights(rights);
        }
	}	
	
	public static JSONArray setJSONArray(RssSettings settings) {
        // Message JSON object
        JSONObject id = new JSONObject();
        JSONObject displayName = new JSONObject();
        JSONObject rights = new JSONObject();
        JSONArray items = new JSONArray();
        for(HashMap.Entry<List<String>, List<String>> entry : settings.getMapFeeds().entrySet()){
        	int i = 0;
            for(String feed :entry.getKey()) {
            	if(i == 0) {
                	id.put("id", feed);            		
                    items.add(id);
            	} else {
                    displayName.put("displayName", feed);
                    items.add(displayName);
            	}
            	i++;
            }
            rights.put("rights", entry.getValue());
            items.add(rights);;
        }	
        return items; 
	}	

	public void getListRight(RssSettings settings) throws IOException {
		// List Rights in rights.properties
        // Load properties
		Properties properties = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException(PROPERTIES_FILE_NAME);
        }		
        
		ArrayList<String> prop2 = new ArrayList<String>(); 
		properties.forEach((k, v) -> prop2.add(v.toString()));
		settings.setRightsDisplay(prop2);		
	}
}
