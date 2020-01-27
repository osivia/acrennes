package fr.toutatice.portail.acrennes.rss.portlet.command;

import fr.toutatice.portail.cms.nuxeo.api.INuxeoCommand;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilter;
import fr.toutatice.portail.cms.nuxeo.api.NuxeoQueryFilterContext;
import org.apache.commons.lang.StringUtils;
import org.nuxeo.ecm.automation.client.Constants;
import org.nuxeo.ecm.automation.client.OperationRequest;
import org.nuxeo.ecm.automation.client.Session;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;


/**
 * Get RSS items Nuxeo command.
 *
 * @author Cédric Krommenhoek
 * @see INuxeoCommand
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetItemsCommand implements INuxeoCommand {

    /**
     * Feed identifiers.
     */
    private final List<String> identifiers;


    /**
     * Constructor.
     *
     * @param identifiers feed identifiers
     */
    public GetItemsCommand(List<String> identifiers) {
        super();
        this.identifiers = identifiers;
    }


    @Override
    public Object execute(Session nuxeoSession) throws Exception {
        // Clause
        StringBuilder clause = new StringBuilder();
        clause.append("ecm:primaryType = 'RssItem' ");
        clause.append("AND rssi:syncId IN ('");
        clause.append(StringUtils.join(this.identifiers, "', '"));
        clause.append("') ");
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
