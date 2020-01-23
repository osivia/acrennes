package fr.toutatice.portail.acrennes.cua.client.portlet.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * CUA hub metadata.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CuaHubMetadata {

    @JsonProperty("derniereSynchronisation")
    private String derniereSynchronisation;

    @JsonProperty("etat")
    private String etat;

    @JsonProperty("favori")
    private boolean favori;

    @JsonProperty("idInterneCUA")
    private String idInterneCUA;

    @JsonProperty("premiereSynchronisation")
    private String premiereSynchronisation;

    @JsonProperty("rang")
    private int rang;

    @JsonProperty("rangFavori")
    private int rangFavori;


    /**
     * Constructor.
     */
    public CuaHubMetadata() {
        super();
    }


    public String getDerniereSynchronisation() {
        return derniereSynchronisation;
    }

    public void setDerniereSynchronisation(String derniereSynchronisation) {
        this.derniereSynchronisation = derniereSynchronisation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public boolean isFavori() {
        return favori;
    }

    public void setFavori(boolean favori) {
        this.favori = favori;
    }

    public String getIdInterneCUA() {
        return idInterneCUA;
    }

    public void setIdInterneCUA(String idInterneCUA) {
        this.idInterneCUA = idInterneCUA;
    }

    public String getPremiereSynchronisation() {
        return premiereSynchronisation;
    }

    public void setPremiereSynchronisation(String premiereSynchronisation) {
        this.premiereSynchronisation = premiereSynchronisation;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public int getRangFavori() {
        return rangFavori;
    }

    public void setRangFavori(int rangFavori) {
        this.rangFavori = rangFavori;
    }
}
