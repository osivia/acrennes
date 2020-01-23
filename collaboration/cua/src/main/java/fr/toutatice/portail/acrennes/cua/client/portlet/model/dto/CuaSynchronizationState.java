package fr.toutatice.portail.acrennes.cua.client.portlet.model.dto;

/**
 * CUA synchronization states enumeration.
 *
 * @author Cédric Krommenhoek
 */
public enum CuaSynchronizationState {

    NEVER_SYNCHRONIZED,

    WAITING,

    IN_PROGRESS,

    DONE,

    FAILED;

}
