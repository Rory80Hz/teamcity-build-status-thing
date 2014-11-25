package uk.gov.defra.capd.buildstatus.service.domain.models;

import java.util.List;

/**
 * Created by Rory80hz on 05/09/2014.
 */
public class OtherStuff {
    private final String name;
    private final List<Build> brokenBuilds;

    public OtherStuff(String name, List<Build> brokenBuilds) {
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
