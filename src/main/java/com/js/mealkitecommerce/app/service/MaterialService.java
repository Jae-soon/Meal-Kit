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

    public void applyMaterial(Kit kit, String materialContents) {
        List<Material> oldMaterials = getMaterials(kit);

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
            saveMaterial(kit, keywordContent);
        });
    }

    private Material saveMaterial(Kit kit, String keywordContent) {
        MaterialKeyword keyword = materialKeywordService.save(keywordContent);

        Optional<Material> opMaterial = materialRepository.findByKitIdAndMaterialKeywordId(kit.getId(), keyword.getId());

        if (opMaterial.isPresent()) {
            return opMaterial.get();
        }

        Material material = Material.builder()
                .kit(kit)
                .materialKeyword(keyword)
                .build();

        materialRepository.save(material);

        return material;
    }

    private List<Material> getMaterials(Kit kit) {
        return materialRepository.findAllByKitId(kit.getId());
    }


    public List<Material> getMaterialsByKitIdIn(long[] ids) {
        return materialRepository.findAllByKitIdIn(ids);
    }
}
