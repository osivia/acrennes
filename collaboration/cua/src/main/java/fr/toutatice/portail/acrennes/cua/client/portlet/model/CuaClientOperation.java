package fr.toutatice.portail.acrennes.cua.client.portlet.model;

import org.springframework.http.HttpMethod;

/**
 * CUA client operations enumeration.
 *
 * @author Cédric Krommenhoek
 */
public enum CuaClientOperation {

    /**
     * Check if catalog exists.
     */
    IS_CATALOG_EXISTS("/api/v1/catalogues/%s", HttpMethod.GET),
    /**
     * Create catalog.
     */
    CREATE_CATALOG("/api/v1/catalogues/%s", HttpMethod.PUT),
    /**
     * Get catalog applications.
     */
    GET_APPLICATIONS("/api/v1/catalogues/%s/applications", HttpMethod.GET),
    /**
     * Get catalog applications identifiers.
     */
    GET_APPLICATIONS_IDENTIFIERS("/api/v1/catalogues/%s/applications/_ids", HttpMethod.GET),
    /**
     * Reorder catalog applications.
     */
    REORDER_APPLICATIONS("/api/v1/catalogues/%s/applications/_ids", HttpMethod.PUT),
    /**
     * Check if application is starred.
     */
    IS_APPLICATION_STARRED("/api/v1/catalogues/%s/applications/%s/favori", HttpMethod.GET),
    /**
     * Set application starred status.
     */
    SET_APPLICATION_STARRED("/api/v1/catalogues/%s/applications/%s/favori", HttpMethod.PUT),
    /**
     * Get catalog starred applications.
     */
    GET_STARRED_APPLICATIONS("/api/v1/catalogues/%s/favoris", HttpMethod.GET),
    /**
     * Get catalog starred applications identifiers.
     */
    GET_STARRED_APPLICATIONS_IDENTIFIERS("/api/v1/catalogues/%s/favoris/_ids", HttpMethod.GET),
    /**
     * Reorder catalog starred applications.
     */
    REORDER_STARRED_APPLICATIONS("/api/v1/catalogues/%s/favoris/_ids", HttpMethod.PUT),
    /**
     * Get health.
     */
    GET_HEALTH("/api/v1/health", HttpMethod.GET),
    /**
     * Get catalog synchronization.
     */
    GET_SYNCHRONIZATION("/api/v1/catalogues/%s/sync", HttpMethod.GET),
    /**
     * Synchronize catalog.
     */
    SYNCHRONIZE("/api/v1/catalogues/%s/sync", HttpMethod.POST);


    /**
     * Path.
     */
    private final String path;
    /**
     * HTTP method.
     */
    private final HttpMethod method;


    /**
     * Constructor.
     *
     * @param path   path
     * @param method HTTP method
     */
    CuaClientOperation(String path, HttpMethod method) {
        this.path = path;
        this.method = method;
    }


    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

}
