package uk.gov.defra.capd.buildstatus.service;

import com.sun.jersey.api.client.Client;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import uk.gov.defra.capd.buildstatus.service.core.healthcheck.TeamCity;
import uk.gov.defra.capd.buildstatus.service.domain.*;
import uk.gov.defra.capd.buildstatus.service.health.TeamCityHealthCheck;
import uk.gov.defra.capd.buildstatus.service.resources.BuildStatusResource;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class DataLoadServiceApplication extends Application<DataLoadServiceConfiguration> {
    public static void main(String[] args) throws Exception {
        new DataLoadServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "buildstatus-service";
    }

    @Override
    public void initialize(Bootstrap<DataLoadServiceConfiguration> bootstrap) {

    }

    @Override
    public void run(DataLoadServiceConfiguration configuration, Environment environment) throws ClassNotFoundException {
        final Client jerseyClient = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration()).build("Team City Client");
        final TeamCity teamCity = new TeamCity(configuration.getTeamCityUri(), jerseyClient, configuration.getBasicAuth());
        environment.healthChecks().register("Team City", new TeamCityHealthCheck(teamCity));
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("crossOriginRequsts", CrossOriginFilter.class);
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        environment.jersey().register(new BuildStatusResource(bootstrapPersonService(jerseyClient, configuration.getTeamCityUri(), configuration.getBasicAuth())));
    }

    private BuildStatusService bootstrapPersonService(Client jerseyClient, String teamCityUri, String basicAuth) {
        final CapBuildsMapperService capBuildsMapperService = new CapBuildsMapperServiceImpl();
        final TeamCityProjectService teamCityProjectService = new TeamCityProjectServiceImpl(jerseyClient, teamCityUri, basicAuth);
        return new BuildStatusServiceImpl(teamCityProjectService, capBuildsMapperService);
    }
}
