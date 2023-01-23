package pv.visualizer.controller;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.json.*;
import pv.visualizer.entities.FroniusObject;
import pv.visualizer.genericoperations.CRUDOperations;
import pv.visualizer.service.FroniusService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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

    public void loadSettings() {
        try {
            JSONObject json = new JSONObject(new JSONTokener(new FileReader("C:\\Users\\1209a\\OneDrive\\Desktop\\School\\Syp\\PV-Visualizer\\BackendxFrontend\\Backend\\pv-visualizer\\src\\main\\resources\\Settings.json")));
            service = json.getString("Service");
            username = json.getString("Username");
            password = json.getString("Password");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(every="10s")
    @GET
    public Response data() {
        loadSettings();
        System.out.println("Call up FroniusObject");
        System.out.println(service);
        if(service.contains("Fronius") == true){
            FroniusObject froniusObject = new FroniusObject();
            try {
                froniusObject = froniusService.getAll();
            } catch(Exception e) {
                e.printStackTrace();
            }

            crud.add(froniusObject);

            return Response.ok(froniusObject).build();
        }
        else{
            return Response.ok().build();
        }
    }
}
