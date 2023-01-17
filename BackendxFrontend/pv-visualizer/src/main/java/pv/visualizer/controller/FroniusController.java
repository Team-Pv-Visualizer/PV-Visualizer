package pv.visualizer.controller;
import org.json.*;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
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

    /**
     * Client Builder for Login
     */
    private final Client client = ClientBuilder.newClient();

    public void loadSettings() {
        try {
            JSONObject json = new JSONObject(new JSONTokener(new FileReader("C:\\Users\\1209a\\OneDrive\\Desktop\\School\\Syp\\PV-Visualizer\\BackendxFrontend\\pv-visualizer\\src\\main\\resources\\Settings.json")));
            service = json.getString("Service");
            username = json.getString("Username");
            password = json.getString("Password");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //System.out.println(service);
        //System.out.println(username);
        //System.out.println(password);

    }

    public void login() {
        System.out.println(username);
        System.out.println(password);
        Form form = new Form()
                .param("username", username)
                .param("password", password);

        Response response = client.target("https://www.solarweb.com" + "/Account/ExternalLogin")
                .request(MediaType.APPLICATION_FORM_URLENCODED)
                .post(Entity.form(form));

        if (response.getStatus() != 200) {
            throw new RuntimeException("Login failed with status code: " + response.getStatus());
        }
    }

    @GET
    public Response test() {
        loadSettings();
        login();
        return Response.ok().build();
    }
}
