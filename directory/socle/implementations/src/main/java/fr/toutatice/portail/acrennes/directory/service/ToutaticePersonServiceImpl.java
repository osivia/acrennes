package fr.toutatice.portail.acrennes.directory.service;

import fr.toutatice.portail.acrennes.directory.dao.ToutaticePersonDao;
import fr.toutatice.portail.acrennes.directory.model.ToutaticePerson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osivia.directory.v2.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.Name;

/**
 * Toutatice person service implementation.
 *
 * @author Cédric Krommenhoek
 * @see PersonServiceImpl
 * @see ToutaticePersonService
 */
@Service
@Primary
public class ToutaticePersonServiceImpl extends PersonServiceImpl implements ToutaticePersonService {

    /** Log. */
    private final Log log;


    /**
     * Application context.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Toutatice person DAO.
     */
    @Autowired
    private ToutaticePersonDao dao;


    /**
     * Constructor.
     */
    public ToutaticePersonServiceImpl() {
        super();

        // Log
        this.log = LogFactory.getLog(this.getClass());
    }


    @Override
    public ToutaticePerson getEmptyPerson() {
        return this.applicationContext.getBean(ToutaticePerson.class);
    }


    @Override
    public ToutaticePerson getPerson(Name dn) {
        ToutaticePerson person;

        try {
            person = this.dao.getPerson(dn);

            // Avatar
            this.appendAvatar(person);
        } catch (NameNotFoundException e) {
            person = null;

            String message = String.format("Person with DN '%s' not found.", dn);
            this.log.warn(message);
        }

        return person;
    }


    @Override
    public ToutaticePerson getPerson(String uid) {
        Name dn = this.dao.buildDn(uid);
        return this.getPerson(dn);
    }

}
