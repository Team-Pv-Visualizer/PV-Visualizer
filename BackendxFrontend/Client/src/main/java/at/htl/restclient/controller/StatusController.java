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

    @Scheduled(every="120s")
    public void callUpMethod(){
        System.out.println("Call up Status");
        data();
    }
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
    @Transactional
    private Boolean controlColumn(){
        Boolean duplicate = false;
        em.createQuery("select x from Status x", Status.class).getSingleResult();
        if(em != null){
            duplicate = true;
        }
        return duplicate;
    }
    @Transactional
    private Head getById(Long id){
        return em.createQuery("select x from Head x where x.id = :id", Head.class).setParameter("id", id).getSingleResult();
    }
    
    public static Boolean getFroniusIsOn(){
        return froniusIsOn;
    }
}
