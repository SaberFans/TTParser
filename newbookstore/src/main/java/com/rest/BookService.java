package com.rest;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/*
 * Book Service Controller
 */
@Path("/books")
public class BookService {
	private int count =0 ;
	private PostgresManager pmgr = new PostgresManager();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBook() throws ClassNotFoundException, SQLException {
		JsonObject res = pmgr.getJsonRes();
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
	public void addBooks(List<Book> list) throws SQLException {
		pmgr.insertInto(list);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void delete(Book book) {
		
	}
}
