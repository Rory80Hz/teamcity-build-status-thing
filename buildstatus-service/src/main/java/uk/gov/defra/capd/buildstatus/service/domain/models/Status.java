package uk.gov.defra.capd.buildstatus.service.domain.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rory80hz on 06/08/2014.
 */
public class Status {
    private final List<Pipeline> pipelines;
    private final List<Artifact> artifacts;
    private final List<OtherStuff> otherStuff;

    public Status(){
        this.pipelines = new ArrayList<Pipeline>();
        this.artifacts = new ArrayList<Artifact>();
        this.otherStuff = new ArrayList<OtherStuff>();
    }

    public List<Pipeline> getPipelines() {
        return pipelines;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public List<OtherStuff> getOtherStuff() {
        return otherStuff;
    }
}
