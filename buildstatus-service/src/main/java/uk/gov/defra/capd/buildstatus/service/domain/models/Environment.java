package uk.gov.defra.capd.buildstatus.service.domain.models;

import java.util.ArrayList;
import java.util.List;

public class Environment {
    private final String name;
    private final List<Build> brokenBuilds;

    public Environment(String name, List<Project> brokenProjects) {
        this.name = name;
        this.brokenBuilds = new ArrayList<>();
        for (Project p : brokenProjects) {
            getBrokeBuilds(p, brokenBuilds);
        }
    }

    private void getBrokeBuilds(Project project, List<Build> brokenBuilds) {
        brokenBuilds.addAll(project.getBrokenBuilds());
        for (Project p : project.getProjects()) {
            getBrokeBuilds(p, brokenBuilds);
        }
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
