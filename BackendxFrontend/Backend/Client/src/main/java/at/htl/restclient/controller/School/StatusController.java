package at.htl.restclient.controller.School;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import at.htl.restclient.Common.JsonReader;
import at.htl.restclient.entities.School.Data;
import at.htl.restclient.entities.School.Status;
import at.htl.restclient.entities.School.Head;
import at.htl.restclient.genericoperations.CRUDOperations;
import at.htl.restclient.service.School.StatusService;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@Path("/Status")
@Produces(MediaType.APPLICATION_JSON)
public class StatusController {

    @Inject
    @RestClient
    StatusService statusService;

    @Inject
    CRUDOperations crud;

    @Inject
    EntityManager em;

    /**
     * Json-Variable | To choose, what Service the user wants (Fornius, School)
     */
    private String service;

    /**
     * Call up the method data every 120sec
     */
    @Scheduled(every="120s")
    public void callUpMethod(){
        service = JsonReader.loadSettings();
        if(!service.contains("Fronius")){
            data();
            System.out.println("Call up Status API");
        }
    }

    /**
     * Call up Service and get Json File and process new data
     * @return response code and new Status Json
     */
    @GET
    public Response data() {
        List<Status> posts = new ArrayList<>(0);
        try {
            posts = statusService.getAll();
            for (var post : posts){
                var ef = getById(post.headId);
                post.setHead(ef);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        posts.forEach(x -> crud.add(x));

        return Response.ok(posts).build();
    }

    /**
     * Get per Id the Head Entity
     * @param id Head Entity
     * @return Head Entity
     */
    @Transactional
    private Head getById(Long id){
        return em.createQuery("select x from Head x where x.id = :id", Head.class).setParameter("id", id).getSingleResult();
    }
}

