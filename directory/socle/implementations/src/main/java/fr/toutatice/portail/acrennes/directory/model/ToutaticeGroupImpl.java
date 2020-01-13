package fr.toutatice.portail.acrennes.directory.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import java.util.List;

/**
 * Toutatice group implementation.
 *
 * @author Cédric Krommenhoek
 * @see ToutaticeGroup
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Primary
@Entry(objectClasses = "groupOfNames")
public final class ToutaticeGroupImpl implements ToutaticeGroup {

    /**
     * DN.
     */
    @Id
    private Name dn;

    /**
     * CN.
     */
    @Attribute
    private String cn;

    /**
     * Members.
     */
    @Attribute(name = "member")
    private List<Name> members;


    /**
     * Constructor.
     */
    public ToutaticeGroupImpl() {
        super();
    }


    @Override
    public Name getDn() {
        return this.dn;
    }


    @Override
    public String getCn() {
        return this.cn;
    }


    @Override
    public void setCn(String cn) {
        this.cn = cn;
    }


    @Override
    public List<Name> getMembers() {
        return this.members;
    }


    @Override
    public void setMembers(List<Name> members) {
        this.members = members;
    }

}
