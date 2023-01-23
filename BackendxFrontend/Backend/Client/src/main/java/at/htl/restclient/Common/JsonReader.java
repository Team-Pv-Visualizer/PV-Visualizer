package at.htl.restclient.Common;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonReader {
    public static String loadSettings() {
        String service = "";
        try {
            JSONObject json = new JSONObject(new JSONTokener(new FileReader("C:\\Users\\1209a\\OneDrive\\Desktop\\School\\Syp\\PV-Visualizer\\BackendxFrontend\\Backend\\Client\\src\\main\\resources\\Settings.json")));
            service = json.getString("Service");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return service;
    }
}
