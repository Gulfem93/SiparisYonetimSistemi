    package com.siparisYonetim.SiparisYonetimSistemi.controller;

    import com.siparisYonetim.SiparisYonetimSistemi.model.UserModel;
    import com.siparisYonetim.SiparisYonetimSistemi.repository.UserRepository;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;

    import java.security.Principal;

    @Controller
    @RequestMapping("/main")
    public class MainController {
        private final UserRepository userRepository;

        public MainController(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @GetMapping
        public String mainPage(Model model, Principal principal) {
            model.addAttribute("isAuthenticatedMain", principal != null);

            if (principal != null) {
                String username = principal.getName();
                UserModel userModel = userRepository.findByUsername(username).orElseThrow();
                model.addAttribute("name", userModel.getName());
                model.addAttribute("signup_login", new UserModel());
            }
            return "/logout/main";
        }
    }
