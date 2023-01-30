package at.htl.restclient.controller.Fronius;


import at.htl.restclient.Common.JsonReader;
import at.htl.restclient.genericoperations.CRUDOperations;
import io.quarkus.scheduler.Scheduled;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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
        String[] newSettings = new String[2];
        newSettings = JsonReader.loadSettings();
        if(newSettings[1].contains("Fronius") == true){
            data();
            PvSystemId = newSettings[2];
        }
    }

    @GET
    public Response data() {
        try{
            String urlString = "https://www.solarweb.com/ActualData/GetCompareDataForPvSystem?pvSystemId=" + PvSystemId;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Cookie",".AspNet.Auth=XMVUVyWOdidwdPMEkleP4Vyo3YmM5rj9BlgmUskKfTv3oAkcxMQyoS67SDso4qE_2hjaFdVzKfiN4Gd31SBvmteSYL_nQ44FSgy8LnHzdjFGR0N17WBpSiH23dKVCdCYfqYGrf7KCtR1GQrthonLow2GAVAcfidzmgHucMvLB5esAORqHyJq2hiLhN0hbbg9tRempenBuSRnrgKITZ26P1Ys1bbZKRRxT2JdaEb5UqgBu3_gW8vDLJizW2mrnSHzMSSKk3-G0JQmkZLNUTu2FMQJ0G7ul8DIFbRIpwQqyjc1Ms6S6xUgw2GHxrvaqaNcZzSl1UKTQCEGJM78CGoGNxiaQ0kmF2bvYMhSG6E3B8nOcpi0EWMmins8R0v0vuNQirTexSRM_48MWJ4DsPA4OI6j9x_f9EN52RQYgo6HLTtKR-aj; Culture=en; DateFormat=DD.MM.YYYY; OpenIdConnect.nonce.MoIwCs4evugcwuhhECOaNrU4yGhYLAzvlLenXsbnuSE%3D=dnlxbm00RVNZd3o2NmE3TTNOblVzS0lVblBQbExaN0ZWaVVDbmtLSnpsY0JvWEFqbWFaeklONmsyTGhCUnpldEpERDFsTDlmd2lUMm92MGQ2MkcyQlZCbnhBck9nNTRIOHc5dTVjcEVISVJQbm5iOGlhQ0pJUXpRLUdJamQ0V0J4ekRlU1RZcHV6eER2bTRzRUNGWGNobE4tS3JuVXA2dXg3ODdpOVFjbzNkTExzSUIzWW1Md20yTm1TMFBXSjVOYV9sb1Z2b3JUbkwzSnNpYlJWVmp1WUg2aktB; OpenIdConnect.nonce.TjRPrfJRze1hqsHo2nOWzdn9RB5mgxeCsQTE0HY5II8%3D=Q0d3a21ReHJualR4SFd5dmgyZTE3MUtWVXIyQkJZQ3U5d3dWVVNUQy1TbGt2ZlJPUk9tZlBwNTdWUHQtRTFkWUhDTmpwajlfWm1PcVd0SDM1dEw2OFFmMk45ZldWdFdEUXZlSE9rRHpBY3p5NXRMQnhtWS1tQWJGdlQtNlhDOW4xcl92cVpjcFl5OGNYUU0wNzIzWGM1NnZlUnBKc19OOWdnTXl3bTVsWE92VUEwM0o2aUM1eVc1UWtBbEpwYUZJUGtMaU5iMkJvX21BWmpvUWNpYWdJSGQ2RWo0; OpenIdConnect.nonce.URZ1pNLS5ooZ%2BGT%2F4RVW38ykPSA8uKtQn%2BJUP%2F66EE8%3D=LVFkM3BuUGhXbXgzMDlrNzRQeWRUeVhaYkpyS25CbWFIYzJCbUE5cTBXcjNqdWFrQmVQU1pQZlptd3EtVFVlVzVrSjR3M3NoUEhWcUhKcnBhckRORmh2OHFOOE9HWUFHYllCcnpyaFlJclRZQW1Ed0tuX09TSW9YN0NLQldiYmRObFpqRjNYVEY5MEZyZWRGb2ZXbzhpWWp1eW9Ob2l2YUp1SS16TjZkcXhzbndNVU1BdTVhX29LaUliVEZrbkpnZWpOdTZqMnQ3YWFTU3VadkZKZmxYR2hVWllJ; OpenIdConnect.nonce.Ysg7ruVkRlHDip%2Fz7HdPJRMaIFVLCXwVI2sT9MKTeM8%3D=dXhYY19DOVBEcDFuM0dsMXl6Z1BITDZ5dktLMXVOOXNWWjBNZVZISjlVRWFkX0V0dm1BamhqQldGb284UWZ0dENzQ0pacGhBNXdCTGpLck4tTV9FWTJLLWRJeHRXUTlORFNVRGVhRG5rUXZ1WDdCbVR6Q296V2h3MlhOUVFKZmFWLWFWMHV0YzJ0d2RsU21hYkx6LUVxNDJwWENkbFY2VGVfR3JFMEh1RldDd2cyMThJWnd1bEF6T3FGRjRxUDU1MHI0SFhFMVlXZHExNmdFNV9ZN3F4Y0xnYXVF; OpenIdConnect.nonce.wb8B69AAA5eYCJOE5rzQIXkw9iE9bZ5ndHnk3cs8R3A%3D=UG1aYTJYbVFuRVliQnowZ0hZOFNtSEZvSFBvakZNMVJrYkp5THgyUExNbEhRTUVmVVc4alZMM0xzbWw2dEJKOTNvYkZRbkMtb1RMckdyTy1GUk51Y2pHRFBOMUVRYldnUGtMN09uQUNja1FJQm1jcXRvVjJUUk14M0F4cHN4VE93MjdOcU54MUtuWWtyaTNSYTVFdmgwRlpfOUNwTEFnY2RWQWxyOUlTYXlFZ2g1Z1JERm84Snl6cFhzVTRDUUFSZXVMLUVHRk05OUswNWZVYllhRmc2elJRUmNn; OpenIdConnect.nonce.ywD5NNmQ4b2O6vyhoAqbn5xWLuLtmtjlV6b5wd2XkQ4%3D=Q0IzUlNpY2tFSk9kT2tqc1k3VWZIdVBxWmQ1QnhyTWpFNjZ1QVo3NFhjTzJrTzNnUDBJTl9vMXZ2VWJkbERZb3phTzVEX0xqck51bVFVV3FFc0d1SXJKLVdoREladmdFZ0FiR3h3X1hNY2ZtcFdNYk1KY2tnYlE2MDhUdzc0VzlucG1QVFYtNTZZR0p2Y3Jjb2dkb0ZNT2RZUTQ1YkotWGZFWlFZWGlSa0FPSjVQNE1DYmhwdlN5Y3pOMnRWbDJEOEZybUlPaUtKZFZrdVBsbGQzZ25mQ2Z1RmxN; TimeFormat=HH:mm; __RequestVerificationToken=Ns66gjqynD0Znnj0pOZLmMa1HKs8sM3HjyqJ9Ta_eLLZf28tAL1E06GgDjIOWp5uyZ4ke4OVdhb5cV7b8mMEDMgAzMQ1; lbc=!AF0Y+JRdVRVojPWebLrGiH4EM+a4DsY0+8FC2oaa97ZojEvjGiCf4QPs2jmQQl98d2N67XiZEZxn8bpA8FGS4QRnr6+jdToBlkQiz88rAJA=");
            con.setRequestProperty("Host", "www.solarweb.com");

            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            javax.json.JsonReader reader = Json.createReader(new StringReader(response.toString()));
            JsonObject jsonObject = reader.readObject();
            String pLoad = jsonObject.getString("P_Load");

            System.out.println(pLoad);
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
