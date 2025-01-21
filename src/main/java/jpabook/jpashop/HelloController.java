package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
    /* Model */
    // SpringUI 에 있는 Model 이란 model 변수에다가 어떤 데이터를 실어서 view 에 넘길 수 있다.
    // 즉, 컨트롤러에서 데이터를 view 로 넘길수 있다.
    // name 이 data 라는 키의 값을 hello!! 로 넘길 것이다.
        model.addAttribute("data", "hello!!");
        return "hello";
        // return "hello"; 화면 이름이 hello.html 이다.
    }
}
