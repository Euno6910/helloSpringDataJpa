package kr.ac.hansung.cse.hellospringdatajpa.controller;

import kr.ac.hansung.cse.hellospringdatajpa.service.UserService;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    // 회원가입 폼
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String password, Model model) {
        try {
            userService.registerUser(email, password);
            model.addAttribute("success", "회원가입이 완료되었습니다. 로그인 해주세요.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    // 로그인 폼
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin_user_list";
    }
} 