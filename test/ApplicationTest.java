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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ApplicationTest {

  public FakeApplication application;

  @Before
  public void startApp() {
    application = fakeApplication(inMemoryDatabase());
    start(application);
  }

  @After
  public void stopApp() {
    stop(application);
  }

  @Test
  public void testModel() {
    Student student = new Student("Student-01", "StudentName", "yyy@yyy.edu");
    Book book = new Book("Book-01", "Algorithms", "12345", 1, 14.75);
    Request bookRequest = new Request("Request-01", "good", 12.00, book, student);
    Offer bookOffer = new Offer("Offer-01", "average", 2.00, book, student);
    book.getRequests().add(bookRequest);
    book.getOffers().add(bookOffer);
    student.getRequests().add(bookRequest);
    student.getOffers().add(bookOffer);

    student.save();
    ;
    bookRequest.save();
    bookOffer.save();
    book.save();

    List<Student> students = Student.find().findList();
    List<Book> books = Book.find().findList();
    List<Request> requests = Request.find().findList();
    List<Offer> offers = Offer.find().findList();

    assertEquals("Check Student's size", students.size(), 1);
    assertEquals("Check Book's size", books.size(), 1);
    assertEquals("Check Request's size", requests.size(), 1);
    assertEquals("Check Offer's size", offers.size(), 1);

    assertEquals("Student-Request", students.get(0).getRequests().get(0), requests.get(0));
    assertEquals("Request-Student", requests.get(0).getStudent(), students.get(0));
    assertEquals("Student-Offer", students.get(0).getOffers().get(0), offers.get(0));
    assertEquals("Offer-Student", offers.get(0).getStudent(), students.get(0));
    assertEquals("Request-Book", requests.get(0).getBook(), books.get(0));
    assertEquals("Book-Request", books.get(0).getRequests().get(0), requests.get(0));
    assertEquals("Offer-Book", offers.get(0).getBook(), books.get(0));
    assertEquals("Book-Offer", books.get(0).getOffers().get(0), offers.get(0));

    student.getOffers().clear();
    book.getOffers().clear();

    bookOffer.setBook(null);
    bookOffer.setStudent(null);

    student.save();
    bookOffer.save();
    book.save();

    assertTrue("Check student offers is empty", Student.find().findList().get(0).getOffers()
        .isEmpty());
    assertNull("Check offer book is not pointing to the book table", Offer.find().findList().get(0)
        .getBook());
    assertNull("Check offer book is not pointing to the student table", Offer.find().findList()
        .get(0).getStudent());
    assertTrue("Check book offer is empty", Book.find().findList().get(0).getOffers().isEmpty());

    bookOffer.delete();
    assertTrue("Delete bookOffer", Offer.find().findList().isEmpty());

  }

}