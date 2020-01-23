package fr.toutatice.portail.acrennes.cua.client.portlet.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * CUA identity vector.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CuaIdentityVector {

    @JsonProperty("arenaId")
    private String arenaId;

    @JsonProperty("garEntId")
    private String garEntId;

    @JsonProperty("garId")
    private String garId;

    @JsonProperty("network")
    private String network;

    @JsonProperty("toutaticeId")
    private String toutaticeId;

    @JsonProperty("uai")
    private String[] uai;


    /**
     * Constructor.
     */
    public CuaIdentityVector() {
        super();
    }


    public String getArenaId() {
        return arenaId;
    }

    public void setArenaId(String arenaId) {
        this.arenaId = arenaId;
    }

    public String getGarEntId() {
        return garEntId;
    }

    public void setGarEntId(String garEntId) {
        this.garEntId = garEntId;
    }

    public String getGarId() {
        return garId;
    }

    public void setGarId(String garId) {
        this.garId = garId;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getToutaticeId() {
        return toutaticeId;
    }

    public void setToutaticeId(String toutaticeId) {
        this.toutaticeId = toutaticeId;
    }

    public String[] getUai() {
        return uai;
    }

    public void setUai(String[] uai) {
        this.uai = uai;
    }
}
