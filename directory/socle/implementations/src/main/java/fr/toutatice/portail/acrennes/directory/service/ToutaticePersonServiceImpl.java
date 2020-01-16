package fr.toutatice.portail.acrennes.directory.service;

import fr.toutatice.portail.acrennes.directory.dao.ToutaticePersonDao;
import fr.toutatice.portail.acrennes.directory.model.ToutaticePerson;
import org.osivia.directory.v2.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
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
    }


    @Override
    public ToutaticePerson getEmptyPerson() {
        return this.applicationContext.getBean(ToutaticePerson.class);
    }


    @Override
    public ToutaticePerson getPerson(Name dn) {
        return this.dao.getPerson(dn);
    }


    @Override
    public ToutaticePerson getPerson(String uid) {
        Name dn = this.dao.buildDn(uid);
        return this.getPerson(dn);
    }

}
