package ru.belenya.library.library.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Название не может быть пустым.")
    @Size(min = 2, max = 100, message = "Допустимая длинна от 2 до 100 символов.")
    private String name;

    @NotEmpty(message = "Автор не может мыть пустым.")
    @Size(min = 2, max = 100, message = "Допустимая длинна от 2 до 100 символов.")
    private String author;

    @Min(value = 1500, message = "Минимальный год 1500.")
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Timestamp timestamp;

    @Transient
    private boolean isOverdue;

    public void setUser(User user) {
        this.user = user;
        timestamp = Objects.isNull(user) ? null : new Timestamp(System.currentTimeMillis());
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public boolean isOverdue() {
        if (!isOverdue & !Objects.isNull(timestamp)) {
            long overdueDate = Timestamp.valueOf(timestamp.toLocalDateTime().plusDays(10)).getTime();
            isOverdue = System.currentTimeMillis() >= overdueDate;
        }
        return isOverdue;
    }

}
