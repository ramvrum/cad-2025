package com.bsuedu.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/")
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private BorrowRecordService borrowRecordService;

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

	@PreAuthorize("hasRole('USER') or hasRole('LIBRARIAN')")
	@GetMapping("/api/books")
	@ResponseBody
	public List<Book> apiGetBooks() {
		return bookService.getAllBooks();
	}

	@PreAuthorize("hasRole('USER') or hasRole('LIBRARIAN')")
	@GetMapping("/api/books/{id}/history")
	@ResponseBody
	public List<BorrowRecord> apiGetHistory(@PathVariable Long id) {
		return borrowRecordService.getRecordsForBook(id);
	}

	@PreAuthorize("hasRole('LIBRARIAN')")
	@DeleteMapping("/api/books/{id}")
	@ResponseBody
	public void apiDeleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
	}

	@PreAuthorize("hasRole('LIBRARIAN')")
	@PostMapping("/api/books")
	@ResponseBody
	public void apiAddBook(@RequestParam String title,
			@RequestParam String author,
			@RequestParam String genre) {

		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		book.setGenre(genre);
		book.setAvailable(true);

		bookService.saveBook(book);
	}
}