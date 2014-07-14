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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBook() throws ClassNotFoundException, SQLException {
		try{
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver found for postgres.");
		}
		catch(ClassNotFoundException e){
			System.out.println("JDBC driver not found!");
			return null;
		}
		Connection con = null;
		JsonObject res = null;
		try{
			con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
			System.out.println("Connection to postgres is built.");
			Statement stm = con.createStatement();
			String query = "select * from book";
			ResultSet rs = stm.executeQuery(query);
			while(rs.next()){
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				int stock = rs.getInt("stock");
				String desc = rs.getString("description");
				BookStore.instance.getBookList().add(new Book(name,desc,price,stock));
			}
			res = BookStore.getAllBooks();
		}
		catch(Exception e){
			System.out.println("No connection to database");
		}
		finally{
			con.close();
			
			System.out.println("Connection is closed");
		}
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
		try{
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver found for postgres.");
		}
		catch(ClassNotFoundException e){
			System.out.println("JDBC driver not found!");
		}
		Connection con = null;
		JsonObject res = null;
		try{
			con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
			System.out.println("Connection to postgres is built.");
			Statement stm = con.createStatement();
			DatabaseMetaData dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "book", null);
			if(tables.next()){
				String query = "INSERT INTO book (name,price,stock,description)"
						+ "		VALUES(?, ?, ?, ?) ";
				PreparedStatement ps = con.prepareStatement(query);
				while(Book b : list){
					ps.setString(1, b.getName());
					ps.setDouble(2, b.getPrice());
					ps.setInt(3, b.getStock());
					ps.setString(4, b.getDescription());
					ps.addBatch(query);
				}
				ps.executeBatch();
			}
			else{
				////////////////////
			}
			String query = "select * from book";
			ResultSet rs = stm.executeQuery(query);
		}
		catch(SQLException e){
			System.out.println("No connection to database.");
		}
		finally{
			con.close();
			
		}
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
