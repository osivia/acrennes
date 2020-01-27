package fr.toutatice.portail.acrennes.cua.client.portlet.model.comparator;

import fr.toutatice.portail.acrennes.cua.client.portlet.model.CuaClientApplication;
import org.springframework.stereotype.Component;

import java.util.Comparator;

/**
 * CUA client application comparator.
 *
 * @author Cédric Krommenhoek
 * @see Comparator
 * @see CuaClientApplication
 */
@Component
public class CuaClientApplicationComparator implements Comparator<CuaClientApplication> {

    /**
     * Constructor.
     */
    public CuaClientApplicationComparator() {
        super();
    }


    @Override
    public int compare(CuaClientApplication application1, CuaClientApplication application2) {
        return Integer.valueOf(application1.getOrder()).compareTo(Integer.valueOf(application2.getOrder()));
    }

}
