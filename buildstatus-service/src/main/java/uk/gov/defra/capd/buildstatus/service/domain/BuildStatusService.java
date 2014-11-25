package uk.gov.defra.capd.buildstatus.service.domain;

import uk.gov.defra.capd.buildstatus.service.domain.models.Status;

public interface BuildStatusService {

    public Status getBrokenBuilds();

    public Status getBrokenDvtBuilds();

    public Status getBrokenArtifactBuilds();
}
