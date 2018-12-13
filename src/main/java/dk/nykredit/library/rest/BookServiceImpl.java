package dk.nykredit.library.rest;

import dk.nykredit.library.dao.BookDb;
import dk.nykredit.library.domain.Book;
import dk.nykredit.library.domain.Status;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookServiceImpl implements BookService {

    @Inject
    BookDb bookDb;

    public BookServiceImpl() {
    }

    public List<Book> getAllBooks() {
        return bookDb.getAllBooks();
    }

    public Book getBookById(Integer id) throws BookException {
        Optional<Book> book = bookDb.getBook(id);
        return book.orElseThrow(() -> new BookException("Book not found.", Response.Status.NOT_FOUND));
    }

    @Override
    public Integer createBook(String title, Status status) throws BookException {
        if (title == null) throw new BookException("Book title for create missing.", Response.Status.BAD_REQUEST);

        return bookDb.createBook(title, status);
    }

    public Book updateBook(Integer id, Book book) throws BookException {
        Optional<Book> bookOptional = bookDb.getBook(id);
        if (bookOptional.isPresent()) {
            if (book.getTitle() == null)
                throw new BookException("Book title for update missing.", Response.Status.BAD_REQUEST);
            bookDb.updateBook(id, book);
            return bookOptional.get();
        } else throw new BookException("Book to delete not found.", Response.Status.NOT_FOUND);
    }

    public void deleteBook(Integer id) throws BookException {
        Optional<Book> bookOptional = bookDb.getBook(id);
        if (bookOptional.isPresent()) {
            bookDb.removeBook(id);
        } else throw new BookException("Book to delete not found.", Response.Status.NOT_FOUND);
    }

}