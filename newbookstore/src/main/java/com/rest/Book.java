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
	
	@Override
	public boolean equals(Object o){
		if(o == this) return true;
		if(!(o instanceof Book) || o==null) return false;
		Book b = (Book)o;
		if(b.getName().equals(name)&&b.getPrice()==price&&b.getStock().equals(stock)&&b.getDescription().equals(description))
			return true;
		
		return false;
		
	}
}
