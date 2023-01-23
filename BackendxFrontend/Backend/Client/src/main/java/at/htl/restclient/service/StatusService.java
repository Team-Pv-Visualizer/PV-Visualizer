package at.htl.restclient.service;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import at.htl.restclient.entities.Status;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * Connect to RestClient and get the Json File
 */
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "http://localhost:3004/Status")
public interface StatusService {

    /**
     * Get all data - get json file
     * @return json file
     */
    @GET
        Status getAll();
}
