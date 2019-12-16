package fr.toutatice.portail.acrennes.cua.client.portlet.model.comparator;

import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaApplication;
import org.springframework.stereotype.Component;

import java.util.Comparator;

/**
 * CUA applications comparator.
 *
 * @author Cédric Krommenhoek
 * @see Comparator
 * @see CuaApplication
 */
@Component
public class CuaApplicationsComparator implements Comparator<CuaApplication> {

    /**
     * Constructor.
     */
    public CuaApplicationsComparator() {
        super();
    }


    @Override
    public int compare(CuaApplication application1, CuaApplication application2) {
        int result;

        if (application1 == null) {
            result = -1;
        } else if (application2 == null) {
            result = 1;
        } else {
            result = Integer.valueOf(application1.getOrder()).compareTo(Integer.valueOf(application2.getOrder()));
        }

        return result;
    }

}
