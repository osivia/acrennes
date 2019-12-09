package fr.toutatice.portail.acrennes.rss.portlet.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.PaginableDocuments;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.internationalization.Bundle;
import org.osivia.portal.api.internationalization.IBundleFactory;
import org.osivia.portal.api.windows.PortalWindow;
import org.osivia.portal.api.windows.WindowFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

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
    public ItemRepository repository;
    
    /** Application context. */
    @Autowired
    protected ApplicationContext applicationContext;      
    
    /** logger */
	protected static final Log logger = LogFactory.getLog(ItemServiceImpl.class);
	
    /** Nb Items window property. */
    private static final String NBITEMS_WINDOW_PROPERTY = "template.nbItems";

    /** View RSS window property (slider, liste). */
    private static final String VIEW_RSS_WINDOW_PROPERTY = "template.viewRss";    
	
    /** Select2 results page size. */
	public final static int SELECT2_RESULTS_PAGE_SIZE = 10;    

	/** Bundle factory. */
	@Autowired
	private IBundleFactory bundleFactory;    
    
    /**
     * {@inheritDoc}
     */
    public List<ItemRssModel> getListItem(PortalControllerContext portalControllerContext) throws PortletException {
        return this.repository.getListItemRss(portalControllerContext, null);        
    }
	
	@Override
	public Document getCurrentDocument(PortalControllerContext portalControllerContext) throws PortletException {
		// TODO Auto-generated method stub
		return null;
	}	
	
    /**
     * {@inheritDoc}
     */
    @Override
    public void save(PortalControllerContext portalControllerContext, RssSettings settings) throws PortletException {
        // Window
        PortalWindow window = WindowFactory.getWindow(portalControllerContext.getRequest());

        // Nb Items
        window.setProperty(NBITEMS_WINDOW_PROPERTY, settings.getNbItems());
        
        // View Rss
        window.setProperty(VIEW_RSS_WINDOW_PROPERTY, settings.getViewRss());
    }

	@Override
	public RssSettings getSettings(PortalControllerContext portalControllerContext) throws PortletException {
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
		
		// List Feeds View
//		Containers containers = this.repository.getListFeedRss(portalControllerContext);
//		settings.setContainers(containers);
		
		// List Right in Application.properties
        // Load properties
        Properties prop = null;
		try {
			prop = load("/resources/rights.properties");
		} catch (FileNotFoundException e) {
			System.out.println("Fichier non  trouvé: Vérifier que le fihier est présent dans resources/rights.properties");
		} catch (IOException e) {
			System.out.println("Problème de lecture du fichier de propriété Rights.");
		}
		ArrayList<String> prop2 = new ArrayList<String>(); 
		prop.forEach((k, v) -> prop2.add(v.toString()));
		settings.setRights(prop2);
		
		return settings;
	}
	
	/**
	 * Charge la liste des propriétés contenu dans le fichier spécifié
	 *
	 * @param filename le fichier contenant les propriétés
	 * @return un objet Properties contenant les propriétés du fichier
	 */
	public static Properties load(String filename) throws IOException, FileNotFoundException {
		Properties properties = new Properties();

		FileInputStream input = new FileInputStream(filename);
		try {
			properties.load(input);
			return properties;
		} finally {
			input.close();
		}
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public JSONObject searchDocuments(PortalControllerContext portalControllerContext, String filter, int page) throws PortletException {
        // Portlet request
        PortletRequest request = portalControllerContext.getRequest();
        // Window
        PortalWindow window = WindowFactory.getWindow(request);
        // Internationalization bundle
        Bundle bundle = this.bundleFactory.getBundle(request.getLocale());

        // Base path
        String basePath = window.getProperty(BASE_PATH_PROPERTY);

        // Documents
        PaginableDocuments documents = this.repository.searchDocuments(portalControllerContext, basePath, filter, page - 1);


        // Total results
        int total = documents.getTotalSize();
        // JSON objects
        List<JSONObject> objects = new ArrayList<>(Math.min(ItemRepository.SELECT2_RESULTS_PAGE_SIZE, total));

        for (Document document : documents) {
            // Search result
            JSONObject object = new JSONObject();
            // Document properties
            Map<String, String> properties = this.repository.getDocumentProperties(portalControllerContext, document);

            for (Map.Entry<String, String> entry : properties.entrySet()) {
                object.put(entry.getKey(), entry.getValue());
            }

            objects.add(object);
        }


        // Results JSON object
        JSONObject results = new JSONObject();

        // Items JSON array
        JSONArray items = new JSONArray();

        // Message
        if ((page == 1) && CollectionUtils.isNotEmpty(objects)) {
            String message;
            if (total == 0) {
                message = bundle.getString("SELECT2_NO_RESULT");
            } else if (total == 1) {
                message = bundle.getString("SELECT2_ONE_RESULT");
            } else {
                message = bundle.getString("SELECT2_MULTIPLE_RESULTS", total);
            }
            JSONObject object = new JSONObject();
            object.put("message", message);
            items.add(object);
        }
        // Paginated results
        for (JSONObject object : objects) {
            items.add(object);
        }

        // Pagination informations
        results.put("page", page);
        results.put("pageSize", ItemRepository.SELECT2_RESULTS_PAGE_SIZE);

        results.put("items", items);
        results.put("total", total);


        return results;
    }
}
