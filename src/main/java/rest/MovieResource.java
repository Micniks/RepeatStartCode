package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.MovieDTO;
import entities.Movie;
import utils.EMF_Creator;
import facades.MovieFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("movie")
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/Movie",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final MovieFacade FACADE =  MovieFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieCount() {
        long count = FACADE.getMovieCount();
        return "{\"count\":"+count+"}";
    }
    
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovies() {
        List<Movie> list = FACADE.getAllMovies();
        return GSON.toJson(list);
    }
    
    @Path("/name/{name}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovie(@PathParam("name") String name) {
        List<MovieDTO> movies = FACADE.getMovieName(name);
        return GSON.toJson(movies);
    }
    
    @Path("/id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovie(@PathParam("id") Long id) {
        Movie movie = FACADE.getMovieID(id);
        return GSON.toJson(movie);
    }
 
}
