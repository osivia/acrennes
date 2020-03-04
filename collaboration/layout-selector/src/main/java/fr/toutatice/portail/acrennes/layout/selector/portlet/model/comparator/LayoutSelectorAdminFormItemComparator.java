package fr.toutatice.portail.acrennes.layout.selector.portlet.model.comparator;

import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorAdminFormItem;
import org.springframework.stereotype.Component;

import java.util.Comparator;

/**
 * Layout selector administration form item comparator.
 *
 * @author Cédric Krommenhoek
 * @see Comparator
 * @see LayoutSelectorAdminFormItem
 */
@Component
public class LayoutSelectorAdminFormItemComparator implements Comparator<LayoutSelectorAdminFormItem> {

    /**
     * Constructor.
     */
    public LayoutSelectorAdminFormItemComparator() {
        super();
    }


    @Override
    public int compare(LayoutSelectorAdminFormItem item1, LayoutSelectorAdminFormItem item2) {
        int result;

        if (item1 == null) {
            result = -1;
        } else if (item2 == null) {
            result = 1;
        } else {
            Integer order1 = item1.getOrder();
            Integer order2 = item2.getOrder();
            result = order1.compareTo(order2);
        }

        return result;
    }

}
