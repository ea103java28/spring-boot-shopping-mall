package com.tony.thymeleaf;

import com.tony.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ThymeleafController {

    @GetMapping("/home")
    public String home(Model model){

        User user = new User();
        user.setUserId(1);
        user.setEmail("xxxx@test.com");
        model.addAttribute("myUser", user);

        return "index";
    }


    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }



    @PostMapping("/login")
    public String login(String userName,
                        String userPassword){
        System.out.println("userName 為:" + userName);
        System.out.println("userPassword 為:" + userPassword);

        return "login";
    }
}
