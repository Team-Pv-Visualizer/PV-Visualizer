package pv.visualiser.service;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import pv.visualiser.entities.FroniusObject;

/**
 * Connect to RestClient and get the Json File
 */
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "https://www.solarweb.com/PvSystems/Widget")
public interface FroniusService {
    /**
     * Get all data - get json file
     * @return json file
     */
    @GET
    @Path("")
    FroniusObject getAll();
}
