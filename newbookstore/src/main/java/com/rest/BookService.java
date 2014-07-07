package com.rest;

import java.io.IOException;

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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("status")
	public Response getStatus() {
		return Response.ok(
				"{\"status\":\"Service Book Service is running...\"}").build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
	public Response getAllBook() {
		//JsonObject model = Json.
		String output="{\"booklist\":";
		for(Book b:BookStore.instance.getBookList()){
			
		}
		output+="";
		return Response.ok(output).build();
	}
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newBook(@FormParam("name") String name,@FormParam("price") String price,@FormParam("stock") String stock,@FormParam("desc") String desc, @Context HttpServletResponse servletResponse) throws IOException {
		Book book = new Book();
		book.setName(name);
		book.setPrice(Double.parseDouble(price));
		book.setStock(Integer.parseInt(stock));
		book.setDescription(desc);
		
		BookStore.instance.getBookList().add(book);
		servletResponse.sendRedirect("../create.html");
	 }
	
}
