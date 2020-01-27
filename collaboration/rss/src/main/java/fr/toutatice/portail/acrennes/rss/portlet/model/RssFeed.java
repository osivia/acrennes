package fr.toutatice.portail.acrennes.rss.portlet.model;

/**
 * RSS feed interface.
 *
 * @author Cédric Krommenhoek
 */
public interface RssFeed {

    /**
     * Get identifier.
     *
     * @return identifier
     */
    String getId();


    /**
     * Set identifier
     *
     * @param id identifier
     */
    void setId(String id);


    /**
     * Get display name.
     *
     * @return display name
     */
    String getDisplayName();


    /**
     * Set display name.
     *
     * @param displayName display name
     */
    void setDisplayName(String displayName);


    /**
     * Get picture URL.
     *
     * @return URL
     */
    String getPictureUrl();


    /**
     * Set picture URL.
     *
     * @param url picture URL
     */
    void setPictureUrl(String url);

}
