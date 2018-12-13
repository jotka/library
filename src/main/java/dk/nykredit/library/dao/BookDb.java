package dk.nykredit.library.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import dk.nykredit.library.domain.Book;
import dk.nykredit.library.domain.Status;

public class BookDb {
    private static Map<Integer, Book> bookDB = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger();

    public static Integer createBook(String name, Status status) {
        Book c = new Book();
        c.setId(idCounter.incrementAndGet());
        c.setContent(name);
        c.setStatus(status == null ? Status.READ : status);
        bookDB.put(c.getId(), c);
        return c.getId();
    }

    public static Optional<Book> getBook(Integer id) {
        return Optional.ofNullable(bookDB.get(id));
    }

    public static List<Book> getAllBooks() {
        return new ArrayList<>(bookDB.values());
    }

    public static Book removeBook(Integer id) {
        return bookDB.remove(id);
    }

    public static Book updateBook(Integer id, Book c) {
        return bookDB.put(id, c);
    }
}
