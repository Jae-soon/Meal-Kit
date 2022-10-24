package com.js.mealkitecommerce.app.service;

import com.js.mealkitecommerce.app.entity.Kit;
import com.js.mealkitecommerce.app.entity.material.Material;
import com.js.mealkitecommerce.app.entity.material.MaterialKeyword;
import com.js.mealkitecommerce.app.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialKeywordService materialKeywordService;
    private final MaterialRepository materialRepository;

    public void applyHashTags(Material material, String materialContents) {
        List<Material> oldMaterials = getMaterials(material);

        List<String> keywordContents = Arrays.stream(materialContents.split(","))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        List<Material> needToDelete = new ArrayList<>();

        for (Material oldMaterial : oldMaterials) {
            boolean contains = keywordContents.stream().anyMatch(s -> s.equals(oldMaterial.getMaterialKeyword().getContent()));

            if (contains == false) {
                needToDelete.add(oldMaterial);
            }
        }

        needToDelete.forEach(hashTag -> {
            materialRepository.delete(hashTag);
        });

        keywordContents.forEach(keywordContent -> {
            saveHashTag(material, keywordContent);
        });
    }

    private Material saveHashTag(Kit kit, String keywordContent) {
        MaterialKeyword keyword = materialKeywordService.save(keywordContent);

        Optional<Material> opHashTag = materialRepository.findByMaterialIdAndMaterialKeywordId(material.getId(), keyword.getId());

        if (opHashTag.isPresent()) {
            return opHashTag.get();
        }

        PostHashTag hashTag = PostHashTag.builder()
                .member(post.getMember())
                .post(post)
                .postKeyword(keyword)
                .build();

        postHashTagRepository.save(hashTag);

        return hashTag;
    }

    private List<Material> getMaterials(Material material) {
        return materialRepository.findAllByMaterialId(material.getId());
    }


}
