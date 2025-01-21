package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Spring 에서 제공하는 @Repository 를 사용하면 @ComponentScan 의 대상이 되어서 자동으로 Spring bean 으로 관리가 된다.
@Repository
@RequiredArgsConstructor //  스프링 부트가 지원하는 Spring Data JPA 라이브러리를 적용하면 간편하게 쓸 수 있다.
public class MemberRepository {

/*
    // 혹시 만약에 EntityManagerFactory 를 직접 주입하고 싶다면
    // 거의 쓸일이 없다.
    @PersistenceUnit
    private EntityManagerFactory emf;
*/

    // JPA 가 제공하는 표준 어노테이션인 PersistenceContext
    // 스프링이 주입해준다.
//    @PersistenceContext

    // 참고로 EntityManager 는 @Autowired 가 안되고 @PersistenceContext 표준 어노테이션이 있어야 인젝션이 되지만,
    // Spring Data JPA 가 @Autowired 도 인젝션되게 지원을 해준다.
    @Autowired
    private final EntityManager em;

    // 저장 로직
    // ItemRepository 와는 다르게 MemberRepository 의 save() 메소드는 일반적으로,
    // Member 객체가 생성된 이후에 변경되지 않거나, 변경 시마다 다시 저장되지 않는 경우가 많다! (생성, 삭제)
    public void save(Member member) {
        em.persist(member); // PersistenceContext 에 Member 엔티티를 넣는다.
        // 나중에 트랜잭션이 commit 되는 시점에 db 에 반영이 된다. db 에 insertQuery 가 날라간다.
    }

    // 단 건 조회
    public Member findOne(Long id) {
        // EntityManager 의 find() 메소드가 Member 를 찾아서 반환해준다.
        return em.find(Member.class, id);
    }

    // 목록 조회
    public List<Member> findAll() {
        // JPQL
        // SQL 이랑 차이점은 SQL 은 테이블을 대상으로 쿼리를 하지만, JPQL 은 Entity 객체를 대상으로 쿼리를 한다.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // (특정 속성)이름으로 회원을 조회
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
