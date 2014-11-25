package uk.gov.defra.capd.buildstatus.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class DataLoadServiceConfiguration extends Configuration {
    @NotEmpty
    private String teamCityUri;

    @NotEmpty
    private String basicAuth;

    @Valid
    @NotNull
    @JsonProperty
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClient;
    }

    @JsonProperty
    public String getTeamCityUri() {
        return teamCityUri;
    }

    @JsonProperty
    public void setTeamCityUri(String teamCityUri) {
        this.teamCityUri = teamCityUri;
    }

    @JsonProperty
    public String getBasicAuth() {
        return basicAuth;
    }

    @JsonProperty
    public void setBasicAuth(String basicAuth) {
        this.basicAuth = basicAuth;
    }
}