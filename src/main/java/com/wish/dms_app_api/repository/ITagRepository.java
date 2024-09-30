package com.wish.dms_app_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wish.dms_app_api.entity.DocumentTags;

@Repository
public interface ITagRepository extends JpaRepository<DocumentTags, Long> {
    Optional<DocumentTags> findByName(String name);
}
