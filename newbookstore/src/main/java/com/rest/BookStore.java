package com.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public enum BookStore {
	
	instance;
	private List<Book> book_list = new ArrayList<Book>();
	 
	public List<Book> getBookList(){
		return book_list;
	}
	static public JsonObject getAllBooks(){
		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonArrayBuilder array = Json.createArrayBuilder();
		for(Book b:BookStore.instance.getBookList()){
			array.add(Json.createObjectBuilder()
					.add("name", b.getName())
					.add("price",b.getPrice())
					.add("stock", b.getStock())
					.add("desc", b.getDescription()));
		}
		builder.add("books", array);
		JsonObject ret = builder.build();
		return ret;
	}
	static public JsonObject getBookByName(String name){
		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonArrayBuilder array = Json.createArrayBuilder();
		for(Book b:BookStore.instance.book_list){
			if(b.getName().equals(name.trim()))
				array.add(Json.createObjectBuilder()
						.add("name",b.getName())
						.add("price",b.getPrice())
						.add("stock",b.getStock())
						.add("desc", b.getDescription())
						);
		}
		JsonObject ret = builder.add("books", array).build();
		return ret;
	}
	static public void deleteBookByName(String name){
		Iterator<Book> it = BookStore.instance.book_list.iterator();
		while(it.hasNext()){
			if(it.next().getName().equals(name.trim()))
				it.remove();
		}
	}
	static public void addBooks(JsonObject books){
		
	}
}
