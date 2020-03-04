package fr.toutatice.portail.acrennes.layout.selector.portlet.repository;

import fr.toutatice.portail.acrennes.directory.service.ToutaticeGroupService;
import org.apache.commons.lang.StringUtils;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.directory.v2.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.portlet.PortletException;
import java.util.List;

/**
 * Layout selector portlet repository implementation.
 *
 * @author Cédric Krommenhoek
 * @see LayoutSelectorRepository
 */
@Repository
public class LayoutSelectorRepositoryImpl implements LayoutSelectorRepository {

    /**
     * Group service.
     */
    @Autowired
    private ToutaticeGroupService groupService;


    /**
     * Constructor.
     */
    public LayoutSelectorRepositoryImpl() {
        super();
    }


    @Override
    public List<Group> searchGroups(PortalControllerContext portalControllerContext, String filter) throws PortletException {
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

}
