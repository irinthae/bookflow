package com.example.application.data.repository;

import com.example.application.data.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LibraryRepository extends JpaRepository<Library, Long>, JpaSpecificationExecutor<Library> {
}
