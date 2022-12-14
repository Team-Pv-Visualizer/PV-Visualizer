package at.htl.restclient.service;

import at.htl.restclient.entities.Head;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Connect to RestClient and get the Json File
 */
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "http://localhost:3004/Head")
public interface HeadService {

    /**
     * Get all data - get json file
     * @return json file
     */
    @GET
    Head getAll();
}
