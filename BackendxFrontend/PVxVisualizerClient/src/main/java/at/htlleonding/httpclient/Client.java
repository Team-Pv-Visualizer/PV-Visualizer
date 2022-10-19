package at.htlleonding.httpclient;

import at.htlleonding.entities.EnergyFlow;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {

    public static void getData() throws JSONException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://mockend.com/Toni2735/Toni2735/Energyflow")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(Client::parse)
                .join();
    }
    @SneakyThrows
    public static String parse(String responseBody){
        JSONArray jsonarray = new JSONArray(responseBody);
        for (int i = 0; i < jsonarray.length(); i++){
            JSONObject object = jsonarray.getJSONObject(i);
            EnergyFlow energyFlow = new EnergyFlow();
            long id = object.getLong("id");
            String title = object.getString("title");
            Double value = object.getDouble("value");
            energyFlow.setId(id);
            energyFlow.setTitle(title);
            energyFlow.setValue(value);
            System.out.println(id + " " + title + " " + value);
            System.out.println("DB:" + energyFlow.getId() + " " + energyFlow.getTitle() + " " + energyFlow.getValue());
            System.out.println("---------------");
        }
        return null;
    }
}
