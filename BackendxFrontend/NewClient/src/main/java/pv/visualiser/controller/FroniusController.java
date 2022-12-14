package pv.visualiser.controller;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pv.visualiser.entities.FroniusObject;
import pv.visualiser.service.FroniusService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Path("/Fronius")
public class FroniusController {

    @Inject
    @RestClient
    FroniusService froniusService;

    @GET
    public Response data() {

        FroniusObject post = new FroniusObject();
        try {
            post = froniusService.getAll();
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println(post);
        return Response.ok(post).build();
    }

    public static void readJSONFile() throws IOException, ParseException {
        JSONParser jsonparser = new JSONParser();

        FileReader reader = new FileReader(".\\jsonfiles\\filename.json");
        jsonparser.parse(reader);

        Object obj = jsonparser.parse(reader);
        JSONObject empjsonobj = (JSONObject)obj;

        String temporaryname=(String) empjsonobj.get("");

        //Lines for reading an JSON array
        JSONArray array =(JSONArray)empjsonobj.get("address");

        for(int i=0; i<array.size();i++){
            JSONObject address=(JSONObject) array.get(i);

            String street = (String) address.get("street");
        }

    }
}
