package com.example.demo.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
	// jdbcでDBを操作するためのクラスを初期化
	private final JdbcTemplate jdbcTemplate;

//	Add an annotation here
	@Autowired
 	public SampleController(JdbcTemplate jdbcTemplate) {
 		this.jdbcTemplate = jdbcTemplate;
 	}

	// GET:データをURLに含める
	@GetMapping("/test")
	public String test(Model model) {
		String sql = "select id, name, email "
				+ "from inquiry where id = 1";

		Map<String, Object> map = jdbcTemplate.queryForMap(sql);

		// Mapの値をhtmlに渡す
		model.addAttribute("name", map.get("name"));
		model.addAttribute("email", map.get("email"));

		model.addAttribute("title", "Inquiry Form");

		return "test";
	}

}
