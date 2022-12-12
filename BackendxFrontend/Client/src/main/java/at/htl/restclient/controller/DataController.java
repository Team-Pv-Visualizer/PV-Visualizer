package at.htl.restclient.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import at.htl.restclient.entities.Head;
import at.htl.restclient.entities.Data;
import at.htl.restclient.entities.MonthlyConsumption;
import at.htl.restclient.entities.TodayConsumption;
import at.htl.restclient.genericoperations.CRUDOperations;
import at.htl.restclient.service.DataService;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import static at.htl.restclient.controller.StatusController.getFroniusIsOn;

/**
 * Process the data and store the processed data in the database
 */
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

    /**
     * Call up the method data every 120sec
     */
    @Scheduled(every="120s")
    public void callUpMethod(){
        data();
    }

    /**
     * Call up Service and get Json File and process new data
     * @return response code and new Data Json
     */
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
                //MonthlyConsumption();
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
     * Process the Date and generate new Row with data and value
     * @param posts new List - Data
     * @return new row
     */
    public TodayConsumption DailyConsumption(List<Data> posts){
        double maxValue = 0;

        for (int i = 0; i < posts.size(); i++){
            maxValue += posts.get(i).value;
        }
        maxValue /= 1000;
            TodayConsumption update = new TodayConsumption();
            update.value = maxValue;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            update.date = formatter.format(date);
            return update;
        }

    /**
     * Compare two Values if the newer one exist already
     * @param value new value
     * @return Boolean value if the newer one is already created
     */
    private Boolean compareColumn(Double value){
        Boolean duplicate = false;
        var x = em.createQuery("select tc.value from TodayConsumption tc order by tc.id desc", Double.class).setMaxResults(1).getSingleResult();
        if(Objects.equals(x, value)){
            duplicate = true;
        }

        return duplicate;
    }

    /**
     * Calculated MonthlyConsumption with TodayConsumption
     */
    /*public void MonthlyConsumption(){
        /*MonthlyConsumption update = new MonthlyConsumption();
        var x = em.createQuery("SELECT MONTH(tc.date), SUM(tc.value) FROM TodayConsumption tc GROUP BY MONTH(tc.date)", Object.class).getResultList();
        int lastIndex = x.size();
        update.date = x.get(1);
        update.value = x.get(lastIndex).value;
        crud.add(update);
    }*/
}