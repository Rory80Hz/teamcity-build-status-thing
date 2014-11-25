package uk.gov.defra.capd.buildstatus.service.domain;

import uk.gov.defra.capd.buildstatus.service.domain.models.*;

import java.util.ArrayList;
import java.util.List;

public class CapBuildsMapperServiceImpl implements CapBuildsMapperService {

    public static final String PIPELINE = "Pipeline";
    public static final String GENERATION = "Generation";
    public static final String ENVIRONMENT = "environment";

    @Override
    public Status mapAllProjectsToStatus(Project capdMainBuildsProject) {
        return buildAllProjectStatuses(capdMainBuildsProject);
    }

    @Override
    public Status mapOnlyDvtsToStatus(Project capdMainBuildsProject) {
        Project capdMainBuilds = capdMainBuildsProject.getProjects().get(0);
        Status buildStatus = new Status();
        buildStatus.getPipelines().addAll(findPipeLines(capdMainBuilds));
        return this.findOnlyBuildsContainingText(buildStatus, "validation");
    }

    @Override
    public Status mapOnlyArtifactGenerationToStatus(Project capdMainBuildsProject) {
        return buildArtifactGenerationStatuses(capdMainBuildsProject);
    }

    private Status buildAllProjectStatuses(Project rootProject) {
        Project capdMainBuilds = rootProject.getProjects().get(0);
        Status buildStatus = new Status();
        buildStatus.getPipelines().addAll(findPipeLines(capdMainBuilds));
        buildStatus.getArtifacts().addAll(findArtifactGeneration(capdMainBuilds));
        buildStatus.getOtherStuff().addAll(findOther(capdMainBuilds));
        return buildStatus;
    }

    private Status buildArtifactGenerationStatuses(Project rootProject) {
        Project capdMainBuilds = rootProject.getProjects().get(0);
        Status buildStatus = new Status();
        buildStatus.getArtifacts().addAll(findArtifactGeneration(capdMainBuilds));
        return buildStatus;
    }


    private List<Pipeline> findPipeLines(Project capdMainBuilds) {
        ArrayList<Pipeline> pipelines = new ArrayList<Pipeline>();

        for (Project p : capdMainBuilds.getProjects()) {
            if (p.getName().contains(PIPELINE)) {
                Pipeline pipeline = new Pipeline(p.getName());
                pipeline.getEnvironments().addAll(findEnvironments(p));
                pipelines.add(pipeline);
            }
        }

        return pipelines;
    }

    private List<Artifact> findArtifactGeneration(Project capdMainBuilds) {
        ArrayList<Artifact> artifacts = new ArrayList<Artifact>();

        for (Project p : capdMainBuilds.getProjects()) {
            if (p.getName().contains(GENERATION)) {
                Artifact artifact = new Artifact(p.getName(), p.getBrokenBuilds());
                artifacts.add(artifact);
            }
        }

        return artifacts;
    }

    private List<OtherStuff> findOther(Project capdMainBuilds) {
        ArrayList<OtherStuff> otherStuffs = new ArrayList<OtherStuff>();

        for (Project p : capdMainBuilds.getProjects()) {
            if (!p.getName().contains(GENERATION) && !p.getName().contains(PIPELINE)) {
                OtherStuff otherStuff = new OtherStuff(p.getName(), p.getBrokenBuilds());
                otherStuffs.add(otherStuff);
            }
        }

        return otherStuffs;
    }

    private List<Environment> findEnvironments(Project pipeLineProject) {
        ArrayList<Environment> environments = new ArrayList<Environment>();

        for (Project p : pipeLineProject.getProjects()) {
            if (p.getDescription().contains(ENVIRONMENT)) {
                Environment environment = new Environment(p.getName(), p.getProjects());
                environments.add(environment);
            }
        }

        return environments;
    }

    private Status findOnlyBuildsContainingText(Status status, String text) {
        Status reducedStatus = status;
        for (Pipeline p : status.getPipelines()) {
            for (Environment e : p.getEnvironments()) {
                List<Build> removeableBuilds = new ArrayList<Build>();
                for (Build b : e.getBrokenBuilds()) {
                    if (!b.getName().toUpperCase().contains(text.toUpperCase())) {
                        removeableBuilds.add(b);
                    }
                }
                e.getBrokenBuilds().removeAll(removeableBuilds);
            }
        }

        return reducedStatus;
    }
}
