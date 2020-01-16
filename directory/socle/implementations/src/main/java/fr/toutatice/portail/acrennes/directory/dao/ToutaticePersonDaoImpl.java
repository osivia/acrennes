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

    /** LDAP base. */
    private static final String LDAP_BASE = System.getProperty("ldap.base");


    /**
     * LDAP template.
     */
    @Autowired
    private LdapTemplate template;


    /**
     * Constructor.
     */
    public ToutaticePersonDaoImpl() {
        super();
    }


    @Override
    public ToutaticePerson getPerson(Name dn) {
        return this.template.findByDn(dn, ToutaticePerson.class);
    }


    @Override
    public Name getBaseDn() {
        LdapNameBuilder ldapNameBuilder = LdapNameBuilder.newInstance(LDAP_BASE);
        ldapNameBuilder.add("ou=personnes");
        return ldapNameBuilder.build();
    }


    @Override
    public Name buildDn(String uid) {
        Name baseDn = this.getBaseDn();
        LdapNameBuilder ldapNameBuilder = LdapNameBuilder.newInstance(baseDn);
        ldapNameBuilder.add("uid", uid);
        return ldapNameBuilder.build();
    }

}
