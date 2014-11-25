package uk.gov.defra.capd.buildstatus.service.domain.models;

import uk.gov.defra.capd.buildstatus.service.domain.helpers.TeamMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Build {
    private final String name;
    private final String description;
    private final List<Breaker> breakers;

    public Build(String name, String description) {
        this.name = name;
        this.description = description;
        this.breakers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Breaker> getBreakers() {
        return breakers;
    }

    public String getTeam() {
        Set<String> teams = new HashSet<>();
        for (Breaker b : getBreakers()) {
            if (TeamMapper.teamOne.contains(b.getName().toLowerCase())) {
                teams.add("1");
            } else if (TeamMapper.teamTwo.contains(b.getName().toLowerCase())) {
                teams.add("2");
            } else if (TeamMapper.teamThree.contains(b.getName().toLowerCase())) {
                teams.add("3");
            } else if (TeamMapper.teamFour.contains(b.getName().toLowerCase())) {
                teams.add("4");
            }
        }
        return teams.toString();
    }
}
