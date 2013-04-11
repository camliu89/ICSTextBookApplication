import java.util.List;
import models.Book;
import models.Offer;
import models.Request;
import models.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.FakeApplication;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApplicationTest {

  public FakeApplication application;
  
  @Before
  public void startApp () {
    application = fakeApplication(inMemoryDatabase());
    start(application);
  }
  
  @After
  public void stopApp () {
    stop(application);
  }
  
  @Test
  public void testModel () {
    Student student = new Student ("StudentName", "yyy@yyy.edu");
    Book bookForRequest = new Book ("Algorithms", "12345", 1, 14.75);
    Request bookRequest = new Request ("good", 12.00, bookForRequest, student);
    Book bookForOffer = new Book ("Elements of Java", "56789", 1, 3.00);
    Offer bookOffer = new Offer ("average", 2.00,  bookForOffer, student);
    bookForRequest.request = bookRequest;
    bookForOffer.offer = bookOffer;
    student.requests.add(bookRequest);
    student.offers.add(bookOffer);
    
    student.save();
    bookForRequest.save();
    bookRequest.save();
    bookForOffer.save();
    bookOffer.save();
    
    List<Student> students = Student.find().findList();
    List<Book> books = Book.find().findList();
    List<Request> requests = Request.find().findList();
    List<Offer> offers = Offer.find().findList();
    
    assertEquals("Check Student's size",students.size(), 1 );
    assertEquals("Check Book's size", books.size(), 2);
    assertEquals("Check Request's size", requests.size(), 1);
    assertEquals("Check Offer's size", offers.size(), 1);
    
    assertEquals("Student-Request", students.get(0).requests.get(0), requests.get(0));
    assertEquals("Request-Student", requests.get(0).student, students.get(0));
    assertEquals("Student-Offer", students.get(0).offers.get(0), offers.get(0));
    assertEquals("Offer-Student", offers.get(0).student, students.get(0));
    assertEquals("Request-Book", requests.get(0).book, books.get(0));
    assertEquals("Book-Request", books.get(0).request, requests.get(0));
    assertEquals("Offer-Book", offers.get(0).book, books.get(1));
    assertEquals("Book-Offer", books.get(1).offer, offers.get(0));
    
  }
  
}
