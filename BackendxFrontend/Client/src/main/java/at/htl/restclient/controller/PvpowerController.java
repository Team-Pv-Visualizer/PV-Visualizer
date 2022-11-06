package at.htl.restclient.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import at.htl.restclient.entities.Pvpower;
import at.htl.restclient.entities.Statistic;
import at.htl.restclient.genericoperations.CRUDOperations;
import at.htl.restclient.service.PvpowerService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/Pvpower")
@Produces(MediaType.APPLICATION_JSON)
public class PvpowerController {
    @RestClient
    PvpowerService pvpowerService;

    @Inject
    CRUDOperations crud;

    @GET
    public Response data() {
        List<Pvpower> posts = new ArrayList<>(0);
        try {
            posts = pvpowerService.getAll();
        } catch(Exception e) {
            e.printStackTrace();
        }

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

        posts.forEach(x -> crud.add(x));

        Statistic update = new Statistic();
        update.maxValueForToday = maxValue;
        LocalDate localDate = LocalDate.now();
        update.date = localDate.toString();
        crud.add(update);

        return Response.ok(posts).build();
    }
}