package uk.gov.defra.capd.buildstatus.service.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.defra.capd.buildstatus.service.domain.models.Breaker;
import uk.gov.defra.capd.buildstatus.service.domain.models.Build;
import uk.gov.defra.capd.buildstatus.service.domain.models.Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TeamCityProjectServiceImpl implements TeamCityProjectService {
    private static Logger LOGGER = LoggerFactory.getLogger(TeamCityProjectServiceImpl.class);
    private final Client jerseyClient;
    private final String teamCityUri;
    private final String basicAuth;

    public TeamCityProjectServiceImpl(Client jerseyClient, String teamCityUri, String basicAuth) {
        this.jerseyClient = jerseyClient;
        this.teamCityUri = teamCityUri;
        this.basicAuth = basicAuth;
    }

    @Override
    public void getProjectsWithBrokenBuilds(String rootProjectId, Project parentProject) throws IOException {
        this.loadProjects(rootProjectId, parentProject);
    }

    private void loadProjects(String currentProjectName, Project parentProject) throws IOException {
        final String request = this.teamCityUri + "/httpAuth/app/rest/projects/id:" + currentProjectName;
        JsonNode rootNode = getJsonResponse(request);
        Project currentProject = new Project(rootNode.path("name").asText(), rootNode.path("description").asText());
        parentProject.getProjects().add(currentProject);

        LOGGER.info("Processing : " + currentProject.getName());

        JsonNode projectsNode = rootNode.path("projects");
        JsonNode projectNode = projectsNode.path("project");
        Iterator<JsonNode> elements = projectNode.elements();

        currentProject.getBrokenBuilds().addAll(getBuildsForProject(rootNode));

        while (elements.hasNext()) {
            JsonNode project = elements.next();
            if (!project.path("name").asText().equals("BT")) {
                loadProjects(project.path("id").asText(), currentProject);
            }
        }
    }

    private List<Build> getBuildsForProject(JsonNode rootNode) {
        ArrayList<Build> brokeBuilds = new ArrayList<>();
        JsonNode buildTypesNode = rootNode.path("buildTypes");
        JsonNode buildTypeNode = buildTypesNode.path("buildType");
        Iterator<JsonNode> buildTypeElements = buildTypeNode.elements();

        while (buildTypeElements.hasNext()) {
            JsonNode build = buildTypeElements.next();
            try {
                Build failedBuild = findFailedBuilds(build.path("id").asText(), build.path("name").asText());
                if (failedBuild != null) {
                    LOGGER.info("Failed Builds found : " + failedBuild.getName());
                    brokeBuilds.add(failedBuild);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return brokeBuilds;
    }

    private Build findFailedBuilds(String buildTypeId, String buildName) throws IOException {
        final String request = this.teamCityUri + "/httpAuth/app/rest/buildTypes/id:" + buildTypeId + "/builds/";
        JsonNode rootNode = getJsonResponse(request);
        JsonNode buildNode = rootNode.path("build");
        Iterator<JsonNode> buildElements = buildNode.elements();

        if (buildElements.hasNext()) {
            JsonNode build = buildElements.next();
            if (build.path("status").asText().equals("FAILURE")) {
                Build brokeBuild = new Build(buildName, "");
                brokeBuild.getBreakers().addAll(getBuildChanges(build.path("id").asText()));
                return brokeBuild;
            }
        }
        return null;
    }

    private List<Breaker> getBuildChanges(String buildId) throws IOException {
        ArrayList<Breaker> breakers = new ArrayList<Breaker>();
        final String request = this.teamCityUri + "/httpAuth/app/rest/changes?locator=build:(id:" + buildId + ")";
        JsonNode rootNode = getJsonResponse(request);
        JsonNode changeNode = rootNode.path("change");
        Iterator<JsonNode> changeElements = changeNode.elements();

        while (changeElements.hasNext()) {
            JsonNode build = changeElements.next();
            String changeDetailRequest = "http://defraci.kainos.com/httpAuth/app/rest/changes/id:" + build.path("id").asText();
            JsonNode changeDetailNode = getJsonResponse(changeDetailRequest);
            breakers.add(new Breaker(changeDetailNode.path("username").asText(), changeDetailNode.path("comment").asText()));
        }
        return breakers;
    }

    /**
     * Sends request to server and returns JSON object. Note: Uses hardcoded Basic Auth, connecting as R.Hanratty
     * @param requestUrl URL to request
     * @return
     * @throws IOException
     */
    private JsonNode getJsonResponse(String requestUrl) throws IOException {
        final ClientResponse response = jerseyClient.resource(requestUrl).header("Accept", "application/json").header("Authorization", this.basicAuth).get(ClientResponse.class);
        String responseText = response.getEntity(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(responseText);
    }
}