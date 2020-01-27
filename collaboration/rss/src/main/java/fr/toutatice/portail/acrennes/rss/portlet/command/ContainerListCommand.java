package fr.toutatice.portail.acrennes.rss.portlet.command;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.nuxeo.ecm.automation.client.Constants;
import org.nuxeo.ecm.automation.client.OperationRequest;
import org.nuxeo.ecm.automation.client.Session;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilter;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilterContext;

import java.util.List;

/**
 * List Nuxeo command.
 *
 * @author Frédéric Boudan
 * @see INuxeoCommand
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContainerListCommand implements INuxeoCommand {

	/** Feed identifiers. */
	private final List<String> feedIds;


	/**
	 * Constructor.
	 *
	 */
	public ContainerListCommand() {
		this(null);
	}


	public ContainerListCommand(List<String> feedIds) {
		super();
		this.feedIds = feedIds;
	}


	@Override
	public Object execute(Session nuxeoSession) throws Exception {

		// Clause
		StringBuilder clause = new StringBuilder();
		clause.append("ecm:primaryType = 'RssContainer' ");

		if (CollectionUtils.isNotEmpty(this.feedIds)) {
			clause.append("AND rssc:feeds/*/syncId IN ('");
			clause.append(StringUtils.join(this.feedIds, "', '"));
			clause.append("') ");
		}

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
