package fr.toutatice.portail.acrennes.directory.service;

import fr.toutatice.portail.acrennes.directory.model.ToutaticeGroup;
import org.osivia.portal.api.directory.v2.service.GroupService;

import javax.naming.Name;
import java.util.List;

/**
 * Toutatice group service interface.
 *
 * @author Cédric Krommenhoek
 * @see GroupService
 */
public interface ToutaticeGroupService extends GroupService {

    /**
     * Get empty Toutatice group.
     *
     * @return Toutatice group
     */
    @Override
    ToutaticeGroup getEmptyGroup();


    /**
     * Get Toutatice group.
     *
     * @param dn Toutatice group DN
     * @return Toutatice group
     */
    @Override
    ToutaticeGroup get(Name dn);


    /**
     * Get Toutatice group.
     *
     * @param cn Toutatice group CN
     * @return Toutatice group
     */
    @Override
    ToutaticeGroup get(String cn);


    /**
     * Search Toutatice groups by criteria.
     *
     * @param criteria search criteria
     * @return Toutatice groups
     */
    List<ToutaticeGroup> search(ToutaticeGroup criteria);

}
