package com.bsuedu.library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
	private List<Book> bookList = new ArrayList<>();

	public void addBook(Book book) {
		bookList.add(book);
	}

	public List<Book> getAllBooks() {
		return bookList;
	}

	public void deleteBook(Long id) {
		bookList.removeIf(book -> book.getId().equals(id));
	}

	public void updateAvailability(Long id, boolean available) {
		for (Book book : bookList) {
			if (book.getId().equals(id)) {
				book.setAvailable(available);
				break;
			}
		}
	}

	public void setReturnDate(Long id, LocalDate returnDate) {
		for (Book book : bookList) {
			if (book.getId().equals(id)) {
				book.setReturnDate(returnDate);
				break;
			}
		}
	}
}
