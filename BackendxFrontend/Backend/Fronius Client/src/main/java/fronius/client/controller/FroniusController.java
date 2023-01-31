package fronius.client.controller;

import com.google.gson.*;
import fronius.client.Common.JsonReader;
import fronius.client.entities.FroniusObject;
import fronius.client.genericoperations.CRUDOperations;
import io.quarkus.scheduler.Scheduled;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * Json-Variable | PvSystemId for Fronius Login
     */
    private String PvSystemId;

    @Inject
    CRUDOperations crud;

    @Scheduled(every = "30s")
    public void callUpMethode(){
        String[] newSettings;
        newSettings = JsonReader.loadSettings();
        if(newSettings[0].contains("Fronius") == true){
            data();
            PvSystemId = newSettings[1];
        }
    }

    public void getCookie(){
        try{
            URL url = new URL("https://www.solarweb.com/Home/GuestLogOn?pvSystemId=2ffd8c5d-221e-4887-8a6a-f7efbc2016c9");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Host", "www.solarweb.com");

            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            Map<String, List<String>> headers = con.getHeaderFields();
            List<String> cookies = headers.get("Set-Cookie");
            if (cookies != null) {
                for (String cookie : cookies) {
                    System.out.println(cookie);
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    public Response data() {
        //getCookie();

        try{
            String urlString = "https://www.solarweb.com/ActualData/GetCompareDataForPvSystem?pvSystemId=" + PvSystemId;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Cookie",".AspNet.Auth=0L2T8-jTc0qFy1BOzv3uhYK3XexYaZvrxDuWQ9BSq7IbLtGJpLCjvce3kx-PX3o6QPuDGqKntA21mXvUCRFlbcGSTYUiCe-LBlKQSmN8SA7yBim2l1psZJyccSpTVasHJAvgK3HneV3bXC9NMPfr2IOTc3xM1Bq_bxf7pOWEoMMt6mS3LR2M-pAwQcUhrXv4tYYV4E87u3tLKXxGO6tK0t-Xe00HRATMP79HNSLyUulYW9SuTCiMftYs1VLjdFdk9AveXs9FrkiiUZCtJk7V8j7yB0SjHtppZrje5WKGxlJZjeoo9cnaXs8ZRJ_tq-w2r6Di9fS93BAdrcHbaRHzBMo3_SMZQRPAO7HZhFMV-t9EV7RPtL8TS4gtmHF-g-rMSTcMxCOjfl2UGXlbmJX_H_eP2-ly6rw20JkmGshYsCI3IYWX; Culture=en; DateFormat=DD.MM.YYYY; TimeFormat=HH:mm; __RequestVerificationToken=Me_IeE-eVXJi-lgZMc4LINmtoEWmx2kdGfuYOJQPFOIfC8SJU-gcTa51PoBCX5LdQ3SGdgAu236zLwjPOxkq6bN3_e41; lbc=!Hmc9DTZLDSv8eJ2ebLrGiH4EM+a4DunvsaOy2D6OjvLXlSC8fRrovb+DMgTc3HF2i/6a/p+2bxVtmuEO/FEvmHnCDSH9cVASvgIEiQRTj8I=");
            con.setRequestProperty("Host", "www.solarweb.com");
            con.setRequestProperty("Content-Type", "application/json");

            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            String result = response.toString().substring(72,89);
            double pLoad;
            boolean isNumeric =  result.matches("[+-]?\\d*(\\.\\d+)?");
            if(isNumeric == true){
                pLoad = Double.parseDouble(result);
                FroniusObject froniusObject = new FroniusObject();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                froniusObject.p_Load = pLoad * (-1);
                froniusObject.date = dateFormat.format(date);
                crud.add(froniusObject);
            }
            else if(result.contains("_Akku\":null,\"P_PV\"") == true){
                pLoad = Double.parseDouble(response.toString().substring(60,68));
                FroniusObject froniusObject = new FroniusObject();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                froniusObject.p_Load = pLoad * (-1);
                froniusObject.date = dateFormat.format(date);
                crud.add(froniusObject);
            }

            System.out.println(result);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Response.ok().build();
    }
}

