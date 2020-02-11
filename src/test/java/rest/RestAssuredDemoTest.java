package rest;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.parsing.Parser;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RestAssuredDemoTest {

    @BeforeAll
    public static void setup() {
        RestAssured.defaultParser = Parser.JSON;
    }

//    @Test
//    public void
//            movie_resource_returns_200_with_expected_name() {
//
//        String expectedTitle = "Chaos";
//
//        given().
//                when().
//                get("/title/{name}", expectedTitle).
//                then().
//                statusCode(200).
//                body("movie.name", equalTo(expectedTitle));
//
//    }

    @Test
    public void testExternal() {
        given().log().all().get("http://api.icndb.com/jokes/random").then().log().body();
    }

}
