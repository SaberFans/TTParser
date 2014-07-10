package com.rest;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

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
@Path("/books")
public class BookService {
	private int count =0 ;
	/*@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatus() {
		return Response.ok(
				"{\"status\":\"Service Book Service is running "+BookStore.instance.getBookList().size()+ "...\"}").build();
	}*/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBook() {
		JsonObject res = BookStore.getAllBooks();
		return Response.ok(res).build();
	}
	@GET @Path("{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBookByName(@PathParam("name") String name){
		JsonObject res = BookStore.getBookByName(name);
		return Response.ok(res).build();
	}
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public void addBooks(List<Book> list) {
		 BookStore.instance.getBookList().addAll(list);
	}
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void delete(Book book) {
		Iterator<Book> i = BookStore.instance.getBookList().iterator();
		while(i.hasNext()){
			if(book!=null && i.next().equals(book))
				i.remove();
		}
	}
}
