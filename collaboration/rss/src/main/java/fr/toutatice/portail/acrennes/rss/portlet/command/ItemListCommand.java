package fr.toutatice.portail.acrennes.rss.portlet.command;

import java.util.HashMap;
import java.util.List;

import org.nuxeo.ecm.automation.client.Constants;
import org.nuxeo.ecm.automation.client.OperationRequest;
import org.nuxeo.ecm.automation.client.Session;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilter;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilterContext;

/**
 * List Nuxeo command.
 *
 * @author Frédéric Boudan
 * @see INuxeoCommand
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemListCommand implements INuxeoCommand {

    
    private final HashMap<List<String>, List<String>> map;
	
	/**
	 * Constructor.
	 *
	 */
	public ItemListCommand(HashMap<List<String>, List<String>> map) {
		super();
        this.map = map;
	}
	
	@Override
	public Object execute(Session nuxeoSession) throws Exception {

		// Clause
		StringBuilder clause = new StringBuilder();
		clause.append("ecm:primaryType = 'RssItem' ");
		if(map != null && !map.isEmpty()) {
			int index = 0;
			for(HashMap.Entry<List<String>, List<String>> entry : map.entrySet()) {
				if(index == 0) {
					clause.append("AND rssi:syncId IN ('" + entry.getKey().get(0) + "'");	
				}else {
					clause.append(",'" + entry.getKey().get(0) + "'");
				}
				index++;
			}
			clause.append(")");
		}
        clause.append("ORDER BY rssi:pubDate DESC");

		String filteredRequest = NuxeoQueryFilter.addPublicationFilter(NuxeoQueryFilterContext.CONTEXT_LIVE, clause.toString());
		
		// Request
		OperationRequest request;
		request = nuxeoSession.newRequest("Document.Query");
		request.setHeader(Constants.HEADER_NX_SCHEMAS, "*");
		request.set("query", "SELECT * FROM Document WHERE " + filteredRequest);

		return request.execute();
	}

	@Override
	public String getId() {
		return null;
	}

}
