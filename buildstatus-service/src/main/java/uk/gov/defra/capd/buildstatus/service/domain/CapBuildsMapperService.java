package uk.gov.defra.capd.buildstatus.service.domain;

import uk.gov.defra.capd.buildstatus.service.domain.models.Project;
import uk.gov.defra.capd.buildstatus.service.domain.models.Status;

public interface CapBuildsMapperService {
    public Status mapAllProjectsToStatus(Project capdMainBuildsProject);

    public Status mapOnlyDvtsToStatus(Project capdMainBuildsProject);

    public Status mapOnlyArtifactGenerationToStatus(Project capdMainBuildsProject);
}
