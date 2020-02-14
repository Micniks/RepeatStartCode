/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.MovieFacade;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author Bruger
 */
public class SetupDummies {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/Movie",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final MovieFacade FACADE = MovieFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        Movie movie1 = new Movie(1994, "Chaos", new String[]{"Michael", "Cahit", "Tobias"}, 5.5, 18);
        Movie movie2 = new Movie(1995, "Order", new String[]{}, 5.0, 13);
        Movie movie3 = new Movie(1993, "Law", new String[]{"Andreas"}, 1.5, 0);
        FACADE.addMovie(movie1);
        FACADE.addMovie(movie2);
        FACADE.addMovie(movie3);

    }

}
