package com.rest;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.rest.Book;
import com.rest.BookStore;

/*
 * Book Service Controller
 */
@Path("/bookservice")
public class BookService {
	private int count =0 ;
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("status")
	public Response getStatus() {
		
		return Response.ok(
				"{\"status\":\"Service Book Service is running "+BookStore.instance.getBookList().size()+ "...\"}").build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
	public Response getAllBook() {
		//JsonObject model = Json.
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
		return Response.ok(ret).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("search")
	public Response search(@QueryParam("name") String name) {
		Book found = null;
		for(Book b: BookStore.instance.getBookList()){
			if(b.getName().equals(name.trim()))
				found = b;
		}
		JsonObjectBuilder builder = Json.createObjectBuilder();
		if(found!=null){
			builder.add("name", found.getName()).add("price", found.getPrice()).add("stock", found.getStock()).add("desc", found.getDescription());
		}

		return Response.status(200).entity(builder.build()).build(); 
	}
	@POST
	@Path("add")
	@Consumes("application/x-www-form-urlencoded")
	public void newBook(@FormParam("name") String name,@FormParam("price") String price,@FormParam("stock") String stock,@FormParam("desc") String desc, @Context HttpServletResponse servletResponse) throws IOException {
		Book book = new Book();
		book.setName(name);  
		book.setPrice(Double.parseDouble(price));
		book.setStock(Integer.parseInt(stock));
		book.setDescription(desc);
		BookStore.instance.getBookList().add(book);
	 }
}
