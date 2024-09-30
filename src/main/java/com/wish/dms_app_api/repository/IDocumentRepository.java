package com.wish.dms_app_api.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wish.dms_app_api.entity.Document;

@Repository
public interface IDocumentRepository extends JpaRepository<Document, Long> {

	List<Document> findByUserId(Long userId);
	List<Document> findByExtension(String extension);
	@Query("SELECT d FROM Document d JOIN d.tags t WHERE t.name = :tagName")
	List<Document> findDocumentsByTagName(String tagName);
	@Query("SELECT d FROM Document d WHERE d.createdAt = :createdAt")
    List<Document> findByCreatedAt(@Param("createdAt") LocalDate createdAt);



}
