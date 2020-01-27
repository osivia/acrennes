package fr.toutatice.portail.acrennes.rss.portlet.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * RSS player feed item.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RssPlayerFeedItem {

    /**
     * Title.
     */
    private String title;
    /**
     * Description.
     */
    private String description;
    /**
     * Picture URL.
     */
    private String pictureUrl;


    /**
     * Constructor.
     */
    public RssPlayerFeedItem() {
        super();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
