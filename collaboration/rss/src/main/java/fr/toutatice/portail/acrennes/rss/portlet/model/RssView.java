package fr.toutatice.portail.acrennes.rss.portlet.model;

import org.apache.commons.lang.StringUtils;

/**
 * RSS view.
 */
public enum RssView {

    /**
     * List.
     */
    LIST,
    /**
     * Slider.
     */
    SLIDER;


    /**
     * Default view.
     */
    public static final RssView DEFAULT = LIST;


    /**
     * Internationalization key.
     */
    private final String key;


    RssView() {
        this.key = "RSS_VIEW_" + StringUtils.upperCase(this.name());
    }


    public static RssView fromId(String id) {
        RssView result = null;
        for (RssView value : RssView.values()) {
            if (StringUtils.equalsIgnoreCase(id, value.name())) {
                result = value;
            }
        }
        return result;
    }


    public String getId() {
        return this.name();
    }

    public String getKey() {
        return key;
    }
}
