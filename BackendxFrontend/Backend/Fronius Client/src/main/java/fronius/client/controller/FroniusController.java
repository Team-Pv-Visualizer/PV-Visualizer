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
import java.util.Random;

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
            URL url = new URL("https://www.solarweb.com/Home/GuestLogOn?pvSystemId=" + PvSystemId);
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
        try{
            String urlString = "https://www.solarweb.com/ActualData/GetCompareDataForPvSystem?pvSystemId=" + PvSystemId;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Cookie",".AspNet.Auth=ya_divmQLZxSTZusb2mQxV_WDT9lRNOqWo9eb1j9Na3ScdJKsNkpt09TaO_4nCDurBaKDwU3k5zxPIEL_v_xyzsVRgLIEqC56lVIK9g_JtWQerMah7FKE4abEzXw55CUL0kMebPmVJqZ6VhzbZYDCXd1e8vf51wBdI1zQTdhWMqfMX9wgOZA5H9LdAHLEUpI4ZEx6XSBnXYDJQxrye_oh7nj9QGcVjCkh9TBpQAJNkrxlJPMfyhWxjdUjIgojK8tPou73LPp24O7KPM4u6sTDcq1tHZMNspAQjLPPaOcYqgF1sNKKGP73Z--WVl4Hy6ezUCBKIuqE7K7ZXp_mgQTeC5aT1cVGUsW2kipouwyfr1JGFCQEAVLnIxG_OKawdhrIGqrWrHtpaxmrnUw7zT0ra7bMTjkJeBH0MDoMPMrekoorqvn; Culture=en; DateFormat=DD.MM.YYYY; OpenIdConnect.nonce.82D18AkiRkRPI8VGj5a%2BUnCV4V1KDTk4uDrYc2V%2BpnU%3D=dDdWWFUxTTRraTMtQ04wNU4yRjV0c2d3NTlrVmZUaUU5QUp6OUVKdUt3bGhoVnBVTjVaU2NMVUlsUGxSM3ItUjJBU09fYTRGU2tOb3U4THM5RDZYNzc2YkU5X3pZWGM2LWUyQmpoUG5ybWFpbkhDNGoyQlN2N21WMngwS0VOMExSa1g2c216dWlVWElaWF8zRDh1UjA1dzRhMFRtSnpXdUE5ZHJfTnVNZDZFLUNHbWM4RERGd1V4VlVLVkpIUEpKVWxCcmlpWjE4V253dnRTRm1IM2NZQzJGbXpB; OpenIdConnect.nonce.KB0%2FWG9fBwLcLCLMlkff2CERGQyQtWmFFDEs3CQm%2B2Q%3D=WnhvZ1NDRTZuVWdsMTE5OE0zbmZ4Nm9QYV92dkxSQ2JnVGpuellyNnE0Sm5fY0JXOUZaUU9kTTI3ajNWYkZZcS1nT3l0djlrTmNvVXllUTUxUEFkZm9jVlhfTGxTazhTUTd6UVRWNEt6UUZWT3g3TzZqWm9DajZLTk90SThubjltUlF5X1EyMGpXZ1BWam5NT01UcmJnaWI0MTR2Q2w3aFA1dHlXNVYycFBoVFkxUzU3SVVpT1RtdzZWSmtuUDFaaGdQMExxOEEyY3NuLUpZRjUwSEk2THZveUR3; OpenIdConnect.nonce.i85Z%2BZncA8PHVuz8i58CqR39kBpI5BMPgOaJ5M%2BC7NU%3D=bDEyWDdBNzI4N1F4VjByUzBBUnlSV3RUQy1oNGxJNUpUNVRGZDhycWU2cHlHRmprQ04wT0NjalpOMWFPQzVmNkplLVhJdmYxUVdCVm1zbUY3RmhPYnJMYWhfNHR2X3pXQlZFWjhaRzBPTmRlTlpaaktsbGxqelhBUll0TzhocDc2ZzA4TDctY093Z0l5SzB5NDhtaHdILVdOWnYtRXE4S19paXZVb2t0WU81UGdFQS1USktQcDFuTW9qZzk0TjIyOTNVczRaTHlXQ2cxM3FJMEtDUTdpVTV1Z0ZZ; TimeFormat=HH:mm; __RequestVerificationToken=GaH4wx8L_3zhyiXCWSrQ8dkO09q4WM4ZBl0n2dh1EyAbhMhejCQxMTGI-7hYHd_A8d1yBcfFD47TEUlszVZ7gbFm4XY1; lbc=!RkDgeKE4ohVWB5apanfdjt7Yo1P0O24r9kj7C6qojFTrLOpd3ZPlTR4ZgUg6zRH2IPn0/B0eLGyBA7dQBEln+DiNyUjRfRRAFEDJua/N7fM=");
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
                if(pLoad > 0){
                    froniusObject.p_Load = pLoad;
                }
                else{
                    froniusObject.p_Load = pLoad * (-1);
                }
                froniusObject.date = dateFormat.format(date);
                crud.add(froniusObject);
            }else{
                Random random = new Random();
                pLoad = 1400.00 + (1600.00 - 1400.00) * random.nextDouble();
                FroniusObject froniusObject = new FroniusObject();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                froniusObject.p_Load = pLoad * (-1);
                froniusObject.date = dateFormat.format(date);
            }
            /*else if(result.contains("Akku\":null,\"P_PV\"") == true){
                pLoad = Double.parseDouble(response.toString().substring(60,67));
                FroniusObject froniusObject = new FroniusObject();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                froniusObject.p_Load = pLoad * (-1);
                froniusObject.date = dateFormat.format(date);

                crud.add(froniusObject);
            }*/

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

