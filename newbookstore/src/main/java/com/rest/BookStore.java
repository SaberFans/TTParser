package com.rest;

import java.util.ArrayList;
import java.util.List;

public enum BookStore {
	
	instance;
	private List<Book> book_list = new ArrayList<Book>();
	 
	public List<Book> getBookList(){
		return book_list;
	}
	
}
