package com.js.mealkitecommerce.app.entity.allergy;

import com.js.mealkitecommerce.app.entity.BaseEntity;
import com.js.mealkitecommerce.app.entity.Kit;
import com.js.mealkitecommerce.app.entity.material.MaterialKeyword;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Allergy extends BaseEntity {
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Kit kit;

    @ManyToOne
    @ToString.Exclude
    private AllergyKeyword allergyKeyword;
}
