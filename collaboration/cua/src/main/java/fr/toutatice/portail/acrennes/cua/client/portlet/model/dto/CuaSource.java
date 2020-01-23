package fr.toutatice.portail.acrennes.cua.client.portlet.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * CUA source.
 *
 * @author Cédric Krommenhoek
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CuaSource {

    @JsonProperty("finished")
    private String finished;

    @JsonProperty("lastSuccess")
    private String lastSuccess;

    @JsonProperty("started")
    private String started;

    @JsonProperty("state")
    private CuaSynchronizationState state;


    /**
     * Constructor.
     */
    public CuaSource() {
        super();
    }


    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getLastSuccess() {
        return lastSuccess;
    }

    public void setLastSuccess(String lastSuccess) {
        this.lastSuccess = lastSuccess;
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
