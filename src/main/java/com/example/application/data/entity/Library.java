package com.example.application.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "LIBRARY")
public class Library implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @NotBlank
    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Book> getBooks() {
        return new HashSet<>(books);
    }

    public void setEmployees(Set<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
        book.setLibrary(this);
    }

    public void removeEmployee(Book book) {
        books.remove(book);
        book.setLibrary(null);
    }

    @Override
    public String toString() {
        return name;
    }
}
