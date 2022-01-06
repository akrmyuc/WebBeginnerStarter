package com.example.demo.app.inquiry;

import java.time.LocalDateTime;
import java.util.List;

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

		List<Inquiry> list = inquiryService.getAll();

		model.addAttribute("inquiryList", list);
		model.addAttribute("title", "お問い合わせ一覧");

//		Inquiry inquiry = new Inquiry();
//		inquiry.setId(4);
//		inquiry.setName("Jamie");
//		inquiry.setEmail("sample4@example.com");
//		inquiry.setContents("Hello");
//
//		inquiryService.update(inquiry);

//		try {
//			inquiryService.update(inquiry);
//		} catch(InquiryNotFoundException e) {
//			model.addAttribute("message", e);
//			return "error/CustomPage";
//		}

		return "inquiry/index_boot";
	}

	@GetMapping("/form")
	public String form(InquiryForm inquiryForm, Model model,
			@ModelAttribute("complete") String complete) {
		// addAttribute：htmlに送る
		model.addAttribute("title", "お問い合わせフォーム");
		return "inquiry/form_boot";
	}

	@PostMapping("/form")
	public String formGoBack(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "お問い合わせフォーム");
		return "inquiry/form_boot";
	}


	@PostMapping("/confirm")
	public String confirm(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model) {

		if(result.hasErrors()) {
			// エラーがあればtrue。formのページに戻す
			model.addAttribute("title", "お問い合わせフォーム");
			return "inquiry/form_boot";
		}
		// エラーがなかった場合
		model.addAttribute("title", "確認ページ");
		return "inquiry/confirm_boot";
	}

	@PostMapping("/complete")
	public String complete(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {

		if(result.hasErrors()) {
			model.addAttribute("title", "お問い合わせフォーム");
			return "inquiry/form_boot"; // htmlファイル名
		}

		Inquiry inquiry = new Inquiry();
		inquiry.setName(inquiryForm.getName());
		inquiry.setEmail(inquiryForm.getEmail());
		inquiry.setContents(inquiryForm.getContents());
		inquiry.setCreated(LocalDateTime.now());

		inquiryService.save(inquiry);

		redirectAttributes.addFlashAttribute("complete", "送信が完了しました");

		return "redirect:/inquiry/form"; // URLなので、_bootにしない！
	}

////	例外を補足するメソッド
//	@ExceptionHandler(InquiryNotFoundException.class)
//	public String handleException(InquiryNotFoundException e, Model model) {
//		model.addAttribute("message", e);
//		return "error/CustomPage";
//	}
}
