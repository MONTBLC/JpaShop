package jpabook.jpashop;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

// @Id : id 식별자를 id 맵핑을 해준다.
// @GeneratedValue : 자동 생성되게 만든다. = DB 가 자동 생성을 해준다.
@Entity
@Getter @Setter
public class MemberJpa {

    @Id @GeneratedValue
    private Long id;
    private String username;
}
