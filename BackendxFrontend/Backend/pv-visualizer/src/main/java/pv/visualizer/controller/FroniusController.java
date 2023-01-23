package pv.visualizer.controller;
import org.json.*;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

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
    }
    public static void login(String url, String username, String password) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String urlParameters = "username=" + username + "&password=" + password;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    @GET
    public Response test() throws IOException {
        loadSettings();
        login("https://www.solarweb.com/Account/ExternalLogin",username,password);
        return Response.ok().build();
    }
}
