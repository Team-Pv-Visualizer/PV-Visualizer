package at.htl.restclient.service.School;

import at.htl.restclient.entities.School.Status;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Connect to RestClient and get the Json File
 */
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "http://localhost:3005/Status")
public interface StatusService {

    /**
     * Get all data - get json file
     * @return json file
     */
    @GET
    List<Status> getAll();
}
