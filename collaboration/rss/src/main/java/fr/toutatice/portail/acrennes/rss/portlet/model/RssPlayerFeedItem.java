package fr.toutatice.portail.acrennes.rss.portlet.model;

import java.util.Date;

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
     * Feed Link.
     */
    private String link;
    /**
     * Description.
     */
    private String description;
    /**
     * Picture URL.
     */
    private String pictureUrl;
    /**
     * Date.
     */
    private Date pubDate;
    /**
     * Feed display name.
     */
    private String feedDisplayName;


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


    public String getLink() {
        return link;
    }


    public void setLink(String link) {
        this.link = link;
    }


    public Date getPubDate() {
        return pubDate;
    }


    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getFeedDisplayName() {
        return feedDisplayName;
    }

    public void setFeedDisplayName(String feedDisplayName) {
        this.feedDisplayName = feedDisplayName;
    }
}
