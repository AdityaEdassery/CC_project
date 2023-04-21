package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Controller
public class HelloWorldController {
    @Autowired
	private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
        model.addAttribute("greeting", greeting);
        String sql = "INSERT INTO user (id, name, age) VALUES (?,?,?)";
		int result = jdbcTemplate.update(sql,greeting.getId(),greeting.getName(),greeting.getAge());

		if (result > 0){
			System.out.println("A new row has been inserted");
		}
        return "result";
    }

    @PostMapping("/result")
    public String resultSubmit(@ModelAttribute Greeting greeting, Model model) {
        model.addAttribute("result", greeting);
        String sql = "DELETE FROM user WHERE id=?";
		int result = jdbcTemplate.update(sql,greeting.getId());

		if (result > 0){
			System.out.println("Deleted");
		}
        return "result";
    }    
}