package com.convey.service;

import com.convey.core.Book;

public interface BookService {
	
	Book getBookById(Long bookId);
	
	Book save(Book book);
}
