package ru.belenya.library.library.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.belenya.library.library.models.Book;

import java.util.Optional;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Iterable<Book> findByNameContains(String string);

    Optional<Object> findById(Long id);

    void save(Book book);

    void deleteById(Long id);

    Object findAll();
}
