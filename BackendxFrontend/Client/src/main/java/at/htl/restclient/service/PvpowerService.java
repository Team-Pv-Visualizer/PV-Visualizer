package at.htl.restclient.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import at.htl.restclient.entities.Pvpower;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
//@RegisterRestClient(baseUri = "https://mockend.com/Toni2735/Toni2735/Pvpower")
@RegisterRestClient
public interface PvpowerService {
    @GET
    List<Pvpower> getAll();
}
