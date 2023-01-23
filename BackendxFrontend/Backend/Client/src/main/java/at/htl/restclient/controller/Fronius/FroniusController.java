package at.htl.restclient.controller.Fronius;


import at.htl.restclient.Common.JsonReader;
import at.htl.restclient.entities.Fronius.FroniusObject;
import at.htl.restclient.genericoperations.CRUDOperations;
import at.htl.restclient.service.Fronius.FroniusService;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Path("/Fronius")
@Produces(MediaType.APPLICATION_JSON)
/**
 * Fronius Controller to manage Data
 */
public class FroniusController {

    /**
     * Json-Variable | To choose, what Service the user wants (Fornius, School)
     */
    private String service;

    /**
     * Json-Variable | Username for Fronius Login
     */
    private String username;

    /**
     * Json-Variable | Password for Fronius Login
     */
    private String password;

    @Inject
    @RestClient
    FroniusService froniusService;

    @Inject
    CRUDOperations crud;

    @Scheduled(every = "10s")
    public void callUpMethode(){
        service = JsonReader.loadSettings();
        System.out.println(service);
        if(service.contains("Fronius") == true){
            data();
            System.out.println("Call up Fronius API");
        }
    }

    @GET
    public Response data() {
        FroniusObject froniusObject = new FroniusObject();
        try {
            froniusObject = froniusService.getAll();
        } catch(Exception e) {
            e.printStackTrace();
        }

        crud.add(froniusObject);

        return Response.ok(froniusObject).build();
    }
}
