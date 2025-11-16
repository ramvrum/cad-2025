package com.bsuedu.library;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public void addBook(Book book) {
		bookRepository.save(book);
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public void deleteBook(Long id) {
		bookRepository.deleteById(id);
	}

	public void updateAvailability(Long id, boolean available) {
		Book book = bookRepository.findById(id).orElseThrow();
		book.setAvailable(available);
		bookRepository.save(book);
	}

	public void setReturnDate(Long id, LocalDate date) {
		Book book = bookRepository.findById(id).orElseThrow();
		book.setReturnDate(date);
		bookRepository.save(book);
	}

	public void saveBook(Book book) {
		bookRepository.save(book);
	}
}