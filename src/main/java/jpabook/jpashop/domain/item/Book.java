package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * 상품 - 도서 엔티티
 */
@Entity
@DiscriminatorValue("B")
@Getter @Setter
public class Book extends Item {
    private String author;
    private String isbn;
}
