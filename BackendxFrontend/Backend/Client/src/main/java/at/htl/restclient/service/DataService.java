package at.htl.restclient.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import at.htl.restclient.entities.Data;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * Connect to RestClient and get the Json File
 */
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "http://localhost:3004/Data")
public interface DataService {

    /**
     * Get all data - get json file
     * @return json file
     */
    @GET
    List<Data> getAll();
}
