package com.js.mealkitecommerce.app.service;

import com.js.mealkitecommerce.app.entity.allergy.Allergy;
import com.js.mealkitecommerce.app.repository.AllergyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllergyService {
    private final AllergyRepository allergyRepository;
    public List<Allergy> getAllergiesByKitIdIn(long[] ids) {
        return allergyRepository.findAllByKitIdIn(ids);
    }
}
