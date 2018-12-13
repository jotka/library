package dk.nykredit.library.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import dk.nykredit.library.domain.Book;
import dk.nykredit.library.domain.Status;

import javax.annotation.Resource;

@Resource
public class BookDb {
    public BookDb() {
        createBook("Java in practice", Status.READ);
        createBook("Concurrency in Java", Status.UNREAD);
    }

    private static Map<Integer, Book> bookDB = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger();

    public Integer createBook(String name, Status status) {
        Book c = new Book();
        c.setId(idCounter.incrementAndGet());
        c.setTitle(name);
        c.setStatus(status == null ? Status.READ : status);
        bookDB.put(c.getId(), c);
        return c.getId();
    }

    public Optional<Book> getBook(Integer id) {
        return Optional.ofNullable(bookDB.get(id));
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(bookDB.values());
    }

    public Book removeBook(Integer id) {
        return bookDB.remove(id);
    }

    public Book updateBook(Integer id, Book c) {
        return bookDB.put(id, c);
    }
}
