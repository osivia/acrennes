package fr.toutatice.portail.acrennes.cua.client.portlet.model;

import java.util.List;

/**
 * CUA client form abstract super-class.
 *
 * @author Cédric Krommenhoek
 */
public abstract class AbstractCuaClientForm {

    /**
     * Catalog identifier.
     */
    private String catalogId;
    /**
     * Starred applications.
     */
    private List<CuaClientApplication> starredApplications;
    /**
     * Other applications.
     */
    private List<CuaClientApplication> otherApplications;


    /**
     * Constructor.
     */
    public AbstractCuaClientForm() {
        super();
    }


    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public List<CuaClientApplication> getStarredApplications() {
        return starredApplications;
    }

    public void setStarredApplications(List<CuaClientApplication> starredApplications) {
        this.starredApplications = starredApplications;
    }

    public List<CuaClientApplication> getOtherApplications() {
        return otherApplications;
    }

    public void setOtherApplications(List<CuaClientApplication> otherApplications) {
        this.otherApplications = otherApplications;
    }
}
