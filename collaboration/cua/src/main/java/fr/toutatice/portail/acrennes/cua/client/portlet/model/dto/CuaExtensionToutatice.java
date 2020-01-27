package fr.toutatice.portail.acrennes.cua.client.portlet.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * CUA extension Toutatice.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CuaExtensionToutatice {

    @JsonProperty("categorieCaisseDepot")
    private String[] categorieCaisseDepot;

    @JsonProperty("managerDelegue")
    private String[] managerDelegue;

    @JsonProperty("managerNatif")
    private String[] managerNatif;

    @JsonProperty("organisationLiee")
    private String[] organisationLiee;

    @JsonProperty("proprietaire")
    private String[] proprietaire;

    @JsonProperty("propriete")
    private String[] propriete;

    @JsonProperty("rneCaisseDepot")
    private String rneCaisseDepot;

    @JsonProperty("urlDeconnexion")
    private String[] urlDeconnexion;


    /**
     * Constructor.
     */
    public CuaExtensionToutatice() {
        super();
    }


    public String[] getCategorieCaisseDepot() {
        return categorieCaisseDepot;
    }

    public void setCategorieCaisseDepot(String[] categorieCaisseDepot) {
        this.categorieCaisseDepot = categorieCaisseDepot;
    }

    public String[] getManagerDelegue() {
        return managerDelegue;
    }

    public void setManagerDelegue(String[] managerDelegue) {
        this.managerDelegue = managerDelegue;
    }

    public String[] getManagerNatif() {
        return managerNatif;
    }

    public void setManagerNatif(String[] managerNatif) {
        this.managerNatif = managerNatif;
    }

    public String[] getOrganisationLiee() {
        return organisationLiee;
    }

    public void setOrganisationLiee(String[] organisationLiee) {
        this.organisationLiee = organisationLiee;
    }

    public String[] getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String[] proprietaire) {
        this.proprietaire = proprietaire;
    }

    public String[] getPropriete() {
        return propriete;
    }

    public void setPropriete(String[] propriete) {
        this.propriete = propriete;
    }

    public String getRneCaisseDepot() {
        return rneCaisseDepot;
    }

    public void setRneCaisseDepot(String rneCaisseDepot) {
        this.rneCaisseDepot = rneCaisseDepot;
    }

    public String[] getUrlDeconnexion() {
        return urlDeconnexion;
    }

    public void setUrlDeconnexion(String[] urlDeconnexion) {
        this.urlDeconnexion = urlDeconnexion;
    }
}
