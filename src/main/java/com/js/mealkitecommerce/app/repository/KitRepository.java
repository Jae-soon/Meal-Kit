package com.js.mealkitecommerce.app.repository;

import com.js.mealkitecommerce.app.entity.Kit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KitRepository extends JpaRepository<Kit, Long> {
    List<Kit> findAllByOrderByIdDesc();
}
