package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.imp.RoleService;
import ru.kata.spring.boot_security.demo.service.imp.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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

    @GetMapping("/saveUser")
    public ModelAndView saveUser(@ModelAttribute("user_info") User user) {
        userService.save(user);
        return new ModelAndView("redirect:/admin");
    }


    @GetMapping("/updateInfo/{id}/edit")
    public ModelAndView updateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("roles",roleService.findRoles());
        model.addAttribute("user", userService.show(id));
        return new ModelAndView("admin/user-edit");
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
