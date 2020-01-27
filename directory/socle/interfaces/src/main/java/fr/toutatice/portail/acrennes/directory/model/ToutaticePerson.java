package fr.toutatice.portail.acrennes.directory.model;

import org.osivia.portal.api.directory.v2.model.Person;

/**
 * Toutatice person interface.
 *
 * @author Cédric Krommenhoek
 * @see Person
 */
public interface ToutaticePerson extends Person {

    /**
     * Get Toutatice person anonymized identifier.
     *
     * @return identifier
     */
    String getAnonymizedId();

}
