package gift.controller;

import gift.dto.CreateProductRequestDto;
import gift.dto.ProductPageDto;
import gift.exception.CustomException;
import gift.exception.ErrorCode;
import gift.service.MemberService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/boards")
public class AdminProductController {

    private final ProductService productService;

    private final MemberService memberService;

    private final String BOARD_PAGE = "/admin/boards";

    public AdminProductController(ProductService productService,
            MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping
    public String showAdminPage(Model model, @PageableDefault(size = 5, sort = "name", direction = Direction.ASC) Pageable pageable) {
        ProductPageDto products = productService.findAllProducts(pageable);
        model.addAttribute("products", products);
        return "dashboard";
    }

    @GetMapping("/add")
    public String showCreatePage(Model model) {
        return "createForm";
    }

    @GetMapping("/update/{id}")
    public String showUpdatePage(
            @PathVariable Long id,
            Model model) {
        model.addAttribute("id", id);
        return "updateForm";
    }

    @PostMapping
    public String createProduct(
            @Valid @ModelAttribute CreateProductRequestDto requestDto) {
        if (requestDto.name().contains("카카오")) {
            throw new CustomException(ErrorCode.NamingForbidden);
        }
        productService.createProduct(requestDto);
        return "redirect:" + BOARD_PAGE;
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Long id,
            @Valid @ModelAttribute CreateProductRequestDto requestDto) {
        if (requestDto.name().contains("카카오")) {
            throw new CustomException(ErrorCode.NamingForbidden);
        }
        productService.updateProductById(id, requestDto);
        return "redirect:" + BOARD_PAGE;
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(
            @PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:" + BOARD_PAGE;
    }

}
