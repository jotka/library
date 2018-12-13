package dk.nykredit.library.rest;

import dk.nykredit.library.domain.Book;
import dk.nykredit.library.domain.Status;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Integer id) throws BookException;
    Integer createBook(String content, Status status) throws BookException;
    Book updateBook(Integer id, Book book) throws BookException;
    void deleteBook(Integer id) throws BookException;
}
