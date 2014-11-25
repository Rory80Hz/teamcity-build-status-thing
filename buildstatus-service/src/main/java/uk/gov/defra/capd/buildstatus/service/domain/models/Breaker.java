package uk.gov.defra.capd.buildstatus.service.domain.models;

public class Breaker {
    private final String name;
    private final String commitInfo;

    public Breaker(String name, String commitInfo){
        this.name = name;
        this.commitInfo = commitInfo;
    }

    public String getName() {
        return name;
    }

    public String getCommitInfo() {
        return commitInfo;
    }

}
