package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * 상품 - 영화 엔티티
 */
@Entity
@DiscriminatorValue("M")
@Getter @Setter
public class Movie extends Item {
    private String director;
    private String actor;
}
