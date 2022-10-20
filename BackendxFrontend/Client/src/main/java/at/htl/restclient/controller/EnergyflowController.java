package at.htl.restclient.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import at.htl.restclient.entities.Energyflow;
import at.htl.restclient.entities.Pvpower;
import at.htl.restclient.genericoperations.CRUDOperations;
import at.htl.restclient.service.EnergyflowService;
import at.htl.restclient.service.PvpowerService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/energyflow")
@Produces(MediaType.APPLICATION_JSON)
public class EnergyflowController {
    @RestClient
    EnergyflowService energyflowService;

    @Inject
    CRUDOperations crud;

    @GET
    public Response data() {
        List<Energyflow> posts = new ArrayList<>(0);
        try {
            posts = energyflowService.getAll();
        } catch(Exception e) {
            e.printStackTrace();
        }
        posts.forEach(x -> crud.add(x));
        return Response.ok(posts).build();
    }
}
