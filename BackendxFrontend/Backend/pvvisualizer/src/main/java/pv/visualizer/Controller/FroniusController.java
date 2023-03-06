package pv.visualizer.Controller;

import io.quarkus.scheduler.Scheduled;
import org.json.JSONObject;
import pv.visualizer.Common.JsonReader;
import pv.visualizer.Entities.FroniusObject;
import pv.visualizer.GenericOperations.CRUDOperations;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

@Path("/Fronius")
@Produces(MediaType.APPLICATION_JSON)
/**
 * Fronius Controller to manage Data
 */
public class FroniusController {
    /**
     * Json-Variable | PvSystemId for Fronius Login
     */
    private String PvSystemId;

    List<HttpCookie> requiredCookies = new ArrayList<>();

    @Inject
    CRUDOperations crud;

    @Scheduled(every = "30s")
    public void callUpMethode(){
        String[] newSettings;
        newSettings = JsonReader.loadSettings();
        if(newSettings[0].contains("Fronius") == true){
            PvSystemId = newSettings[1];
            try {
                getData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getCookie() throws IOException, URISyntaxException {
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        String urlString = "https://www.solarweb.com/Home/GuestLogOn?pvSystemId="+PvSystemId;
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        List<String> cookieNames = Arrays.asList(".AspNet.Auth", "Culture", "DateFormat", "TimeFormat", "__RequestVerificationToken","lbc");
        List<HttpCookie> cookies = cookieManager.getCookieStore().get(url.toURI());
        requiredCookies = new ArrayList<>();
        for (HttpCookie cookie : cookies) {
            if (cookieNames.contains(cookie.getName())) {
                requiredCookies.add(cookie);
            }
        }
    }

    @GET
    public Response getData() throws IOException, URISyntaxException {
        getCookie();

        String urlString = "https://www.solarweb.com/ActualData/GetCompareDataForPvSystem?pvSystemId=" + PvSystemId;
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Cookie", requiredCookies.get(0) + "; " +
                requiredCookies.get(4) + "; " +
                requiredCookies.get(2) + "; " +
                requiredCookies.get(5) + "; " +
                requiredCookies.get(3) + "; " +
                requiredCookies.get(1)
        );

        int responseCode = connection.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String result = response.toString();

        JSONObject jsonResponse = new JSONObject(result);
        double pLoad = jsonResponse.getDouble("P_Load");

        FroniusObject froniusObject = new FroniusObject();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        if(pLoad > 0){
            froniusObject.p_Load = pLoad;
        }
        else{
            froniusObject.p_Load = pLoad * (-1);
        }
        froniusObject.date = dateFormat.format(date);
        crud.add(froniusObject);
        System.out.println("Fronius" + pLoad);
        return Response.ok().build();
    }
}
