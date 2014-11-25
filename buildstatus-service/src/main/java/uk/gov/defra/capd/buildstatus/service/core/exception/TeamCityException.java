package uk.gov.defra.capd.buildstatus.service.core.exception;

public class TeamCityException extends RuntimeException {
    public TeamCityException(String cause){
        super(cause);
    }
}
