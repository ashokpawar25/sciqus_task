package com.sciqus.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@RequestMapping("/add")
	@ResponseBody
	public void addStudent() {
		String name = "ashok";
		String email = "abc@gmail.com";
		String username = "sampleuser";
		String password = "ashok";

		Student student = new Student(name, email, username, password);
		studentRepositery.save(student);
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
	@ResponseBody
	public String registerStudent(@RequestParam String name, @RequestParam String email, @RequestParam String username,
			@RequestParam String password) {

		// Basic validation
		if (name.trim().isEmpty() || email.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()) {
			return "Please fill in all fields.";
		}

		// Additional validation (e.g., email format, password strength)
		// Add your validation logic here

		// Create a new student object
		Student student = new Student(name, email, username, password);

		// Save the student to the repository
		studentRepositery.save(student);

		// You can return a success message or redirect to another page
		return "Registration successful!";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model) {
		// Your authentication logic here
		Student student = studentRepositery.findByUsernameAndPassword(username, password);

		if (student != null) {
			return "redirect:/studentList"; // Redirect to the student list page on successful login
		} else {
			model.addAttribute("error", "Invalid username or password");
			return "login";
		}
	}

	
}
