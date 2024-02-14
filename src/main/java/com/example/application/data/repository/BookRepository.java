package com.example.application.data.repository;

import com.example.application.data.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Page<Book> findByLibrary_Id(Long LibraryId, Pageable pageable);

    @Query("SELECT b " + "FROM Book b " + "WHERE (LOWER(CONCAT(b.title, ' ', b.author)) LIKE :keyword)")
    List<Book> searchBooks(String keyword, Pageable pageable);

    @Query("SELECT count(b.id) " + "FROM Book b " + "where b.library.id = :libraryId")
    Long countLibraryId(Long libraryId);

    @Modifying
    @Query("update Book b set b.library.id = :library where b.id = :bookId")
    void updateReference(Long bookId, Long library);
}
