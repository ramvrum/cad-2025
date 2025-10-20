package com.bsuedu.library;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class BookController {
	private final BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		return "index";
	}

	@PreAuthorize("hasRole('LIBRARIAN')")
	@PostMapping("/addBook")
	public String addBook(@ModelAttribute Book book) {
		bookService.addBook(book);
		return "redirect:/";
	}

	@PreAuthorize("hasRole('LIBRARIAN')")
	@GetMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return "redirect:/";
	}

	@PreAuthorize("hasRole('LIBRARIAN')")
	@PostMapping("/updateBook/{id}")
	public String updateAvailability(@PathVariable Long id, @RequestParam boolean available) {
		if (!available) {
			bookService.setReturnDate(id, LocalDate.now().plusDays(14));
		} else {
			bookService.setReturnDate(id, null);
		}
		bookService.updateAvailability(id, available);
		return "redirect:/";
	}
}
