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

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Arquillian.class)
public class RestEndpointTest {

    @Deployment(testable = false)
    public static WebArchive create() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(BookDb.class.getPackage())
                .addPackage(Book.class.getPackage())
                .addPackage(BookResource.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test @RunAsClient
    public void shouldCountCars(@ArquillianResource(BookResource.class) URL basePath) {
        System.out.println(basePath);


//        given().
//                when().
//                get(basePath + "api/books").
//                then().
//                statusCode(Response.Status.OK.getStatusCode()).
//                body(equalTo("4"));
    }
}
