package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.imp.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    public String getUserAll(Model model) {
        model.addAttribute("admin", userService.index());
        return "admin/admin";
    }

    @GetMapping("/addNewUser")
    public String addNewUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "admin/user-info";
    }

    @PostMapping("/saveUser")
    public ModelAndView saveUser(@ModelAttribute("user_info") User user) {
        userService.save(user);
        return new ModelAndView("redirect:/admin");
    }


    @PatchMapping("/updateInfo")
    public String updateUser(@RequestParam(value = "userId") Long id, Model model) {
        User user = userService.show(id);
        model.addAttribute("roles",roleRepository.findAll());
        model.addAttribute("user", user);
        return "admin/user-edit";
    }

    @PatchMapping("/updateUser")
    public String update(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam(value = "userId") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
