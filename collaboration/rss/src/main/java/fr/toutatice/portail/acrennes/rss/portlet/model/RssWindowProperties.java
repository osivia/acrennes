package fr.toutatice.portail.acrennes.rss.portlet.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RssWindowProperties {

    private int nbItems;

    private RssView view;

    private List<RssWindowPropertiesFeed> feeds;


    public int getNbItems() {
        return nbItems;
    }

    public void setNbItems(int nbItems) {
        this.nbItems = nbItems;
    }

    public RssView getView() {
        return view;
    }

    public void setView(RssView view) {
        this.view = view;
    }

    public List<RssWindowPropertiesFeed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<RssWindowPropertiesFeed> feeds) {
        this.feeds = feeds;
    }
}
