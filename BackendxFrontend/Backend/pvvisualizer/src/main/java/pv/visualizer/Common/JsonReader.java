package pv.visualizer.Common;

import org.json.*;
import java.io.*;

public class JsonReader {
    public static String[] loadSettings() {
        String[] newSettings = new String[2];
        try {
            JSONObject json = new JSONObject(new JSONTokener(new FileReader("C:\\Users\\1209a\\OneDrive\\Desktop\\School\\Syp\\PV-Visualizer\\BackendxFrontend\\Backend\\pvvisualizer\\src\\main\\resources\\Settings.json")));
            newSettings[0] = json.getString("Service");
            newSettings[1] = json.getString("PvSystemId");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return newSettings;
    }
}
