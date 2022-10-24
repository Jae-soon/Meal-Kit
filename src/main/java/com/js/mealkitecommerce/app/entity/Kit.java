package com.js.mealkitecommerce.app.entity;

import com.js.mealkitecommerce.app.entity.allergy.Allergy;
import com.js.mealkitecommerce.app.entity.material.Material;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Kit extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer member;

    private String title;
    private String kind;
    private int price;
    private int quantity;
    @Column(columnDefinition = "TEXT")
    private String how;
    private int discount;

    public String getExtra_inputValue_materialContents() {
        Map<String, Object> extra = getExtra();

        if (extra.containsKey("material") == false) {
            return "";
        }

        List<Material> materials = (List<Material>) extra.get("material");

        if (materials.isEmpty()) {
            return "";
        }

        return materials
                .stream()
                .map(material -> "#" + material.getMaterialKeyword().getContent())
                .sorted()
                .collect(Collectors.joining(" "));
    }

    public String getExtra_materialLinks() {
        Map<String, Object> extra = getExtra();

        if (extra.containsKey("material") == false) {
            return "";
        }

        List<Material> materials = (List<Material>) extra.get("material");

        if (materials.isEmpty()) {
            return "";
        }

        return materials
                .stream()
                .map(material -> {
                    String text = "#" + material.getMaterialKeyword().getContent();

                    return """
                            <a href="%s&memberId=%d">%s</a>
                            """
                            .stripIndent()
                            .formatted(material.getMaterialKeyword().getListUrl(), material.getKit().getId(), material.getMaterialKeyword().getContent(), text);
                })
                .sorted()
                .collect(Collectors.joining(" "));
    }

    public String getExtra_inputValue_allergyContents() {
        Map<String, Object> extra = getExtra();

        if (extra.containsKey("allergy") == false) {
            return "";
        }

        List<Allergy> allergies = (List<Allergy>) extra.get("allergy");

        if (allergies.isEmpty()) {
            return "";
        }

        return allergies
                .stream()
                .map(allergy -> "#" + allergy.getAllergyKeyword().getContent())
                .sorted()
                .collect(Collectors.joining(" "));
    }

    public String getExtra_allergyLinks() {
        Map<String, Object> extra = getExtra();

        if (extra.containsKey("allergy") == false) {
            return "";
        }

        List<Allergy> allergies = (List<Allergy>) extra.get("allergy");

        if (allergies.isEmpty()) {
            return "";
        }

        return allergies
                .stream()
                .map(allergy -> {
                    String text = "#" + allergy.getAllergyKeyword().getContent();

                    return """
                            <a href="%s&memberId=%d">%s</a>
                            """
                            .stripIndent()
                            .formatted(allergy.getAllergyKeyword().getListUrl(), allergy.getKit().getId(), allergy.getAllergyKeyword().getContent(), text);
                })
                .sorted()
                .collect(Collectors.joining(" "));
    }
}
