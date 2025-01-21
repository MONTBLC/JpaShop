package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    // API 를 만들 때는 이유를 불문하고 절대 엔티티를 웹으로 반환하면 안된다!!!!
    // 그래서 엔티티는 절대 API 로 외부로 노출하면 안된다.

    @Autowired
    private final ItemService itemService;

    /**
     * 상품 등록 폼
     */
    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("bookForm", new BookForm());
        return "items/createItemForm";
    }

    /**
     * 상품 등록 뷰
     */
    @PostMapping("/items/new")
    public String create(@Valid BookForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "items/createItemForm";
        }

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    /**
     * 상품 목록 뷰
     */
    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    /**
     * 상품 수정 폼
     */
    // 변경될 수 있는 값이니 @PathVariable("itemId")로 매핑한다. {itemId}
    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm bookForm = new BookForm();
        bookForm.setId(item.getId());
        bookForm.setName(item.getName());
        bookForm.setPrice(item.getPrice());
        bookForm.setStockQuantity(item.getStockQuantity());
        bookForm.setAuthor(item.getAuthor());
        bookForm.setIsbn(item.getIsbn());

        model.addAttribute("bookForm", bookForm);
        return "items/updateItemForm";
    }

    /**
     상품 수정, 권장 코드
     */
    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId, BookForm bookForm) {

        // 여기서의 Book 객체는 준영속성 엔티티이다!!
        // 기본적으로, 직접 new 로 해서 만든 거는 JPA 가 관리를 하지 않는다.
//        Book book = new Book();
//        book.setId(bookForm.getId());
//        book.setName(bookForm.getName());
//        book.setPrice(bookForm.getPrice());
//        book.setStockQuantity(bookForm.getStockQuantity());
//        book.setAuthor(bookForm.getAuthor());
//        book.setIsbn(bookForm.getIsbn());
//        itemService.saveItem(book);

        // 유지보수상 깔끔하다.
        // 컨트롤러에서 어설프게 엔티티를 생성하지 말자.
        itemService.updateItem(itemId, bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity());

        return "redirect:/items";
    }
}
