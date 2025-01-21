package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

// @RunWith(SpringRunner.class) : 스프링과 테스트 통합
// JPA 가 실제 DB 까지 도는걸 보여드리는게 되게 중요해서 메모리 모드로 DB 까지 엮어서 테스트를 하는게 중요하다!!
// 그래서 완전히 스프링이랑 인티그레이션 해가지고 테스트를 할 것이다!
// @Transactional : 반복 가능한 테스트 지원.
// 각각의 테스트를 실행할 때마다 트랜잭션을 시작하고 테스트가 끝나면 트랜잭션을 강제로 롤백한다.(이 어노테이션이 테스트 케이스에서 사용될 때만 롤백)
// @Transactional 을 그냥 적용하고 돌리면 insertQuery 가 안 보일 것이다.
// 정확하게 말씀드리면 영속성 컨텍스트(PersistenceContext) 가 플러시(flush)를 안해버린다.
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

//    @Autowired
//    EntityManager em;

    @Test
//    @Rollback(value = false) // false 로 하고 돌리면 인서트 쿼리가 남게 된다.
    public void 회원가입() throws Exception {
        // given 이런게 주어졌을 때
        Member member = new Member();
        member.setName("memberA");

        // when 이렇게 하면
        Long saveId = memberService.join(member);
        System.out.println(saveId); // 1
        System.out.println(memberRepository.findOne(saveId)); // jpabook.jpshop.domain.Member@6d985720

        // then 이렇게 된다, 검증해라
//        em.flush(); // 일단 flush() 하면 DB의 쿼리가 강제로 나가는 것이다. 그러다가 스프링 테스트 가 끝나면 인서트 됐다가 롤백을 해버린다.
        // 왜냐면 이 테스트가 반복해서 돼야 하기 때문에 DB 에 데이터가 남으면 안되는 것이다.
        assertEquals(member, memberRepository.findOne(saveId));
    }

//    @Test(expected = IllegalStateException.class) // Junit5 에서는 지원하지 않는다.
    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("MemberA");

        Member member2 = new Member();
        member2.setName("MemberA");

        // when
        memberService.join(member1);
//        try {
//            memberService.join(member2); // 예외가 발생해야 한다!!
//        } catch (IllegalStateException e) {
//            return;
//        }
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });

        // then
//        fail("예외가 발생해야 한다.");
    }
}
