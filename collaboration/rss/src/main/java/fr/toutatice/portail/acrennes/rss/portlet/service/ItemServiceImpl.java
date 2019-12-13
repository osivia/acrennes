package fr.toutatice.portail.acrennes.rss.portlet.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.portlet.PortletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.windows.PortalWindow;
import org.osivia.portal.api.windows.WindowFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import fr.toutatice.portail.acrennes.rss.portlet.model.Containers;
import fr.toutatice.portail.acrennes.rss.portlet.model.ItemRssModel;
import fr.toutatice.portail.acrennes.rss.portlet.model.RssSettings;
import fr.toutatice.portail.acrennes.rss.portlet.repository.ItemRepository;

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
    public ItemRepository repository;
    
    /** Application context. */
    @Autowired
    protected ApplicationContext applicationContext;      
    
    /** logger */
	protected static final Log logger = LogFactory.getLog(ItemServiceImpl.class);
	
    /** Nb Items window property. */
    private static final String NBITEMS_WINDOW_PROPERTY = "form.nbItems";

    /** Nb Items window property. */
    private static final String FEEDS_WINDOW_PROPERTY = "form.mapFeeds";    
    
    /** View RSS window property (slider, liste). */
    private static final String VIEW_RSS_WINDOW_PROPERTY = "form.viewRss";    
	
    /** Select2 results page size. */
	public final static int SELECT2_RESULTS_PAGE_SIZE = 10;    

    /** Default properties file name. */
    private static final String PROPERTIES_FILE_NAME = "rights.properties";	
	
    /**
     * {@inheritDoc}
     */
    public List<ItemRssModel> getListItem(PortalControllerContext portalControllerContext) throws PortletException {


		return this.repository.getListItemRss(portalControllerContext, null);        
    }
	
    /**
     * {@inheritDoc}
     */
	@Override
    public void save(PortalControllerContext portalControllerContext, RssSettings settings) throws PortletException {
        // Window
        PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<String>> map = new HashMap<>();
        if(settings.getMapFeeds() != null) {
        	for (HashMap.Entry<String, List<String>> entry : settings.getMapFeeds().entrySet()) {
                map.put(entry.getKey(), entry.getValue());
              }        	
        }
 
        map.put(settings.getTitle(), settings.getRights());
        
        String json=null;
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        window.setProperty(FEEDS_WINDOW_PROPERTY, json);        
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
		String json = window.getProperty(FEEDS_WINDOW_PROPERTY);
        if (json != null && !json.isEmpty()) {
    		ObjectMapper mapper = new ObjectMapper();
            // convert JSON string to Map
            @SuppressWarnings("unchecked")
    		Map<String, List<String>> map = mapper.readValue(json, Map.class); 	
    		settings.setMapFeeds(map);
        }
	
		return settings;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RssSettings getList(PortalControllerContext portalControllerContext) throws PortletException, IOException {

		// Window
		PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

		// Portlet settings
		RssSettings settings = this.applicationContext.getBean(RssSettings.class);

		// List Feeds View
		Containers containers = this.repository.getListFeedRss(portalControllerContext);
		settings.setContainers(containers);
		
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
		
		// List Feeds 
		String json = window.getProperty(FEEDS_WINDOW_PROPERTY);
        if (json != null && !json.isEmpty()) {
    		ObjectMapper mapper = new ObjectMapper();
            // convert JSON string to Map
    		Map<String, List<String>> map = mapper.readValue(json, Map.class); 	
    		settings.setMapFeeds(map);
        }	
		
		return settings;
	}

	@Override
	public void delFeeds(PortalControllerContext portalControllerContext, RssSettings settings, String id)
			throws PortletException {
		// Window
		PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<String>> map = new HashMap<>();
        if(settings.getMapFeeds() != null) {
        	for (HashMap.Entry<String, List<String>> entry : settings.getMapFeeds().entrySet()) {
                if(!entry.getKey().contains(id)) {
                	map.put(entry.getKey(), entry.getValue());
                }
              }        	
        }
        
        settings.setMapFeeds(map);
        String json=null;
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        window.setProperty(FEEDS_WINDOW_PROPERTY, json);     		
	}

	@Override
	public void mod(PortalControllerContext portalControllerContext, RssSettings settings) throws PortletException {
		// Window
		PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<String>> map = new HashMap<>();
        if(settings.getMapFeeds() != null) {
        	for (HashMap.Entry<String, List<String>> entry : settings.getMapFeeds().entrySet()) {
                if(entry.getKey().contains(settings.getTitle())) {
                	map.put(settings.getTitle(), settings.getRights());	
                }else {
                	map.put(entry.getKey(), entry.getValue());
                }
              }        	
        }
        
        settings.setMapFeeds(map);
        String json=null;
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        window.setProperty(FEEDS_WINDOW_PROPERTY, json);
	}	
	
	
}
