package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 회원 엔티티
 */
// @Id만 사용할 경우 기본 키를 직접 할당해 주어야 한다.
// 기본 키를 직접 할당하는 대신 데이터베이스가 생성해주는 값을 사용하려면 @GeneratedValue 를 사용하면 된다.
// 따라서, 기본키를 자동으로 생성할 때에는 @Id와 @GeneratedValue 어노테이션이 함께 사용되어야 한다.
@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    // 임베디드 내장 타입을 포함했다라는 @Embedded 로 맵핑을 해주면 된다.
    @Embedded
    private Address address;

    // 읽기전용이 된다.
    // 연관관계의 주인이 아니기 때문에 mappedBy 를 통해서 order 테이블에 있는 member 필드에 매핑된다.
    @OneToMany(mappedBy = "member") // 일 대 다
    private List<Order> orders = new ArrayList<>();
}
