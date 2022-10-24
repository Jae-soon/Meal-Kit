package com.js.mealkitecommerce.app.entity.allergy;

import com.js.mealkitecommerce.app.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class AllergyKeyword extends BaseEntity {
    private String content;

    public String getListUrl() {
        return "/post/list?kwType=keyword&kw=%s".formatted(content);
    }
}
