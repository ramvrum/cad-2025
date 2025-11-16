package com.bsuedu.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowRecordService {

	@Autowired
	private BorrowRecordRepository borrowRecordRepository;

	@Autowired
	private BookRepository bookRepository;

	public void borrowBook(Long bookId, String borrower) {
		Book book = bookRepository.findById(bookId).orElseThrow();

		if (!book.isAvailable()) {
			throw new IllegalStateException("Книга уже выдана");
		}

		BorrowRecord record = new BorrowRecord(borrower, LocalDate.now(), book);
		borrowRecordRepository.save(record);

		book.setAvailable(false);
		book.setReturnDate(LocalDate.now().plusDays(14));
		bookRepository.save(book);
	}

	public void returnBook(Long recordId) {
		BorrowRecord record = borrowRecordRepository.findById(recordId).orElseThrow();

		record.setReturned(true);
		record.setReturnDate(LocalDate.now());
		borrowRecordRepository.save(record);

		Book book = record.getBook();
		book.setAvailable(true);
		book.setReturnDate(null);
		bookRepository.save(book);
	}

	public List<BorrowRecord> getRecordsForBook(Long id) {
		return borrowRecordRepository.findByBookId(id);
	}
}