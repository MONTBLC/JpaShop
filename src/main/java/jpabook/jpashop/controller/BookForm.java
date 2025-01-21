package jpabook.jpashop.controller;

import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Getter @Setter
public class BookForm {
    // 상품의 공통 속성
    private Long id;

    @NotEmpty(message = "상품명은 필수입니다.")
    private String name;

    private int price;
    private int stockQuantity;

    // 책과 관련된 특별한 속성
    private String author;
    private String isbn;
}
