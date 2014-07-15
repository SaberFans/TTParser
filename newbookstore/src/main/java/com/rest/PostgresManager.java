package com.rest;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.json.JsonObject;

public class PostgresManager {
	private String url="jdbc:postgresql://localhost:5432/postgres";
	private String usr="postgres";
	private String pwd="postgres";
	
	private String table_name="book";
	private Connection con;
	private Statement stm;
	private PreparedStatement pstm;
	
	private JsonObject res;
	
//	public PostgresManager(String url, String usr, String pwd){
//		
//	}
	public PostgresManager(){
		try{
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver found for postgres.");
		}
		catch(ClassNotFoundException e){
			System.out.println("JDBC driver not found!");
			return;
		}
		con = null;
		stm = null;
		try{
			con = DriverManager.getConnection(url,usr,pwd);
			System.out.println("Connection to db is built.");
			DatabaseMetaData dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "book", null);
			if(!tables.next()){
				String sql =  "CREATE TABLE book"+
							  "(book_id serial NOT NULL ,"
							  + "name VARCHAR(255),"
							  + "price DOUBLE PRECISION,"
							  + "stock INTEGER,"
							  + "description VARCHAR(255)"
							  + "PRIMARY KEY (id))";
				stm = con.createStatement();
				stm.execute(sql);
			}
			String query = "select * from book";
			Statement stm = con.createStatement();
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
			e.printStackTrace();
		}
		finally{
			try{
				con.close();
				if(stm!=null)
					stm.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			
			System.out.println("Connection is closed");
		}
	}
	public JsonObject getJsonRes(){
		return res;
	}
	public void insertInto(List<Book> list){
		try{
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver found for postgres.");
		}
		catch(ClassNotFoundException e){
			System.out.println("JDBC driver not found!");
		}
		con = null;
		stm = null;
		try{
			con=DriverManager.getConnection(url,usr,pwd);
			System.out.println("Connection to postgres is built.");
			
			DatabaseMetaData dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "book", null);
			if(!tables.next()){
				String sql =  "CREATE TABLE book"+
							  "(book_id serial PRIMARY KEY ,"
							  + "name VARCHAR(255),"
							  + "price DOUBLE PRECISION,"
							  + "stock INTEGER,"
							  + "description VARCHAR(255))";
				stm = con.createStatement();
				stm.execute(sql);
			}
			
			String query = "INSERT INTO book (name,price,stock,description)"
					+ "		VALUES(?, ?, ?, ?) ";
			
			PreparedStatement ps = con.prepareStatement(query);
			for(Book b : list){
				ps.setString(1, b.getName());
				ps.setDouble(2, b.getPrice());
				ps.setInt(3, b.getStock());
				ps.setString(4, b.getDescription());
				ps.addBatch();
			}
			ps.executeBatch();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{
				con.close();
				stm.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	public void delete(){
		
	}
	public void createTable(){
		
	}
	public void dropTable(){
		
	}

}
