package fr.toutatice.portail.acrennes.directory.dao;

import fr.toutatice.portail.acrennes.directory.model.ToutaticePerson;
import org.osivia.directory.v2.dao.PersonDao;

import javax.naming.Name;
import java.util.List;

/**
 * Toutatice person DAO interface.
 *
 * @author Cédric Krommenhoek
 * @see PersonDao
 */
public interface ToutaticePersonDao extends PersonDao {

    /**
     * Get Toutatice person.
     *
     * @param dn Toutatice person DN
     * @return Toutatice person
     */
    @Override
    ToutaticePerson getPerson(Name dn);


    /**
     * Get Toutatice person base DN.
     *
     * @return DN
     */
    Name getBaseDn();


    /**
     * Build Toutatice person DN.
     *
     * @param uid Toutatice person UID
     * @return Toutatice person DN
     */
    Name buildDn(String uid);

}
