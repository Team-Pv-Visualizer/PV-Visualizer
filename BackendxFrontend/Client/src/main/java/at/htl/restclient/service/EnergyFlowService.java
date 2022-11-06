package at.htl.restclient.service;

import at.htl.restclient.entities.EnergyFlow;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "http://localhost:3004/EnergyFlow")
public interface EnergyFlowService {
    @GET
    EnergyFlow getAll();
}
