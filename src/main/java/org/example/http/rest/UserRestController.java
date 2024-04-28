package org.example.http.rest;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.example.database.entity.Role;
import org.example.dto.PageResponse;
import org.example.dto.UserCreateEditDto;
import org.example.dto.UserFilter;
import org.example.dto.UserReadDto;
import org.example.service.CompanyService;
import org.example.service.UserService;
import org.example.validation.group.CreateAction;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;
    private final CompanyService companyService;


    @GetMapping()
    public List<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
//        model.addAttribute("users", userService.findAll());

//        model.addAttribute("users", userService.findAll(filter));
        var users = userService.findAll(filter);
        return users;
    }

    @GetMapping("/{id}")
    public UserReadDto findById(@PathVariable("id") Long id) {
        return userService.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    //@RequestBody указывает, что данные должны прихожить из вне
    public UserReadDto create(@Validated({Default.class, CreateAction.class}) @RequestBody UserCreateEditDto user) {

        return userService.create(user);
    }


    @PutMapping("/{id}")
    public UserReadDto update(@PathVariable("id") Long id, @Validated @RequestBody UserCreateEditDto user) {
        return userService.update(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PostMapping("/{id}/delete")
    public void delete(@PathVariable("id") Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}
