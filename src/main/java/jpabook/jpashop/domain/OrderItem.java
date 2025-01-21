package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

/**
 * 주문상품 엔티티
 */
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
//    // JPA 는 생성자를 만들 때 Protected 까지 기본 생성자를 만들 수 있게 허용해준다.
//    // JPA 쓰면서 protected 하면 쓰지 말라는 것이다!!
//    // Lombok 의 @NoArgsConstructor 로 어노테이션을 적용하면 생략할 수 있다!!!!
//    protected OrderItem() {}

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // 주문 상품

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // 주문

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    //==생성 메소드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회 로직==//
    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
