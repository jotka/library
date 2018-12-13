package dk.nykredit.library;

import dk.nykredit.library.dao.BookDb;
import dk.nykredit.library.domain.Book;
import dk.nykredit.library.rest.BookResource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Arquillian.class)
public class RestEndpointTestIT {

    @ArquillianResource
    URL basePath;

    @Deployment(testable = false)
    public static WebArchive create() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(BookDb.class.getPackage())
                .addPackage(Book.class.getPackage())
                .addPackage(BookResource.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @RunAsClient
    public void shouldGetAllBooks() {
        given().
                when().
                get(basePath + "api/books").
                then().
                body("book.size()", greaterThan(0)).
                statusCode(Response.Status.OK.getStatusCode());

    }

    @Test
    @RunAsClient
    public void shouldCreateBook() {
        Map<String, String> book = new HashMap<>();
        book.put("title", "new book");
        given().
                contentType("application/json").
                body(book).
                when().
                post(basePath + "api/books").
                then().
                statusCode(Response.Status.CREATED.getStatusCode()).extract().asString();

        given().
                when().
                get(basePath + "api/books/3").
                then().
                statusCode(Response.Status.OK.getStatusCode()).
                body("title", equalTo("new book"));
    }

    @Test
    @RunAsClient
    public void shouldNotCreateBookWithoutTitle() {
        Map<String, String> book = new HashMap<>();
        book.put("title", "");

        given().
                contentType("application/json").
                body(book).
                when().
                post(basePath + "api/books").
                then().
                statusCode(Response.Status.BAD_REQUEST.getStatusCode()).extract().asString();
    }

}
