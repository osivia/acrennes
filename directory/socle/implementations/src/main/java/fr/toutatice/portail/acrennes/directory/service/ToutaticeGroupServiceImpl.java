package fr.toutatice.portail.acrennes.directory.service;

import fr.toutatice.portail.acrennes.directory.dao.ToutaticeGroupDao;
import fr.toutatice.portail.acrennes.directory.model.ToutaticeGroup;
import org.osivia.directory.v2.service.GroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.List;

/**
 * Toutatice group service implementation.
 *
 * @author Cédric Krommenhoek
 * @see GroupServiceImpl
 * @see ToutaticeGroupService
 */
@Service
@Primary
public class ToutaticeGroupServiceImpl extends GroupServiceImpl implements ToutaticeGroupService {

    /**
     * Application context.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Toutatice group DAO.
     */
    @Autowired
    private ToutaticeGroupDao dao;


    /**
     * Constructor.
     */
    public ToutaticeGroupServiceImpl() {
        super();
    }


    @Override
    public ToutaticeGroup getEmptyGroup() {
        return this.applicationContext.getBean(ToutaticeGroup.class);
    }


    @Override
    public ToutaticeGroup get(Name dn) {
        return this.dao.get(dn);
    }


    @Override
    public ToutaticeGroup get(String cn) {
        // Search criteria
        ToutaticeGroup criteria = this.getEmptyGroup();
        criteria.setCn(cn);

        // Search results
        List<ToutaticeGroup> results = this.dao.find(criteria);

        // Toutatice group
        ToutaticeGroup result;
        if ((results != null) && (results.size() == 1)) {
            result = results.get(0);
        } else {
            result = null;
        }

        return result;
    }


    @Override
    public List<ToutaticeGroup> search(ToutaticeGroup criteria) {
        return this.dao.find(criteria);
    }

}
