package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

// JPA 를 사용하기 때문에 Entity Manager(엔티티 매니저)가 있어야 한다.
// @PersistenceContext : Entity Manager(엔티티 매니저)를 주입을 해준다.
@Repository
public class MemberRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    // 저장하는 코드
    // 커맨드랑 쿼리를 분리해라!
    // 저장을 하고 나서 가급적이면, 사이드 이펙트를 일으키는 커맨드성이기 때문에 리턴값을 거의 안 만든다.
    // 대신에 아이디 정도 있으면 다음에 다시 조회할 수 있으니까 아이디 정도만 조회하는 것으로 주로 설계를 한다.
    public Long save(MemberJpa member) {
        em.persist(member);
        return member.getId();
    }

    // 하나의 회원 조회하는 코드
    public MemberJpa find(Long id) {
        return em.find(MemberJpa.class, id);
    }
}
