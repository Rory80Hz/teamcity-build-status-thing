package uk.gov.defra.capd.buildstatus.service.core.healthcheck;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import uk.gov.defra.capd.buildstatus.service.core.exception.TeamCityException;

public class TeamCity {
    private final String healthCheckUri;
    private final Client jerseyClient;
    private final String basicAuth;


    public TeamCity(String healthCheckUri, Client jerseyClient, String basicAuth) {
        this.healthCheckUri = healthCheckUri;
        this.jerseyClient = jerseyClient;
        this.basicAuth = basicAuth;
    }
    
    public void checkAlive() {
        WebResource webResource = this.jerseyClient.resource(healthCheckUri);
        ClientResponse response = webResource.type("application/json").header("Authorization", this.basicAuth).get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new TeamCityException("Team City Request Failed : HTTP error code : " + response.getStatus());
        }
    }
}
