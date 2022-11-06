package at.htl.restclient.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import at.htl.restclient.entities.EnergyFlow;
import at.htl.restclient.genericoperations.CRUDOperations;
import at.htl.restclient.service.EnergyFlowService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/EnergyFlow")
@Produces(MediaType.APPLICATION_JSON)
public class EnergyFlowController {
    @RestClient
    EnergyFlowService energyflowService;

    @Inject
    CRUDOperations crud;

    @GET
    public Response data() {
        EnergyFlow post = new EnergyFlow();
        try {
            post = energyflowService.getAll();
        } catch(Exception e) {
            e.printStackTrace();
        }
        crud.add(post);
        return Response.ok(post).build();
    }
}
