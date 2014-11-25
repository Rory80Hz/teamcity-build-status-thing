package uk.gov.defra.capd.buildstatus.service.resources;

import com.codahale.metrics.annotation.Timed;
import uk.gov.defra.capd.buildstatus.service.domain.BuildStatusService;
import uk.gov.defra.capd.buildstatus.service.domain.models.Status;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/builds")
@Produces(MediaType.APPLICATION_JSON)
public class BuildStatusResource {
    private final BuildStatusService buildStatusService;

    public BuildStatusResource(BuildStatusService buildStatusService) {
        this.buildStatusService = buildStatusService;
    }

    @GET
    @Timed
    public Status getAllBuilds() {
        return this.buildStatusService.getBrokenBuilds();
    }

    @GET
    @Path("/dvts")
    @Timed
    public Status getDvtBuilds(){
        return this.buildStatusService.getBrokenDvtBuilds();
    }

    @GET
    @Path("/artifacts")
    @Timed
    public Status getArtifactBuilds(){
        return this.buildStatusService.getBrokenArtifactBuilds();
    }
}