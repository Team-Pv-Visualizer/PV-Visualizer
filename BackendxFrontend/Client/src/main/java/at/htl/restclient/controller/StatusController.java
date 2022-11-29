package at.htl.restclient.controller;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import at.htl.restclient.entities.Head;
import at.htl.restclient.entities.Status;
import at.htl.restclient.genericoperations.CRUDOperations;
import at.htl.restclient.service.StatusService;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * Process the data and store the processed data in the database
 */
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

    static Boolean froniusIsOn = true;
    static Integer counter = 0;

    /**
     * Call up the method data every 120sec
     */
    @Scheduled(every="120s")
    public void callUpMethod(){
        System.out.println("Call up Status");
        data();
    }

    /**
     * Call up Service and get Json File and process new data
     * @return response code and new Status Json
     */
    @GET
    public Response data() {
        Status post = new Status();
        try {
            post = statusService.getAll();
            var ef = getById(post.headId);
            post.setHead(ef);
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(counter == 0){
            crud.add(post);
            counter++;
        }
        else{
            if(controlColumn() != true){
                crud.add(post);
            }
        }
        if(post.code == "0")
            froniusIsOn = false;

        return Response.ok(post).build();
    }
    /**
     * Compare two timeStamps if the newer one exist already
     * @return Boolean value if the newer one is already created
     */
    @Transactional
    private Boolean controlColumn(){
        Boolean duplicate = false;
        em.createQuery("select x from Status x", Status.class).getSingleResult();
        if(em != null){
            duplicate = true;
        }
        return duplicate;
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

    /**
     * Get FroniusIsOn Boolean Value
     * @return Boolean Value
     */
    public static Boolean getFroniusIsOn(){
        return froniusIsOn;
    }
}
