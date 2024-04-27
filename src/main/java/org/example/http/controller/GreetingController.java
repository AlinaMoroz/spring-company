package org.example.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.database.entity.Role;
import org.example.dto.UserReadDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api")
@SessionAttributes({"user"})//так мы можем пометить атрибут как сессионный
public class GreetingController {

    @ModelAttribute("roles")
    public List<Role> roles() {
        return Arrays.asList(Role.values());
    }

    @GetMapping("/hello")
    public String hello(Model model, HttpServletRequest request,
                        @ModelAttribute("userReadDto") UserReadDto userReadDto
    ) {
        //request.getSession().setAttribute(); sessionScope

//        model.addAttribute("user",new UserReadDto(1L, "Ivan"));//requestScope
        model.addAttribute("user", userReadDto);

        return "greeting/hello";
    }

    @GetMapping("/bye")
    public ModelAndView bye(ModelAndView modelAndView, @SessionAttribute("user") UserReadDto user) {
        // получить атрибут из сессии
        modelAndView.setViewName("greeting/bye");

        return modelAndView;
    }

    @GetMapping("/hello/{id}")
    public ModelAndView hello2(ModelAndView modelAndView, HttpServletRequest request,
                               @RequestParam("age") Integer age,
                               @RequestHeader("accept") String accept,
                               @CookieValue("JSESSIONID") String jsessionId,
                               @PathVariable("id") Integer id) {

        modelAndView.setViewName("greeting/hello");

        return modelAndView;
    }
}
