package uk.gov.defra.capd.buildstatus.service.domain.models;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private final List<Project> projects;
    private final String name;
    private final String description;
    private final List<Build> brokenBuilds;

    public Project(String name, String description){
        this.projects = new ArrayList<Project>();
        this.brokenBuilds = new ArrayList<Build>();
        this.name = name;
        this.description = description;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<Build> getBrokenBuilds() {
        return brokenBuilds;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
