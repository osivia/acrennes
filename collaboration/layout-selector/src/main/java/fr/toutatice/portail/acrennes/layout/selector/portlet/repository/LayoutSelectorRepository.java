package fr.toutatice.portail.acrennes.layout.selector.portlet.repository;

import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.directory.v2.model.Group;

import javax.portlet.PortletException;
import java.util.List;

/**
 * Layout selector portlet repository interface.
 *
 * @author Cédric Krommenhoek
 */
public interface LayoutSelectorRepository {

    /**
     * Search groups.
     *
     * @param portalControllerContext portal controller context
     * @param filter                  search filter
     */
    List<Group> searchGroups(PortalControllerContext portalControllerContext, String filter) throws PortletException;

}
