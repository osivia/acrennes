package fr.toutatice.portail.acrennes.directory.service;

import fr.toutatice.portail.acrennes.directory.model.ToutaticePerson;
import org.osivia.directory.v2.service.PersonUpdateService;

import javax.naming.Name;

/**
 * Toutatice person service interface.
 *
 * @author Cédric Krommenhoek
 * @see PersonUpdateService
 */
public interface ToutaticePersonService extends PersonUpdateService {

    /**
     * Get empty Toutatice person.
     *
     * @return Toutatice person
     */
    @Override
    ToutaticePerson getEmptyPerson();


    /**
     * Get Toutatice person.
     *
     * @param dn Toutatice person DN
     * @return Toutatice person
     */
    @Override
    ToutaticePerson getPerson(Name dn);


    /**
     * Get Toutatice person.
     *
     * @param uid Toutatice person UID
     * @return Toutatice person
     */
    @Override
    ToutaticePerson getPerson(String uid);

}
