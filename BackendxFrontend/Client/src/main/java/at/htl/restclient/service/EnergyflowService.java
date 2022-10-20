package at.htl.restclient.service;

import at.htl.restclient.entities.Energyflow;
import at.htl.restclient.entities.Pvpower;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "https://mocki.io/v1/a450ca3e-2b25-42a8-988c-05f742838139")
public interface EnergyflowService {
    @GET
    List<Energyflow> getAll();
}
