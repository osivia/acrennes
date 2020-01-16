package fr.toutatice.portail.acrennes.directory.model;

import org.osivia.portal.api.urls.Link;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
@Primary
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entry(objectClasses = "ENTPerson")
public final class ToutaticePersonImpl implements ToutaticePerson {

    /**
     * LDAP base.
     */
    private static final String LDAP_BASE = System.getProperty("ldap.base");


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
     * SN.
     */
    @Attribute
    private String sn;

    /**
     * Display name.
     */
    @Attribute
    private String displayName;

    /**
     * Given name.
     */
    @Attribute
    private String givenName;

    /**
     * Mail.
     */
    @Attribute
    private String mail;

    /**
     * Title.
     */
    @Attribute
    private String title;

    /**
     * UID.
     */
    @Attribute
    private String uid;

    /**
     * Profiles.
     */
    @Attribute(name = "EntPersonProfils")
    private List<Name> profiles;

    /**
     * Avatar link.
     */
    @Transient
    private Link avatar;

    /**
     * External account indicator.
     */
    @Transient
    private Boolean external;

    /**
     * Account creation date.
     */
    @Transient
    private Date creationDate;

    /**
     * Account validity date.
     */
    @Transient
    private Date validity;

    /**
     * Last connection date.
     */
    @Transient
    private Date lastConnection;


    /**
     * Constructor.
     */
    public ToutaticePersonImpl() {
        super();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToutaticePersonImpl that = (ToutaticePersonImpl) o;
        return uid.equals(that.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }


    @Override
    public Name getDn() {
        return this.dn;
    }


    @Override
    public void setDn(Name dn) {
        this.dn = dn;
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
    public String getSn() {
        return this.sn;
    }


    @Override
    public void setSn(String sn) {
        this.sn = sn;
    }


    @Override
    public String getDisplayName() {
        return this.displayName;
    }


    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    @Override
    public String getGivenName() {
        return this.givenName;
    }


    @Override
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }


    @Override
    public String getMail() {
        return this.mail;
    }


    @Override
    public void setMail(String mail) {
        this.mail = mail;
    }


    @Override
    public String getTitle() {
        return this.title;
    }


    @Override
    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String getUid() {
        return this.uid;
    }


    @Override
    public void setUid(String uid) {
        this.uid = uid;
    }


    @Override
    public List<Name> getProfiles() {
        return this.profiles;
    }


    @Override
    public void setProfiles(List<Name> profiles) {
        this.profiles = profiles;
    }


    @Override
    public Link getAvatar() {
        return this.avatar;
    }


    @Override
    public void setAvatar(Link avatar) {
        this.avatar = avatar;
    }


    @Override
    public Boolean getExternal() {
        return this.external;
    }


    @Override
    public void setExternal(Boolean external) {
        this.external = external;
    }


    @Override
    public Date getCreationDate() {
        return this.creationDate;
    }


    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    @Override
    public Date getValidity() {
        return this.validity;
    }


    @Override
    public void setValidity(Date validity) {
        this.validity = validity;
    }


    @Override
    public Date getLastConnection() {
        return this.lastConnection;
    }


    @Override
    public void setLastConnection(Date lastConnection) {
        this.lastConnection = lastConnection;
    }


    @Override
    public Name buildBaseDn() {
        LdapNameBuilder ldapNameBuilder = LdapNameBuilder.newInstance(LDAP_BASE);
        ldapNameBuilder.add("ou=personnes");
        return ldapNameBuilder.build();
    }


    @Override
    public Name buildDn(String uid) {
        Name baseDn = this.buildBaseDn();
        LdapNameBuilder ldapNameBuilder = LdapNameBuilder.newInstance(baseDn);
        ldapNameBuilder.add("uid", uid);
        return ldapNameBuilder.build();
    }

}
