package at.htl.restclient.Common;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonReader {
    public static String[] loadSettings() {
        String[] newSettings = new String[2];
        try {
            JSONObject json = new JSONObject(new JSONTokener(new FileReader("C:\\Users\\1209a\\OneDrive\\Desktop\\School\\Syp\\PV-Visualizer\\BackendxFrontend\\Backend\\Client\\src\\main\\resources\\Settings.json")));
            newSettings[1] = json.getString("Service");
            newSettings[2] = json.getString("PvSystemId");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return newSettings;
    }
}
