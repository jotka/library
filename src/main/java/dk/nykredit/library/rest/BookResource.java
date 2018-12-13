package dk.nykredit.library.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import dk.nykredit.library.domain.Book;
import dk.nykredit.library.domain.Books;
import dk.nykredit.library.domain.Message;

/**
 * This REST resource has common path "/books" and
 * represents books collection resource as well as individual collection resources.
 * <p>
 * Default MIME type for this resource is "application/json"
 */
@RequestScoped
@Path("/books")
@Produces("application/json")
public class BookResource {
    /**
     * Use uriInfo to get current context path and to build HATEOAS links
     */
    @Context
    UriInfo uriInfo;

    @Inject
    BookService bookService;


    /**
     * Get books collection resource mapped at path "HTTP GET /books"
     */
    @GET
    public Books getBooks() {
        List<Book> list = bookService.getAllBooks();
        Books books = new Books();
        books.setBooks(list);
        books.setSize(list.size());

        //Set link for primary collection
        Link link = Link.fromUri(uriInfo.getPath()).rel("uri").build();
        books.setLink(link);

        //Set links in book items
        for (Book c : list) {
            Link lnk = Link.fromUri(uriInfo.getPath() + "/" + c.getId()).rel("self").build();
            c.setLink(lnk);
        }

        return books;
    }

    /**
     * Get individual book resource mapped at path "HTTP GET /books/{id}"
     */
    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") Integer id) {
        try {
            Book book = bookService.getBookById(id);
            UriBuilder builder = UriBuilder.fromResource(BookResource.class).path(BookResource.class, "getBookById");
            Link link = Link.fromUri(builder.build(id)).rel("self").build();
            book.setLink(link);

            return Response.status(Response.Status.OK).entity(book).build();
        } catch (BookException exc) {
            return Response.status(exc.getStatus()).entity(new Message(exc.getMessage())).build();
        }
    }

    /**
     * Create NEW book resource in books collection resource
     */
    @POST
    @Consumes("application/json")
    public Response createBook(Book book) {
        try {
            Integer id = bookService.createBook(book.getTitle(), book.getStatus());
            Link lnk = Link.fromUri(uriInfo.getPath() + "/" + id).rel("self").build();
            return Response.status(Response.Status.CREATED).location(lnk.getUri()).build();
        } catch (BookException exc) {
            return Response.status(exc.getStatus()).entity(new Message(exc.getMessage())).build();
        }
    }

    /**
     * Modify EXISTING book resource by it's "id" at path "/books/{id}"
     */
    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response updateBook(@PathParam("id") Integer id, Book book) {
        try {
            bookService.updateBook(id, book);
            return Response.status(Response.Status.OK).entity(new Message("Book Updated Successfully")).build();
        } catch (BookException exc) {
            return Response.status(exc.getStatus()).entity(new Message(exc.getMessage())).build();
        }
    }

    /**
     * Delete book resource by it's "id" at path "/book/{id}"
     */
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Integer id) {
        try {
            bookService.deleteBook(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (BookException exc) {
            return Response.status(exc.getStatus()).entity(new Message(exc.getMessage())).build();
        }
    }
}