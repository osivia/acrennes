package fr.toutatice.portail.acrennes.directory.dao;

import fr.toutatice.portail.acrennes.directory.model.ToutaticePerson;
import org.osivia.directory.v2.dao.PersonDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Repository;

import javax.naming.Name;

/**
 * Toutatice person DAO implementation.
 *
 * @author Cédric Krommenhoek
 * @see PersonDaoImpl
 * @see ToutaticePersonDao
 */
@Repository
@Primary
public class ToutaticePersonDaoImpl extends PersonDaoImpl implements ToutaticePersonDao {

    /**
     * LDAP template.
     */
    @Autowired
    private LdapTemplate template;

    /**
     * Toutatice person sample.
     */
    @Autowired
    private ToutaticePerson sample;


    /**
     * Constructor.
     */
    public ToutaticePersonDaoImpl() {
        super();
    }


    @Override
    public ToutaticePerson getPerson(Name dn) {
        return this.template.findByDn(dn, this.sample.getClass());
    }


    @Override
    public Name getBaseDn() {
        return this.sample.buildBaseDn();
    }


    @Override
    public Name buildDn(String uid) {
        return this.sample.buildDn(uid);
    }

}
