package uk.gov.defra.capd.buildstatus.service.domain;

import uk.gov.defra.capd.buildstatus.service.domain.models.Project;

import java.io.IOException;

public interface TeamCityProjectService {
    public void getProjectsWithBrokenBuilds(String rootProjectId, Project parentProject) throws IOException;
}
