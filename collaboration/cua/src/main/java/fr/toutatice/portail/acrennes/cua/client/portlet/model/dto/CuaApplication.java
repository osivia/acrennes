package fr.toutatice.portail.acrennes.cua.client.portlet.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * CUA application.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CuaApplication {

    @JsonProperty("contributor")
    private String contributor;

    @JsonProperty("coverage")
    private String coverage;

    @JsonProperty("creator")
    private String creator;

    @JsonProperty("date")
    private String date;

    @JsonProperty("description")
    private String description;

    @JsonProperty("extensionGAR")
    private CuaExtensionGar extensionGar;

    @JsonProperty("extensionToutatice")
    private CuaExtensionToutatice extensionToutatice;

    @JsonProperty("format")
    private String format;

    @JsonProperty("hubMetadata")
    private CuaHubMetadata hubMetadata;

    @JsonProperty("identifier")
    private String identifier;

    @JsonProperty("language")
    private String language;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("relation")
    private String relation;

    @JsonProperty("rights")
    private String rights;

    @JsonProperty("source")
    private String source;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("title")
    private String[] title;

    @JsonProperty("type")
    private String type;

    @JsonProperty("vignette")
    private String vignette;


    /**
     * Constructor.
     */
    public CuaApplication() {
        super();
    }


    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CuaExtensionGar getExtensionGar() {
        return extensionGar;
    }

    public void setExtensionGar(CuaExtensionGar extensionGar) {
        this.extensionGar = extensionGar;
    }

    public CuaExtensionToutatice getExtensionToutatice() {
        return extensionToutatice;
    }

    public void setExtensionToutatice(CuaExtensionToutatice extensionToutatice) {
        this.extensionToutatice = extensionToutatice;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public CuaHubMetadata getHubMetadata() {
        return hubMetadata;
    }

    public void setHubMetadata(CuaHubMetadata hubMetadata) {
        this.hubMetadata = hubMetadata;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVignette() {
        return vignette;
    }

    public void setVignette(String vignette) {
        this.vignette = vignette;
    }
}
