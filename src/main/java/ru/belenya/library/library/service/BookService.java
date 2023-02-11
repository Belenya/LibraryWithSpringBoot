package ru.belenya.library.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.belenya.library.library.models.Book;
import ru.belenya.library.library.repositories.BookRepository;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository repository) {
        this.bookRepository = repository;
    }

    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    public List<Book> findAll(Sort sort) {
        return (List<Book>) bookRepository.findAll(sort);
    }

    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public List<Book> findAllByNameContains(String string) {
        return (List<Book>) bookRepository.findByNameContains(string);
    }

    public Book findById(Long id) {
        return (Book) bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void update(Book book) {
    bookRepository.save(book);
    }
}
