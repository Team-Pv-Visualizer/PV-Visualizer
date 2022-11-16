package at.htl.restclient.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import at.htl.restclient.entities.Head;
import at.htl.restclient.entities.Data;
import at.htl.restclient.entities.MonthConsumption;
import at.htl.restclient.entities.TodayConsumption;
import at.htl.restclient.genericoperations.CRUDOperations;
import at.htl.restclient.service.DataService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import static at.htl.restclient.controller.StatusController.getFroniusIsOn;

@Path("/Data")
@Produces(MediaType.APPLICATION_JSON)
public class DataController {

    @Inject
    @RestClient
    DataService dataService;

    @Inject
    CRUDOperations crud;

    @Inject
    EntityManager em;
    static Integer counter = 0;
    @GET
    public Response data() {
        Boolean froniusIsOn = getFroniusIsOn();
        if(froniusIsOn == true){
            List<Data> posts = new ArrayList<>(0);
            try {
                posts = dataService.getAll();
                for (var post : posts){
                    var ef = getById(post.headId);
                    post.setHead(ef);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }

            TodayConsumption update = DailyConsumption(posts);

            posts.forEach(x -> crud.add(x));

            if(counter == 0){
                crud.add(update);
                counter++;
            }else {
                MonthlyConsumption();
                if(compareColumn(update.value) == false) {
                    crud.add(update);
                }
            }

            return Response.ok(posts).build();
        }
        else{
            return Response.ok().build();
        }

    }
    @Transactional
    private Head getById(Long id){
        return em.createQuery("select x from Head x where x.id = :id", Head.class).setParameter("id", id).getSingleResult();
    }

    public TodayConsumption DailyConsumption(List<Data> posts){
        double maxValue = 0;
        double firstValue = 0;
        double nextValue = 0;

        for (int i = 0; i < posts.size(); i++){
            if(firstValue == 0){
                firstValue = posts.get(i).value;
                maxValue = firstValue;
            }
            else{
                nextValue = posts.get(i).value;
                if(nextValue > maxValue)
                    maxValue = nextValue;
            }
        }

        TodayConsumption update = new TodayConsumption();
        update.value = maxValue;
        LocalDate localDate = LocalDate.now();
        update.date = localDate.toString();

        return update;
    }
    private Boolean compareColumn(Double value){
        Boolean duplicate = false;
        var x = em.createQuery("select tc.value from TodayConsumption tc order by tc.id desc", Double.class).setMaxResults(1).getSingleResult();
        if(Objects.equals(x, value)){
            duplicate = true;
        }

        return duplicate;
    }
    public void MonthlyConsumption(){
        MonthConsumption update = new MonthConsumption();
        var x = em.createQuery("select x.value from TodayConsumption x", Double.class).getResultList();
        System.out.println(x);
        var res = x.stream().mapToDouble(i -> i).sum();
        System.out.println(res);
        LocalDate localDate = LocalDate.now();
        update.date = localDate.toString();
        update.value = res;

        crud.add(update);
    }
}