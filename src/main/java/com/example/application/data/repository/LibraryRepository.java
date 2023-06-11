package com.example.application.data.repository;

import com.example.application.data.entity.Library;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LibraryRepository extends JpaRepository<Library, Long>, JpaSpecificationExecutor<Library> {

    //TODO
    @Query("select l from Library l inner join Book b on l.id = b.library.id where b.id = :bookId")
    Library findLibraryByBook_Id(Long bookId);

    @Query("select l from Library l join fetch l.books b where l.name = : name")
    Library findLibrary(@Param("name") String name);

}
