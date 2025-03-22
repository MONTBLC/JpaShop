package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

/**
 * 주문 엔티티
 */
@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY) // 다 대 일
    @JoinColumn(name = "member_id")
    private Member member;

    // CascadeType.ALL 되어 있으므로 Order 를 persist 하면
    // 여기 콜렉션에 와있는 OrderItem, Delivery 도 다 persist 를 강제로 날려준다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; // 배송정보

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    //==연관관계 편의 메소드==//
    // 양방향에서는 연관관계 편의 메소드가 있으면 좋다.
    // 양쪽 셋팅을 원자적으로 한 코드로 딱 해결을 하는 거죠.
    public void setMember(Member member) {
        this.member = member;           // order -> member
        member.getOrders().add(this);   // member -> order
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);  // order -> orderItem
        orderItem.setOrder(this);   // orderItem -> order
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;   // order -> delivery
        delivery.setOrder(this);    // delivery -> order
    }
    // 연관 관계 편의 메서드를 작성해야 하는 이유는 바로
    // 객체까지 고려해서 연관관계 주인이 아닌 곳에도 값을 넣어줘야 하기 때문이다!!

    //==생성 메소드==//
    // 생성자 보다 생성 메소드로 작성한 것은 객체 생성 의도를 더 잘 드러내기 위한 방법 중 하나이다!!!
    // OrderItem 을 List 로 넘기면 되는데 여러 개 할 수 있으니 OrderItem... 문법으로 표기한다.
    // 중요한 것은 앞으로 뭔가 생성하는 지점을 변경해야 되면 여기에서만 바꾸면 되기 때문이다.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    // 실무에서는 주로 주문에 전체 주문 가격 필드를 두고 역정규화 한다.
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
//        // stream() 으로 깔끔하게 작성
//        // 추가로 Ctrl + Alt + N 으로 inline 변환
//        return orderItems.stream()
//                .mapToInt(OrderItem::getTotalPrice)
//                .sum();
    }
}
