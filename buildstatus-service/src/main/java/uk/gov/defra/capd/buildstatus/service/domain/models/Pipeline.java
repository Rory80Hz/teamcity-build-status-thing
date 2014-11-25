package uk.gov.defra.capd.buildstatus.service.domain.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rory80hz on 05/09/2014.
 */
public class Pipeline {
    private final String name;
    private List<Environment> environments;

    public Pipeline(String name) {
        this.name = name;
        this.environments = new ArrayList<Environment>();
    }

    public String getName() {
        return name;
    }

    public List<Environment> getEnvironments() {
        return this.environments;
    }
}
