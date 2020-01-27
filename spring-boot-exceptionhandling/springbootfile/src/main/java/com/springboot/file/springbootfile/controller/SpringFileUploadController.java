package com.springboot.file.springbootfile.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.springboot.file.springbootfile.model.User;
import com.springboot.file.springbootfile.model.UserFiles;
import com.springboot.file.springbootfile.service.UserService;

@Controller
public class SpringFileUploadController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/")
	public String users(Model model) {

		List<User> users = userService.getAllUsers();

		model.addAttribute("users", users);
		model.addAttribute("user", new User());
		model.addAttribute("userfiles", new ArrayList<UserFiles>());
		model.addAttribute("isAdd", true);

		return "view/user";
	}

	@PostMapping(value = "/save")
	public String save(@ModelAttribute User user, RedirectAttributes redirectAttributes, Model model) {

		System.out.println("********************");
		System.out.println(user);
		System.out.println("********************");
		User dbUser = userService.save(user);

		if (dbUser != null) {
			redirectAttributes.addFlashAttribute("successmessage", "User is saved successfully");
			return "redirect:/";

		} else {
			model.addAttribute("errormessage", "User is not save,please try  again");

			model.addAttribute("user", user);

			return "view/user";

		}
	}

	@GetMapping(value = "/edituser/{userId}")
	public String edituser(@PathVariable Long userId, Model model) {

		User user = userService.findById(userId);

		List<UserFiles> userFiles = userService.findFilesByUserId(userId);

		List<User> users = userService.getAllUsers();

		model.addAttribute("users", users);
		model.addAttribute("user", user);
		model.addAttribute("userfiles", userFiles);
		model.addAttribute("isAdd", false);

		return "view/user";

	}

}
