package jpabook.jpashop;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

// @RunWith() : JUnit 한테 알려줘야죠. 스프링 러너 나 스프링과 관련된 것으로 테스트를 할 것이다 라고 알려줘야 된다.
// @Rollback(value = false) : 롤백 안하고 그냥 커밋 해버리는 것이다.
// 테스트를 실행하면 데이터가 소멸되는 것을 false 값으로 돌릴 때 데이터가 남는 것을 확인 할 수 있다.
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepositoryJpa memberRepository;

    @Test
    @Transactional
//    @Rollback(value = false)
    public void testMember() throws Exception {
        // given
        MemberJpa member = new MemberJpa();
        member.setUsername("memberA");

        // when
        Long saveId = memberRepository.save(member);
        MemberJpa findMember = memberRepository.find(saveId);

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
        System.out.println("findMember == member = " + (findMember == member)); //JPA 엔티티 동일성 보장

        // 처음 실행을 하면 에러가 날 것이다. - (트랜잭션이 없어서 그렇다.)
        // 그 이유는 엔티티 매니저를 통한 모든 데이터 변경은 항상 트랜잭션 안에서 이루어져야 하기 때문이다.
        // 그래서 @Transactional 을 걸어준다.
    }
}
