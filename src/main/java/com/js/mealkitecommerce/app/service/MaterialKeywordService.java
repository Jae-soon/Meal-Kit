package com.js.mealkitecommerce.app.service;

import com.js.mealkitecommerce.app.entity.material.MaterialKeyword;
import com.js.mealkitecommerce.app.repository.MaterialKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialKeywordService {
    private final MaterialKeywordRepository materialKeywordRepository;
    public MaterialKeyword save(String keywordContent) {
        Optional<MaterialKeyword> optKeyword = materialKeywordRepository.findByContent(keywordContent);

        if ( optKeyword.isPresent() ) {
            return optKeyword.get();
        }

        MaterialKeyword keyword = MaterialKeyword
                .builder()
                .content(keywordContent)
                .build();

        materialKeywordRepository.save(keyword);

        return keyword;
    }
}
