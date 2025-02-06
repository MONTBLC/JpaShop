package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 중요! JPA 의 어떤 모든 데이터 변경이나 로직들은 가급적이면 트랜잭션 안에서 실행돼야 한다!!
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

//    @Autowired
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional // 따로 설정한 거는 우선권을 가진다.
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복 회원 검증 로직
     */
    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
//    @Transactional(readOnly = true) // JPA 가 조회하는 곳에서는 조금 더 성능을 최적화합니다.
    // 읽기에는 가급적이면 readOnly = true 를 넣어준다. 쓰기에 넣으면 데이터 변경이 안 된다.
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 한 건만 조회 = 단건 조회
//    @Transactional(readOnly = true) // JPA 가 조회하는 곳에서는 조금 더 성능을 최적화합니다.
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
