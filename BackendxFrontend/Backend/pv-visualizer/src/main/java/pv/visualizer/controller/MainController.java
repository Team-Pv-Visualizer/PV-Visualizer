package pv.visualizer.controller;

import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import io.quarkus.scheduler.Scheduled;

public class MainController {
    /**
     * Json-Variable | To choose, what Service the user wants (Fornius, School)
     */
    private String service;

    /**
     * Json-Variable | Username for Fronius Login
     */
    private String username;

    /**
     * Json-Variable | Password for Fronius Login
     */
    private String password;

    /**
     * Client Builder for Login
     */
    public void loadSettings() {
        try {
            JSONObject json = new JSONObject(new JSONTokener(new FileReader("C:\\Users\\1209a\\OneDrive\\Desktop\\School\\Syp\\PV-Visualizer\\BackendxFrontend\\pv-visualizer\\src\\main\\resources\\Settings.json")));
            service = json.getString("Service");
            username = json.getString("Username");
            password = json.getString("Password");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Scheduled(every="120s")
    public void run(){
        loadSettings();
    }

}
