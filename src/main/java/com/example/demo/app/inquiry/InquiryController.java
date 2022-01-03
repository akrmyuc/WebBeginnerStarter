package com.example.demo.app.inquiry;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Inquiry;
import com.example.demo.service.InquiryService;

/*
 * Add annotations here
 */
@Controller
@RequestMapping("/inquiry")
public class InquiryController {

 	private final InquiryService inquiryService;

	//Add an annotation here
 	public InquiryController(InquiryService inquiryService){
 		this.inquiryService = inquiryService;
 	}

	@GetMapping
	public String index(Model model) {

		//hands-on

		return "inquiry/index";
	}

	@GetMapping("/form")
	public String form(InquiryForm inquiryForm, Model model,
			@ModelAttribute("complete")String complete) {
		// addAttribute：htmlに送る
		model.addAttribute("title", "お問い合わせフォーム");

		return "inquiry/form";
	}

	@PostMapping("/form")
	public String formGoBack(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "お問い合わせフォーム");
		return "inquiry/form";
	}


	@PostMapping("/confirm")
	public String confirm(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model) {

		if(result.hasErrors()) {
			// エラーがあればtrue。formのページに戻す
			model.addAttribute("title", "お問い合わせフォーム");
			return "inquiry/form";
		}
		// エラーがなかった場合
		model.addAttribute("title", "確認ページ");

		return "inquiry/confirm";
	}

	@PostMapping("/complete")
	public String complete(@Validated InquiryForm inquiry,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {

		if(result.hasErrors()) {
			model.addAttribute("title", "お問い合わせフォーム");
			return "Inquiry/form"; // htmlファイル名
		}

		Inquiry inquiry = new Inquiry();
		Inquiry.setName(InquiryForm.getName());
		inquiry.setEmail(InquiryForm.getEmail());
		inquiry.setContents(InquiryForm.getContents());
		inquiry.setCreated(LocalDateTime.now());

		inquiryService.save(inquiry);

		redirectAttributes.addFlashAttribute("complete", "送信が完了しました");

		return "redirect:/inquiry/form"; // URL
	}

}
