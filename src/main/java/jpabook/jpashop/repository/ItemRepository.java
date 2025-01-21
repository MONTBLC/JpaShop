package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) { // Item 객체가 새로 등록될 뿐만 아니라, 이미 등록된 Item 이 수정될 때도 있다. (생성, 변경)
        // Item 은 JPA 저장하기 전까지 id 값이 없다.(id 값이 없다 = 새로 생성한 객체다.)
        if (item.getId() == null) { // id가 없으면
            em.persist(item); // 신규로 등록
        } else { // id가 있으면 이미 db 에 등록된 것을 가져옴
            em.merge(item); // 여기서 merge 는 업데이트 비슷한 것이라고 보면 된다.
            // 파라미터로 넘어온 item 은 영속성 상태로 변하지 않는다.
            // Item mergeItem = em.merge(item); 에 mergeItem 는 병합이 돼서 영속성 컨텍스트로 관리되는 객체이다.
        }
    }

    // 한,단 건은 find()!!
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    // 여러개 건은 JPQL!!
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
