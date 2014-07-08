package com.rest;

import java.io.IOException;

import javax.json.Json;
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
		JsonObjectBuilder builder = Json.createObjectBuilder();
		BookStore.instance.getBookList().add(new Book("monk","sad",10,10));
		for(Book b:BookStore.instance.getBookList()){
			builder.add("books", Json.createArrayBuilder().
					add(Json.createObjectBuilder()
						.add("name", b.getName())
						.add("price", b.getPrice())
						.add("desc", b.getDescription())
						.add("stock", b.getStock())));
		}
		JsonObject ret = builder.build();
		return Response.ok(ret).build();
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public void newBook(@FormParam("name") String name,@FormParam("price") String price,@FormParam("stock") String stock,@FormParam("desc") String desc, @Context HttpServletResponse servletResponse) throws IOException {
		Book book = new Book();
		/*book.setName(name);  
		book.setPrice(Double.parseDouble(price));
		book.setStock(Integer.parseInt(stock));
		book.setDescription(desc);*/
		book.setName("hehe");
		book.setPrice(11);
		book.setStock(11);
		book.setDescription(name+price+stock+desc);
		BookStore.instance.getBookList().add(book);
	 }
}
