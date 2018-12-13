package dk.nykredit.library;

import dk.nykredit.library.dao.BookDb;
import dk.nykredit.library.domain.Book;
import dk.nykredit.library.rest.BookResource;
import dk.nykredit.library.rest.BookService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class BookServiceTest {

    @Deployment(testable = true) //This run INSIDE the container.
    public static WebArchive create() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(BookDb.class.getPackage())
                .addPackage(Book.class.getPackage())
                .addPackage(BookResource.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void getBookById(BookService bookService) {
        //Given

        //When
        final List result = bookService.getAllBooks();

        //Then
        assertNotNull(result);
        assertEquals(2, result.size());
    }

}

