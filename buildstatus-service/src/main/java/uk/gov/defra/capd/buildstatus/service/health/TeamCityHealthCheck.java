package uk.gov.defra.capd.buildstatus.service.health;

import com.codahale.metrics.health.HealthCheck;
import uk.gov.defra.capd.buildstatus.service.core.healthcheck.TeamCity;

public class TeamCityHealthCheck extends HealthCheck {
    private final TeamCity teamCity;

    public TeamCityHealthCheck(TeamCity teamCity) {
        this.teamCity = teamCity;
    }

    @Override
    protected Result check() throws Exception {
        teamCity.checkAlive();
        return Result.healthy();
    }
}
