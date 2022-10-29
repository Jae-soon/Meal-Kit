package com.js.mealkitecommerce.app.repository;

import com.js.mealkitecommerce.app.entity.allergy.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllergyRepository extends JpaRepository<Allergy, Long> {
    List<Allergy> findAllByKitIdIn(long[] ids);
}
