package com.rest;

public class Book {
	private String name;
	private String description;
	private double price;
	private Integer stock;
	public Book(){
		
	}
	public Book(String name,String description, double price, Integer stock){
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
}
