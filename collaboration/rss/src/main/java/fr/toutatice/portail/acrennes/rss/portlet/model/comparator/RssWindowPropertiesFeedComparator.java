package fr.toutatice.portail.acrennes.rss.portlet.model.comparator;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import fr.toutatice.portail.acrennes.rss.portlet.model.RssWindowPropertiesFeed;

/**
 * Feed selector form item comparator.
 *
 * @author Frederic Boudan
 * @see Comparator
 * @see RssWindowPropertiesFeed
 */
@Component
public class RssWindowPropertiesFeedComparator implements Comparator<RssWindowPropertiesFeed>{

    /**
     * Constructor.
     */
    public RssWindowPropertiesFeedComparator() {
        super();
    }

    @Override
    public int compare(RssWindowPropertiesFeed item1, RssWindowPropertiesFeed item2) {
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
