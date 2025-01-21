package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 배송 엔티티
 */
@Entity
@Getter @Setter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    // STRING 으로 해야 중간에 ENUM 값이 들어가도 순서에 의해서 밀리는 것이 없다.
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP
}
