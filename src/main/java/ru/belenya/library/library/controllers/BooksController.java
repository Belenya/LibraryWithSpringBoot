package ru.belenya.library.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.belenya.library.library.models.Book;
import ru.belenya.library.library.models.User;
import ru.belenya.library.library.service.BookService;
import ru.belenya.library.library.service.UserService;

import java.util.List;


@Controller
@RequestMapping("books")
public class BooksController {

    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public BooksController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping
    public String books(Model model,
                        @RequestParam(name = "page", defaultValue = "-1") int page,
                        @RequestParam(name = "books_per_page", defaultValue = "1") int booksPerPage,
                        @RequestParam(name = "sort_by_year", defaultValue = "false") boolean sortByYear) {
        List<Book> books = null;
        if (page == -1 & sortByYear) {
            books = bookService.findAll(Sort.by(Sort.Direction.ASC, "year"));
        }
        if (page > -1 & !sortByYear & booksPerPage > 0) {
            books = bookService.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
        if (page > -1 & sortByYear & booksPerPage > 0) {
            books = bookService.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        }
        model.addAttribute("books", books == null ? bookService.findAll() : books);
        return "books/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") long id,
                           Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("emptyUser", new User());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping
    public String createBook(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("{id}/edit")
    public String updateBook(Model model, @PathVariable("id") long id) {
        model.addAttribute("book", bookService.findById(id));
        return "books/edit";
    }

    @PatchMapping("{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookService.update(book);
        return "redirect:/books";
    }

    @PatchMapping("{id}/set")
    public String setUser(@ModelAttribute("emptyUser") User user, @PathVariable("id") long id) {
        Book book = bookService.findById(id);
        book.setUser(userService.findById(user.getId()));
        bookService.update(book);
        return "redirect:/books";
    }

    @PatchMapping("{id}/free")
    public String freeBook(@PathVariable("id") long id) {
        Book book = bookService.findById(id);
        book.setUser(null);
        bookService.update(book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        Book book = new Book();
        book.setId(id);
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("search")
    public String searchByName(@RequestParam(name = "name", defaultValue = "") String name, Model model) {
        model.addAttribute("searchResult", null);
        if (!name.isEmpty()) {
            model.addAttribute("searchResult", bookService.findAllByNameContains(name));
        }
        return "books/search";
    }
}
