package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 상품 엔티티
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    // 편의를 위해서 Setter 를 넣어놨는데 Setter 를 넣는 게 아니라
    // StockQuantity 를 생성이 아니라 변경해야 될 일이 있으면
    // 핵심 비즈니스 메소드를 가지고 변경을 해야 되는 것이다.
    // 가장 객체 지향적인 것이다.
    /**
     * stock 증가
     */
    //  재고가 증가하거나 상품 주문을 취소해서 재고를 다시 늘려야 할 때 사용한다.
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    // 만약 재고가 부족하면 예외가 발생한다. 주로 상품을 주문할 때 사용한다.
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
