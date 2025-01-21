package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception {
        Book book = em.find(Book.class, 1L);

        // Transaction
        book.setName("asdf");

        // 변경 감지 == dirty checking
        // Transaction commit

        // 준영속 엔티티
        // JPA 의 영속성 컨텍스트가 더 이상 관리하지 않는 엔티티를 말한다.

        // 병합은 준영속 상태의 엔티티를 영속 상태로 변경할 때 사용하는 기능이다.

        // 준영속 엔티티를 수정하는 2가지 방법
        // 변경 감지 기능 사용
        // 병합(merge) 사용

        /* 가장 좋은 해결 방법 */
        // 엔티티를 변경할 때는 항상 변경 감지를 사용하세요!!
        // 추가로 하나 더 말씀드리면 setter 없이 엔티티 안에서 바로 추적할 수 있는 메소드를 만드세요.

        // ItemController, ItemService, ItemRepository 참조
    }
}
