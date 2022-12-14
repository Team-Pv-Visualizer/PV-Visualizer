package at.htl.restclient.controller;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import at.htl.restclient.entities.Head;
import at.htl.restclient.genericoperations.CRUDOperations;
import at.htl.restclient.service.HeadService;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * Process the data and store the processed data in the database
 */
@Path("/Head")
@Produces(MediaType.APPLICATION_JSON)
public class HeadController {

    @Inject
    @RestClient
    HeadService headService;

    @Inject
    CRUDOperations crud;

    @Inject
    EntityManager em;
    static Integer counter = 0;

    static String date;

    /**
     * Call up the method data every 60sec
     */
    @Scheduled(every="60s")
    public void callUpMethod(){
        System.out.println("Call up Head");
        data();
    }

    /**
     * Call up Service and get Json File and process new data
     * @return response code and new Head Json
     */
    @GET
    public Response data() {
        Head post = new Head();
        try {
            post = headService.getAll();
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(counter == 0){
            crud.add(post);
            counter++;
        }
        else{
            date = post.timeStamp;
            if(controlColumn(post.timeStamp) != true){
                crud.add(post);
            }
        }

        return Response.ok(post).build();
    }

    /**
     * Compare two timeStamps if the newer one exist already
     * @param timestamp new timeStamp
     * @return Boolean value if the newer one is already created
     */
    @Transactional
    private Boolean controlColumn(String timestamp){
        Boolean duplicate = false;
        em.createQuery("select x from Head x where x.timeStamp = :timestamp", Head.class).setParameter("timestamp", timestamp).getSingleResult();
        if(em != null){
            duplicate = true;
        }
        return duplicate;
    }

    /*
    public static String getDate(){
        return date;
    }*/

}
