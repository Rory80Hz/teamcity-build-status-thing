package uk.gov.defra.capd.buildstatus.service.domain.models;

import java.util.List;

public class Artifact {
    private final String name;
    private final List<Build> brokenBuilds;

    public Artifact(String name, List<Build> brokenBuilds) {
        this.name = name;
        this.brokenBuilds = brokenBuilds;
    }

    public String getName() {
        return name;
    }

    public List<Build> getBrokenBuilds() {
        return this.brokenBuilds;
    }

    public Boolean getBroken() {
        return !this.brokenBuilds.isEmpty();
    }
}
