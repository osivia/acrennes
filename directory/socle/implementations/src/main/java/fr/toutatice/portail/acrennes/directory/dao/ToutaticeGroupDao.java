package fr.toutatice.portail.acrennes.directory.dao;

import fr.toutatice.portail.acrennes.directory.model.ToutaticeGroup;
import org.osivia.directory.v2.dao.GroupDao;

import javax.naming.Name;
import java.util.List;

/**
 * Toutatice group DAO interface.
 *
 * @author Cédric Krommenhoek
 * @see GroupDao
 */
public interface ToutaticeGroupDao extends GroupDao {

    /**
     * Get Toutatice group.
     *
     * @param dn Toutatice group DN
     * @return Toutatice group
     */
    @Override
    ToutaticeGroup get(Name dn);


    /**
     * Find Toutatice groups.
     *
     * @param criteria search criteria
     * @return Toutatice groups
     */
    List<ToutaticeGroup> find(ToutaticeGroup criteria);

}
