package com.example.application.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Year;

@Entity
@Table(name = "BOOK")
public class Book implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(name = "title")
    private String title;

    @NotBlank
    @Column(name = "author")
    private String author;

    @NotNull
    @Column(name = "language1")
    @Enumerated(EnumType.STRING)
    private Language language1;

    @Column(name = "language2")
    @Enumerated(EnumType.STRING)
    private Language language2;

    @NotNull
    @Column(name = "yearOfPublication")
    private Integer yearOfPublication;

    @NotNull
    @Column(name = "pages")
    private Integer pages;

    @NotNull
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    /*@NotNull
    @Column(name = "availability")
    private Boolean availability;*/

    @Size(max = 1000)
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_library")
    private Library library;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Language getLanguage1() {
        return language1;
    }

    public void setLanguage1(Language language) {
        this.language1 = language;
    }

    public Language getLanguage2() {
        return language2;
    }

    public void setLanguage2(Language language) {
        this.language2 = language;
    }

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    /*public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }*/

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
