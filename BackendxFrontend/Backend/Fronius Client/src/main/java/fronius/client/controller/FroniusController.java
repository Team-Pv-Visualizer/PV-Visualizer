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

    @Scheduled(every = "10s")
    public void callUpMethode(){
        String[] newSettings;
        newSettings = JsonReader.loadSettings();
        if(newSettings[0].contains("Fronius") == true){
            data();
            PvSystemId = newSettings[1];
        }
    }

    @GET
    public Response data() {
        try{
            String urlString = "https://www.solarweb.com/ActualData/GetCompareDataForPvSystem?pvSystemId=" + PvSystemId;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Cookie",".AspNet.Auth=-9K-ElUkr5xjg8GKJT59ymbWSDWIy7ya8CfDXPyxNDvJdrc6-OOBiBppYLFjHrWpkWXjjQCD1jvRt0Aoz2dxGfgTw4_lLaUN6kaLPu3IXYi5PBWtFyUncVvw91X05PybQbDnoUvrnDIaH72y4hfjp07kOBE_h1CFxHkHpp9Vni9iC2g592l6LRvu9XXxLyDZ5ZYWl_b3nxo_0uTIwNL-eyDhbunvl2Hq2SCOzePRRd6KABTH0--xtfJh6G15b-r6sfJIqaYpuXffC4K8uIUUME0rxJ-eyMmAwlJGYwkUTnEraC1YHDMUvZfpDgx9hXzGlXoWkJlvXdWBUrr1jtCavylM27d_S4Jh-F3oIKZiWTv9S7JRSNvluFg-bJGvC8iJu1tSyPfJplcaTGb9f_Skg3OFR1m89DZMeTkRErSvZvOcuzuI; Culture=en; DateFormat=DD.MM.YYYY; OpenIdConnect.nonce.Ec69tERi%2F1R9%2BKLGIUWLCRriHRRYhB%2FIJ7k7ZUKY8Yw%3D=REYzOXFvUjNzdmxMbDdWd190VUZNVVY1Zy1KdHc4eXFiT1A0V0JnTGl2cDdpaDdOQ1Z3REhDY3dkclh6VmFpNkZHQm8wajdoZ3VQRGs1YWtrbEl0UVRwckRuTGdrU1hFckRuY00wcGNHU3NmUWpVdkFtSXk1YWo2eDU0ZklydkxiM044amNjSzNpWHk4Zk9icnVQU1JsMWN4c2I3M3VlM1NPYXBQM2dRNm5RYnhjX0ZhZVdOTVBTNUxtLThpTXh6Q3hPc296RVg3RXl1ektpY2RobFhVUDd6dWx3; OpenIdConnect.nonce.MoIwCs4evugcwuhhECOaNrU4yGhYLAzvlLenXsbnuSE%3D=dnlxbm00RVNZd3o2NmE3TTNOblVzS0lVblBQbExaN0ZWaVVDbmtLSnpsY0JvWEFqbWFaeklONmsyTGhCUnpldEpERDFsTDlmd2lUMm92MGQ2MkcyQlZCbnhBck9nNTRIOHc5dTVjcEVISVJQbm5iOGlhQ0pJUXpRLUdJamQ0V0J4ekRlU1RZcHV6eER2bTRzRUNGWGNobE4tS3JuVXA2dXg3ODdpOVFjbzNkTExzSUIzWW1Md20yTm1TMFBXSjVOYV9sb1Z2b3JUbkwzSnNpYlJWVmp1WUg2aktB; OpenIdConnect.nonce.TjRPrfJRze1hqsHo2nOWzdn9RB5mgxeCsQTE0HY5II8%3D=Q0d3a21ReHJualR4SFd5dmgyZTE3MUtWVXIyQkJZQ3U5d3dWVVNUQy1TbGt2ZlJPUk9tZlBwNTdWUHQtRTFkWUhDTmpwajlfWm1PcVd0SDM1dEw2OFFmMk45ZldWdFdEUXZlSE9rRHpBY3p5NXRMQnhtWS1tQWJGdlQtNlhDOW4xcl92cVpjcFl5OGNYUU0wNzIzWGM1NnZlUnBKc19OOWdnTXl3bTVsWE92VUEwM0o2aUM1eVc1UWtBbEpwYUZJUGtMaU5iMkJvX21BWmpvUWNpYWdJSGQ2RWo0; OpenIdConnect.nonce.URZ1pNLS5ooZ%2BGT%2F4RVW38ykPSA8uKtQn%2BJUP%2F66EE8%3D=LVFkM3BuUGhXbXgzMDlrNzRQeWRUeVhaYkpyS25CbWFIYzJCbUE5cTBXcjNqdWFrQmVQU1pQZlptd3EtVFVlVzVrSjR3M3NoUEhWcUhKcnBhckRORmh2OHFOOE9HWUFHYllCcnpyaFlJclRZQW1Ed0tuX09TSW9YN0NLQldiYmRObFpqRjNYVEY5MEZyZWRGb2ZXbzhpWWp1eW9Ob2l2YUp1SS16TjZkcXhzbndNVU1BdTVhX29LaUliVEZrbkpnZWpOdTZqMnQ3YWFTU3VadkZKZmxYR2hVWllJ; OpenIdConnect.nonce.Ysg7ruVkRlHDip%2Fz7HdPJRMaIFVLCXwVI2sT9MKTeM8%3D=dXhYY19DOVBEcDFuM0dsMXl6Z1BITDZ5dktLMXVOOXNWWjBNZVZISjlVRWFkX0V0dm1BamhqQldGb284UWZ0dENzQ0pacGhBNXdCTGpLck4tTV9FWTJLLWRJeHRXUTlORFNVRGVhRG5rUXZ1WDdCbVR6Q296V2h3MlhOUVFKZmFWLWFWMHV0YzJ0d2RsU21hYkx6LUVxNDJwWENkbFY2VGVfR3JFMEh1RldDd2cyMThJWnd1bEF6T3FGRjRxUDU1MHI0SFhFMVlXZHExNmdFNV9ZN3F4Y0xnYXVF; OpenIdConnect.nonce.wb8B69AAA5eYCJOE5rzQIXkw9iE9bZ5ndHnk3cs8R3A%3D=UG1aYTJYbVFuRVliQnowZ0hZOFNtSEZvSFBvakZNMVJrYkp5THgyUExNbEhRTUVmVVc4alZMM0xzbWw2dEJKOTNvYkZRbkMtb1RMckdyTy1GUk51Y2pHRFBOMUVRYldnUGtMN09uQUNja1FJQm1jcXRvVjJUUk14M0F4cHN4VE93MjdOcU54MUtuWWtyaTNSYTVFdmgwRlpfOUNwTEFnY2RWQWxyOUlTYXlFZ2g1Z1JERm84Snl6cFhzVTRDUUFSZXVMLUVHRk05OUswNWZVYllhRmc2elJRUmNn; OpenIdConnect.nonce.ywD5NNmQ4b2O6vyhoAqbn5xWLuLtmtjlV6b5wd2XkQ4%3D=Q0IzUlNpY2tFSk9kT2tqc1k3VWZIdVBxWmQ1QnhyTWpFNjZ1QVo3NFhjTzJrTzNnUDBJTl9vMXZ2VWJkbERZb3phTzVEX0xqck51bVFVV3FFc0d1SXJKLVdoREladmdFZ0FiR3h3X1hNY2ZtcFdNYk1KY2tnYlE2MDhUdzc0VzlucG1QVFYtNTZZR0p2Y3Jjb2dkb0ZNT2RZUTQ1YkotWGZFWlFZWGlSa0FPSjVQNE1DYmhwdlN5Y3pOMnRWbDJEOEZybUlPaUtKZFZrdVBsbGQzZ25mQ2Z1RmxN; TimeFormat=HH:mm; __RequestVerificationToken=ihvbG8qn6mGxKODLMeorKnt0JvQTzl0eiCe2jKHA7Krlu2OUn10661zHYJlHhqJjAQDnvkrvnNUGvrGS0QLcInc5Yhs1; lbc=!Qkm0bFflesbPXIKpanfdjt7Yo1P0Owk/Ezz7sDYGddWBQ2jY97buhoPWCF7qXSCbu4bTbur3XcX5H58rZkT1fY9pjei69LfYNUj2vAb3Ois=");
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
            String result = response.toString().substring(71,89);
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

