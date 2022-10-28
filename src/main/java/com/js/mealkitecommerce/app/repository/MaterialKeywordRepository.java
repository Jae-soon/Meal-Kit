package com.js.mealkitecommerce.app.repository;

import com.js.mealkitecommerce.app.entity.material.MaterialKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialKeywordRepository extends JpaRepository<MaterialKeyword, Long> {
    Optional<MaterialKeyword> findByContent(String keywordContent);
}
