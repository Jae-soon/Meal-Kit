package com.js.mealkitecommerce.app.repository;

import com.js.mealkitecommerce.app.entity.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> findByKitIdAndMaterialKeywordId(Long kitId, Long materialKeywordId);

    List<Material> findAllByKitId(Long id);

    List<Material> findAllByKitIdIn(long[] ids);
}
