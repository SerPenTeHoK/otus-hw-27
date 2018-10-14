package ru.sergey_gusarov.hw27.app.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.sergey_gusarov.hw27.service.books.BookService;

@Component
public class HealthCheckBooksCountInLib implements HealthIndicator {
    private final BookService bookService;

    private static int NORMAL_LIB_BOOKS_COUNT = 50;

    public HealthCheckBooksCountInLib(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public Health health() {
        int errorCode = check();
        if (errorCode == 0) {
            return Health.down()
                    .withDetail("Less "+ NORMAL_LIB_BOOKS_COUNT +" book in library", errorCode).build();
        }
        return Health.up().build();
    }

    public int check() {
        if(bookService.count() < NORMAL_LIB_BOOKS_COUNT)
            return 0;
        return 1;
    }
}
