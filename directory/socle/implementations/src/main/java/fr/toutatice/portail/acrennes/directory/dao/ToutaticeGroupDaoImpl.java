package fr.toutatice.portail.acrennes.directory.dao;

import fr.toutatice.portail.acrennes.directory.model.ToutaticeGroup;
import org.osivia.directory.v2.MappingHelper;
import org.osivia.directory.v2.dao.GroupDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Repository;

import javax.naming.Name;
import java.util.List;

/**
 * Toutatice group DAO implementation.
 *
 * @author Cédric Krommenhoek
 * @see GroupDaoImpl
 * @see ToutaticeGroupDao
 */
@Repository
@Primary
public class ToutaticeGroupDaoImpl extends GroupDaoImpl implements ToutaticeGroupDao {

    /**
     * LDAP template.
     */
    @Autowired
    private LdapTemplate template;


    /**
     * Constructor.
     */
    public ToutaticeGroupDaoImpl() {
        super();
    }


    @Override
    public ToutaticeGroup get(Name dn) {
        return this.template.findByDn(dn, ToutaticeGroup.class);
    }


    @Override
    public List<ToutaticeGroup> find(ToutaticeGroup criteria) {
        // LDAP query
        LdapQueryBuilder query = LdapQueryBuilder.query();
        query.base(System.getProperty("ldap.base"));

        // Filter
        Filter filter = MappingHelper.generateAndFilter(criteria);
        query.filter(filter);

        // Search results
        return this.template.find(query, ToutaticeGroup.class);
    }

}
