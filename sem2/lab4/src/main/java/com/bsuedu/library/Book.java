package com.bsuedu.library;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String author;
	private String genre;

	private boolean available;

	private LocalDate returnDate;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	private List<BorrowRecord> borrowRecords = new ArrayList<>();

	public Book() {
		this.available = true;
	}

	public Book(String title, String author, String genre) {
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.available = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public List<BorrowRecord> getBorrowRecords() {
		return borrowRecords;
	}

	public void setBorrowRecords(List<BorrowRecord> borrowRecords) {
		this.borrowRecords = borrowRecords;
	}

	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", title='" + title + '\'' +
				", author='" + author + '\'' +
				", genre='" + genre + '\'' +
				", available=" + available +
				", returnDate=" + returnDate +
				'}';
	}
}