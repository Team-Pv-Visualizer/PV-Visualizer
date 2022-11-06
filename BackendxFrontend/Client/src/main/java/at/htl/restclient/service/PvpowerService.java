package at.htl.restclient.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import at.htl.restclient.entities.Pvpower;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "http://localhost:3004/Pvpower")
public interface PvpowerService {
    @GET
    List<Pvpower> getAll();
}
