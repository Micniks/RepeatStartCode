package rest;

import entities.Movie;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

public class MovieResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Movie m1,m2,m3,m4,m5;
    
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    
    

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);
        
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }
    
    @AfterAll
    public static void closeTestServer(){
         EMF_Creator.endREST_TestWithDB();
         httpServer.shutdownNow();
    }
    
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        m1 = new Movie(1993, "Law & Order", new String[]{"Andreas"}, 3.0, 13);
        m2 = new Movie(1997, "Chaos Riders", new String[]{"Michael", "Marcus", "Cahit"}, 4.3, 18);
        m3 = new Movie(2003, "The First Humans", new String[]{"Adam", "Eve", "Chuck Norris"}, 0.3, 8);
        m4 = new Movie(2005, "The First", new String[]{"Chuck Norris"}, 9.9, 18);
        m5 = new Movie(2019, "Last", new String[]{"Chuck Norris"}, 10.0, 18);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(m1);
            em.persist(m2); 
            em.persist(m3);
            em.persist(m4);
            em.persist(m5);
            em.getTransaction().commit();
        } finally { 
            em.close();
        }
    }
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/movie").then().statusCode(200);
    }
   
    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
        .contentType("application/json")
        .get("/movie/").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("msg", equalTo("Hello World"));   
    }
    
    @Test
    public void testCount() throws Exception {
        given()
        .contentType("application/json")
        .get("/movie/count").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("count", equalTo(5));   
    }
    
    @Test
    public void testAll() throws Exception {
        given()
        .contentType("application/json")
        .get("/movie/all")
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("[0].actors", hasItem("Chuck Norris"));   
    }
    
    @Test
    public void testName() throws Exception {
        given()
        .contentType("application/json")
        .get("movie/name/"+m3.getName())
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("[0].name", equalTo(m3.getName()));   
    }
    
    @Test
    public void testNoName() throws Exception {
        given()
        .contentType("application/json")
        .get("movie/name/NoSuchNameListThis")
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body(equalTo("[]"));   
    }
    
    @Test
    public void testID() throws Exception {
        
        given()
        .contentType("application/json")
        .get("movie/id/"+m1.getId().toString())
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("name", equalTo(m1.getName()));   
    }
}
