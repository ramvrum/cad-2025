package com.bsuedu.library;

import java.time.LocalDate;

public class Book {
	private static Long nextId = 1L;
	private Long id;
	private String title;
	private String author;
	private String genre;
	private boolean available;
	private LocalDate returnDate;

	public Book() {
		this.id = generateID();
		this.available = true;
	}

	public Book(String title, String author, String genre) {
		this.id = generateID();
		this.title = title;
		this.author = author;
		this.available = true;
	}

	private synchronized Long generateID() {
		return nextId++;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getGenre() {
		return genre;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
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
