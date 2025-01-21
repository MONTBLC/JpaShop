package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 전체
@RequiredArgsConstructor
public class ItemService {

    // 상품 서비스는 상품 리포지토리에 단순하게 위임만 하는 클래스이다!!!!

    private final ItemRepository itemRepository;

    // (readOnly = true) 면 저장이 안된다!!!!
    @Transactional // 우선권
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     영속성 컨텍스트가 자동 변경
     */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) { // 트랜잭션이 있는 서비스 계층에 식별자(id)와 변경할 데이터를 명확하게 전달하세요.
        Item findItem = itemRepository.findOne(itemId); // 같은 엔티티를 조회한다.
        findItem.setName(name); // 데이터를 수정한다.
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
        // Transaction commit 시점에서 변경 감지(Dirty Checking)가 동작해서 DB 에 UPDATE SQL 실행
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
