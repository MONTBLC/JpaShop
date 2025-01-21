package jpabook.jpashop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    // Java 의 유효성 검증을 통해서 스프링이 이걸 validation 해준다.
    // name 의 값이 비어있으면 오류가 난다는 것이다.
    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
