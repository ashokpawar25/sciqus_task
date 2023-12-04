package com.sciqus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.sciqus.dao.StudentRepositery;
import com.sciqus.entity.Student;

@Controller
public class HomeController {

	@Autowired
	StudentRepositery studentRepositery;

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@GetMapping("/studentList")
	public String displayStudents(Model model) {
		Iterable<Student> students = studentRepositery.findAll();
		model.addAttribute("students", students);

		return "studentList";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/register")
	public String registerStudent(@RequestParam String name, @RequestParam String email, @RequestParam String username,
			@RequestParam String password) {

		if (name.trim().isEmpty() || email.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()) {
			return "Please fill in all fields.";
		}
		
		Student student = new Student(name, email, username, password);
		studentRepositery.save(student);
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model) {
		Student student = studentRepositery.findByUsernameAndPassword(username, password);

		if (student != null) {
			return "redirect:/studentList";
		} else {
			model.addAttribute("error", "Invalid username or password");
			return "login";
		}
	}

	
}
