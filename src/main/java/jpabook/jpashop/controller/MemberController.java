package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    // API 를 만들 때는 이유를 불문하고 절대 엔티티를 웹으로 반환하면 안된다!!!!
    // 그래서 엔티티는 절대 API 로 외부로 노출하면 안된다.

    @Autowired
    private final MemberService memberService;

    /**
     * 회원 가입 폼
     */
    // GetMapping 은 화면을 열어보는 것
    @GetMapping("/members/new")
    public String createForm(Model model) {
        // Controller 에서 View 로 넘어갈 때 Model 에 데이터를 실어서 넘어간다.
        // 화면에서 이제 MemberForm 객체를 접근할 수 있게 된다.
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    /**
     * 회원 가입 실제 등록
     */
    // PostMapping 은 데이터를 실제 등록하는 것
    // BindingResult : MemberForm 객체에서 오류가 났을 시, 오류를 BindingResult 에 담겨서 코드가 실행이 된다.
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        // 에러가 있으면
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    /**
     * 회원 목록 뷰
     */
    @GetMapping("/members")
    public String list(Model model) {
        // 실무에서 엔티티는 핵심 비즈니스 로직만 가지고 있고, 화면을 위한 로직은 없어야 한다!!
        // Member 를 이대로 보기보다는 화면이나 API 에 맞는 폼 객체나 DTO 를 사용하자.
        // 그래서 화면이나 API 요구사항을 이것들로 처리하고, 엔티티는 최대한 순수하게 유지하자.
        List<Member> members = memberService.findMembers();
        // 스프링 MVC 가 제공하는 Model 객체에 보관
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
