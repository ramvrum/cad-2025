package com.bsuedu.library;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
	private final BookRepository bookRepository;

	@Autowired
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public void addBook(Book book) {
		bookRepository.addBook(book);
	}

	public List<Book> getAllBooks() {
		return bookRepository.getAllBooks();
	}

	public void deleteBook(Long id) {
		bookRepository.deleteBook(id);
	}

	public void updateAvailability(Long id, boolean available) {
		bookRepository.updateAvailability(id, available);
	}

	public void setReturnDate(Long id, LocalDate returnDate) {
		bookRepository.setReturnDate(id, returnDate);
	}

}
