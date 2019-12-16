package fr.toutatice.portail.acrennes.cua.client.portlet.repository;

import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaApplication;
import org.osivia.portal.api.context.PortalControllerContext;

import javax.portlet.PortletException;
import java.util.SortedSet;

/**
 * CUA client portlet repository interface.
 *
 * @author Cédric Krommenhoek
 */
public interface CuaClientRepository {

    SortedSet<CuaApplication> getApplications(PortalControllerContext portalControllerContext) throws PortletException;

}
