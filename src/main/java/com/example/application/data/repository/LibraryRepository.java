package com.example.application.data.repository;

import com.example.application.data.entity.Library;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long>, JpaSpecificationExecutor<Library> {

    @Query("select l from Library l join fetch l.books b where b.id = :bookId")
    Library findLibraryByBookId(Long bookId);

    @Query("select l.id from Library l where l.name = :name")
    Long findLibraryIdByName(String name);

    @Query("select l.name from Library l")
    List<String> findLabels(Pageable pageable);
}
