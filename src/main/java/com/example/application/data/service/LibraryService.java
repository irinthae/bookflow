package com.example.application.data.service;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Library;
import com.example.application.data.repository.BookRepository;
import com.example.application.data.repository.LibraryRepository;
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

    public LibraryService(LibraryRepository libraryRepository, BookRepository bookRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
    }

    public void update(Library library) {
        libraryRepository.save(library);
    }

    public Optional<Library> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return libraryRepository.findById(id);
    }

    public Stream<Library> findAll(PageRequest pageRequest) {
        return libraryRepository.findAll(pageRequest).stream();
    }

    public void deleteByID(Long id) {
        libraryRepository.deleteById(id);
    }

    public Stream<Book> findBooksByLibraryId(Long id, Pageable pageable) {
        return bookRepository.findByLibrary_Id(id, pageable).stream();
    }




}
