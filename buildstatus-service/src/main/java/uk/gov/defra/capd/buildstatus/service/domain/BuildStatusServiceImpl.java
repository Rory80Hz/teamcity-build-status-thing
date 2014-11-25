package uk.gov.defra.capd.buildstatus.service.domain;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import uk.gov.defra.capd.buildstatus.service.domain.models.Project;
import uk.gov.defra.capd.buildstatus.service.domain.models.Status;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class BuildStatusServiceImpl implements BuildStatusService {
    private final LoadingCache<String,Project> cache;
    private final TeamCityProjectService teamCityProjectService;
    private final CapBuildsMapperService capBuildsMapperService;

    public BuildStatusServiceImpl(TeamCityProjectService teamCityProjectService, CapBuildsMapperService capBuildsMapperService) {
        this.teamCityProjectService = teamCityProjectService;
        this.capBuildsMapperService = capBuildsMapperService;
        cache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(5, TimeUnit.MINUTES).build(new CacheLoader<String, Project>() {
            @Override
            public Project load(String key) throws Exception {
                return getStatus();
            }
        });
    }

    @Override
    public Status getBrokenBuilds() {
        try {
            return this.capBuildsMapperService.mapAllProjectsToStatus(cache.get("Status"));
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new HTTPException(500);
        }
    }

    @Override
    public Status getBrokenDvtBuilds() {
        try {
            return this.capBuildsMapperService.mapOnlyDvtsToStatus(cache.get("Status"));
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new HTTPException(500);
        }
    }

    @Override
    public Status getBrokenArtifactBuilds() {
        try {
            return this.capBuildsMapperService.mapOnlyArtifactGenerationToStatus(cache.get("Status"));
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new HTTPException(500);
        }
    }

    private Project getStatus(){
        Project rootProject = new Project("CAP Builds", "All the stuff we build");
        try {
            teamCityProjectService.getProjectsWithBrokenBuilds("Capd", rootProject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootProject;
    }
}
