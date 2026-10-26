package com.elibrary.infrastructure;

import com.elibrary.domain.book.Book;
import com.elibrary.domain.book.ContentType;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleDataLoader implements ApplicationRunner {
    private final JpaBookStore books;

    public SampleDataLoader(JpaBookStore books) {
        this.books = books;
    }

    @Override
    public void run(ApplicationArguments args) {
        save("book-ddd", "Domain-Driven Design", "Eric Evans", "9780321125217",
                ContentType.EBOOK, "Strategic and tactical design for complex software.", 2);
        save("book-clean", "Clean Architecture", "Robert C. Martin", "9780134494166",
                ContentType.EBOOK, "Dependency rules and boundaries in application structure.", 1);
        save("book-ddia", "Designing Data-Intensive Applications", "Martin Kleppmann", "9781449373320",
                ContentType.EBOOK, "Storage, replication, and consistency for data systems.", 2);
        save("journal-ieee", "IEEE Software — Architecture Issue", "IEEE", "0740-7459",
                ContentType.JOURNAL, "A sample journal issue on software architecture.", 5);
        save("journal-nature", "Nature — Computing Column", "Springer Nature", "0028-0836",
                ContentType.JOURNAL, "Short research briefs related to computing.", 4);
    }

    private void save(String id, String title, String author, String isbn, ContentType type, String summary, int seats) {
        books.save(new Book(id, title, author, isbn, type, summary, seats, seats, "/content/" + id));
    }
}
