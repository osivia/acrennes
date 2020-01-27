package fr.toutatice.portail.acrennes.cua.client.portlet.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * CUA synchronization.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CuaSynchronization {

    @JsonProperty("finished")
    private String finished;

    @JsonProperty("nextNotBefore")
    private String nextNotBefore;

    @JsonProperty("requested")
    private String requested;

    @JsonProperty("sources")
    private Map<String, CuaSource> sources;

    @JsonProperty("started")
    private String started;

    @JsonProperty("state")
    private CuaSynchronizationState state;


    /**
     * Constructor.
     */
    public CuaSynchronization() {
        super();
    }


    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getNextNotBefore() {
        return nextNotBefore;
    }

    public void setNextNotBefore(String nextNotBefore) {
        this.nextNotBefore = nextNotBefore;
    }

    public String getRequested() {
        return requested;
    }

    public void setRequested(String requested) {
        this.requested = requested;
    }

    public Map<String, CuaSource> getSources() {
        return sources;
    }

    public void setSources(Map<String, CuaSource> sources) {
        this.sources = sources;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public CuaSynchronizationState getState() {
        return state;
    }

    public void setState(CuaSynchronizationState state) {
        this.state = state;
    }
}
