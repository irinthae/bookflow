package com.example.application.data.service;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Library;
import com.example.application.data.repository.BookRepository;
import com.example.application.data.repository.LibraryRepository;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.vaadin.flow.data.provider.Query;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository, BookRepository bookRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
    }

    public void update(Library library) {
        libraryRepository.save(library);
    }

    public void update(Book book) {
        bookRepository.save(book);
    }

    public Optional<Library> findLibraryById(Long libraryID) {
        if (libraryID == null) {
            return Optional.empty();
        }
        return libraryRepository.findById(libraryID);
    }

    public Optional<Book> findBookById(Long bookID) {
        if (bookID == null) {
            return Optional.empty();
        }
        return bookRepository.findById(bookID);
    }

    public Stream<Library> findAllLibraries(PageRequest pageRequest) {
        return libraryRepository.findAll(pageRequest).stream();
    }

    public Stream<Book> findAllBooks(PageRequest pageRequest) {
        return bookRepository.findAll(pageRequest).stream();
    }

    public void deleteByID(Long id) {
        libraryRepository.deleteById(id);
    }

    public Stream<Book> findBooksByLibraryId(Long id, Pageable pageable) {
        return bookRepository.findByLibrary_Id(id, pageable).stream();
    }

    //TODO
    public Library findLibraryByBookId(Long bookId) {
        return libraryRepository.findLibraryByBook_Id(bookId);
    }

    public long countBooks() {
        return bookRepository.count();
    }

    public long countBooksByLibraryId(Long libraryId) {
        return bookRepository.countLibraryId(libraryId);
    }

    public long countLibraries() {
        return libraryRepository.count();
    }

    public Stream<Book> findBooks(String keyword, Query<Book, Void> query) {
        return bookRepository.searchBooks("%" + keyword.toLowerCase() + "%", toPageable(query)).stream();
    }

    public <T, F> Pageable toPageable(Query<T, F> query) {
        return PageRequest.of(
                query.getPage(),
                query.getPageSize(),
                VaadinSpringDataHelpers.toSpringDataSort(query)
        );
    }
}
