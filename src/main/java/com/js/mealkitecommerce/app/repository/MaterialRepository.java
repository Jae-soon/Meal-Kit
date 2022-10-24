package com.js.mealkitecommerce.app.repository;

import com.js.mealkitecommerce.app.entity.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    List<Material> findAllByMaterialId(Long id);
}
