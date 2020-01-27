package fr.toutatice.portail.acrennes.cua.client.portlet.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * CUA extension GAR.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CuaExtensionGar {

    @JsonProperty("domaineEnseignementURI")
    private String domaineEnseignementUri;

    @JsonProperty("idEditeur")
    private String idEditeur;

    @JsonProperty("idType")
    private String idType;

    @JsonProperty("niveauEducatifNom")
    private String niveauEducatifNom;

    @JsonProperty("niveauEducatifURI")
    private String niveauEducatifUri;

    @JsonProperty("nomSourceEtiquetteGar")
    private String nomSourceEtiquetteGar;

    @JsonProperty("typePedagogiqueURI")
    private String typePedagogiqueUri;

    @JsonProperty("typePresentationCode")
    private String typePresentationCode;

    @JsonProperty("typologieDocumentURI")
    private String typologieDocumentUri;


    /**
     * Constructor.
     */
    public CuaExtensionGar() {
        super();
    }


    public String getDomaineEnseignementUri() {
        return domaineEnseignementUri;
    }

    public void setDomaineEnseignementUri(String domaineEnseignementUri) {
        this.domaineEnseignementUri = domaineEnseignementUri;
    }

    public String getIdEditeur() {
        return idEditeur;
    }

    public void setIdEditeur(String idEditeur) {
        this.idEditeur = idEditeur;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getNiveauEducatifNom() {
        return niveauEducatifNom;
    }

    public void setNiveauEducatifNom(String niveauEducatifNom) {
        this.niveauEducatifNom = niveauEducatifNom;
    }

    public String getNiveauEducatifUri() {
        return niveauEducatifUri;
    }

    public void setNiveauEducatifUri(String niveauEducatifUri) {
        this.niveauEducatifUri = niveauEducatifUri;
    }

    public String getNomSourceEtiquetteGar() {
        return nomSourceEtiquetteGar;
    }

    public void setNomSourceEtiquetteGar(String nomSourceEtiquetteGar) {
        this.nomSourceEtiquetteGar = nomSourceEtiquetteGar;
    }

    public String getTypePedagogiqueUri() {
        return typePedagogiqueUri;
    }

    public void setTypePedagogiqueUri(String typePedagogiqueUri) {
        this.typePedagogiqueUri = typePedagogiqueUri;
    }

    public String getTypePresentationCode() {
        return typePresentationCode;
    }

    public void setTypePresentationCode(String typePresentationCode) {
        this.typePresentationCode = typePresentationCode;
    }

    public String getTypologieDocumentUri() {
        return typologieDocumentUri;
    }

    public void setTypologieDocumentUri(String typologieDocumentUri) {
        this.typologieDocumentUri = typologieDocumentUri;
    }
}
