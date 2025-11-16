package com.bsuedu.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class BorrowRecordController {

	@Autowired
	private BorrowRecordService borrowRecordService;

	@Autowired
	private BookRepository bookRepository;

	@GetMapping("/book/{id}/history")
	public String viewHistory(@PathVariable Long id, Model model) {
		model.addAttribute("book", bookRepository.findById(id).orElseThrow());
		model.addAttribute("records", borrowRecordService.getRecordsForBook(id));
		return "history";
	}

	@PostMapping("/book/{id}/borrow")
	public String borrow(@PathVariable Long id, @RequestParam String borrower) {
		borrowRecordService.borrowBook(id, borrower);
		return "redirect:/book/" + id + "/history";
	}

	@PostMapping("/record/{id}/return")
	public String returnBook(@PathVariable Long id) {
		borrowRecordService.returnBook(id);
		return "redirect:/";
	}
}