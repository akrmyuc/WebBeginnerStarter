package com.example.demo.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Add annotations here
 */
@Controller
@RequestMapping("/sample")
public class SampleController {

// 	private final JdbcTemplate jdbcTemplate;

// 	//Add an annotation here
// 	public SampleController(JdbcTemplate jdbcTemplate) {
// 		this.jdbcTemplate = jdbcTemplate;
// 	}

	@GetMapping // GET:データをURLに含める
	public String test(Model model) {

		model.addAttribute("title", "Inquiry Form");

		return "test";
	}

}
