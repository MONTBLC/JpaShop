package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final MemberService memberService;

    @Autowired
    private final ItemService itemService;

    @Autowired
    private final OrderService orderService;

    @GetMapping("/order")
    public String createForm(Model model) {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    // OrderController 에서 memberId, itemId 를 찾아서 넘겨도 되긴 하지만,
    // 컨트롤러에서 찾게 되면 로직이 지저분하는 것도 있겠지만,
    // 서비스 계층에서 엔티티에 대해서 더 많이 의존하고 영속 상태로 흘러가기 때문에 훨씬 깔끔하게 해결 할 수 있다.
    // command 성, 주문 등은 외부에서 컨트롤러 레벨에서는 그냥 식별자만 넘기고, 실제 핵심 비즈니스는 서비스 레벨에서 하는것이 바람직하다.
    // 엔티티의 값들도 트랜잭션 안에서 엔티티를 조회를 해야 영속 상태로 진행이 되기 때문이다.(member, item 의 상태도 바꿀 수 있다.)

    // 참고로 @RequestParam 은 FormSubmit 방식이다.
    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        // 단순한 조회 같은 경우에는 이렇게 단순 위임이면 그냥 호출을 하는 편이다.

        model.addAttribute("orders", orders);
//        model.addAttribute("orderSearch", orderSearch);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
