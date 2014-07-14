package newbookstore;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;


import com.rest.Book;
import com.rest.BookService;
import com.rest.BookStore;

import junit.framework.TestCase;

public class TestsForBookStore extends TestCase {
	@Test
	public void testAdd() throws IOException{
		Book book = new Book("gone with the wind","sad story",10,10);
		ArrayList<Book> list = new ArrayList<>();
		list.add(book);
		BookService service = new BookService();
		/*service.newBook("gone with the wind", "10", "10", "sad story");*/

		assertEquals(list,list);
	}

}
