import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.status;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.stop;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import models.Book;
import models.Offer;
import models.Request;
import models.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;
import java.util.HashMap;
import java.util.Map;

public class ControllerTest {

  private FakeApplication application;

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
  public void testStudentController() {
    // Test GET /student on an empty database
    Result result = callAction(controllers.routes.ref.Student.index());
    assertTrue("Empty Students", contentAsString(result).contains("No students"));

    // Test GET /student on a database containing a single student
    String studentId = "Student-01";
    Student student = new Student(studentId, "John Smith", "smith@hawaii.edu");
    student.save();
    result = callAction(controllers.routes.ref.Student.index());
    assertTrue("One Student", contentAsString(result).contains(studentId));

    // Test GET /student/Student-01
    result = callAction(controllers.routes.ref.Student.details(studentId));
    assertTrue("Student detail", contentAsString(result).contains(studentId));

    // Test GET /student/Student-01 and make sure we get a 404
    result = callAction(controllers.routes.ref.Student.details("BadStudentId"));
    assertEquals("Student detail (bad)", NOT_FOUND, status(result));

    // Test POST /students (with simulated, valid form data)
    Map<String, String> studentData = new HashMap<String, String>();
    studentData.put("studentId", "Student-02");
    studentData.put("studName", "Alex Doe");
    studentData.put("email", "doe@hawaii.edu");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create new Student", OK, status(result));

    // Test POST /students (with invalid form data)
    request = fakeRequest();
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create new Student", BAD_REQUEST, status(result));

    // Test DELETE /students/Student-01 (a valid StudentId)
    result = callAction(controllers.routes.ref.Student.delete(studentId));
    assertEquals("Delete current student OK", OK, status(result));
    result = callAction(controllers.routes.ref.Student.details(studentId));
    assertEquals("Delete student gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Student.delete(studentId));
    assertEquals("Delete missing student also OK", OK, status(result));

  }

  @Test
  public void testOfferController() {
    // Test GET /offer on an empty database
    Result result = callAction(controllers.routes.ref.Offer.index());
    assertTrue("Empty Offers", contentAsString(result).contains("No offers"));

    // Test GET /offer on a database containing a single offer
    String offerId = "Offer-01";
    Book book = new Book("Book-01", "bookname", "12334", 1, 15.00);
    Student student = new Student("Student-01", "doe", "ddd@ddd.edu");
    Offer offer = new Offer(offerId, "good", 13.30, book, student);
    offer.save();
    book.save();
    student.save();
    result = callAction(controllers.routes.ref.Offer.index());
    assertTrue("One Offer", contentAsString(result).contains(offerId));

    // Test GET /offer/Offer-01
    result = callAction(controllers.routes.ref.Offer.details(offerId));
    assertTrue("Offer detail", contentAsString(result).contains(offerId));

    // Test GET /offer/Offer-01 and make sure we get a 404
    result = callAction(controllers.routes.ref.Offer.details("BadOfferId"));
    assertEquals("Offer detail (bad)", NOT_FOUND, status(result));

    // Test POST /offers (with simulated, valid form data)
    Map<String, String> offerData = new HashMap<String, String>();
    Book book2 = new Book("Book-02", "bookname2", "123345", 1, 16.00);
    Student student2 = new Student("Student-02", "joe", "jjj@jjj.edu");
    offerData.put("offerId", "Offer-02");
    offerData.put("condition", "good");
    offerData.put("offerPrice", "13.50");
    offerData.put("book", book2.getBookId());
    offerData.put("student", student2.getStudentId());
    book2.save();
    student2.save();
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create new Offer", OK, status(result));

    // Test POST /offers (with invalid form data)
    request = fakeRequest();
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create new Offer", BAD_REQUEST, status(result));

    // Test DELETE /offers/Offer-01 (a valid OfferId)
    result = callAction(controllers.routes.ref.Offer.delete(offerId));
    assertEquals("Delete current offer OK", OK, status(result));
    result = callAction(controllers.routes.ref.Offer.details(offerId));
    assertEquals("Delete offer gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Offer.delete(offerId));
    assertEquals("Delete missing offer also OK", OK, status(result));
  }

  @Test
  public void testRequestController() {
    // Test GET /request on an empty database
    Result result = callAction(controllers.routes.ref.Request.index());
    assertTrue("Empty Requests", contentAsString(result).contains("No requests"));

    // Test GET /request on a database containing a single request
    String requestId = "Request-01";
    Book book = new Book("Book-01", "bookname", "12334", 1, 15.00);
    Student student = new Student("Student-01", "doe", "ddd@ddd.edu");
    Request bookRequest = new Request(requestId, "good", 13.30, book, student);
    bookRequest.save();
    book.save();
    student.save();
    result = callAction(controllers.routes.ref.Request.index());
    assertTrue("One request", contentAsString(result).contains(requestId));

    // Test GET /requests/Request-01
    result = callAction(controllers.routes.ref.Request.details(requestId));
    assertTrue("Request detail", contentAsString(result).contains(requestId));

    // Test GET /requests/Request-01 and make sure we get a 404
    result = callAction(controllers.routes.ref.Request.details("BadRequestId"));
    assertEquals("Request detail (bad)", NOT_FOUND, status(result));

    // Test POST /offers (with simulated, valid form data)
    Map<String, String> requestData = new HashMap<String, String>();
    Book book2 = new Book("Book-02", "bookname2", "123345", 1, 16.00);
    Student student2 = new Student("Student-02", "joe", "jjj@jjj.edu");
    requestData.put("requestId", "Request-02");
    requestData.put("condition", "good");
    requestData.put("requestPrice", "13.50");
    requestData.put("book", book2.getBookId());
    requestData.put("student", student2.getStudentId());
    book2.save();
    student2.save();
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), request);
    assertEquals("Create new Request", OK, status(result));

    // Test POST /requests (with invalid form data)
    request = fakeRequest();
    result = callAction(controllers.routes.ref.Request.newRequest(), request);
    assertEquals("Create new a Request", BAD_REQUEST, status(result));

    // Test DELETE /requests/Request-01 (a valid OfferId)
    result = callAction(controllers.routes.ref.Request.delete(requestId));
    assertEquals("Delete current request OK", OK, status(result));
    result = callAction(controllers.routes.ref.Request.details(requestId));
    assertEquals("Delete request gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Request.delete(requestId));
    assertEquals("Delete missing request also OK", OK, status(result));

  }

  @Test
  public void testBookController() {
    // Test GET /book on an empty database
    Result result = callAction(controllers.routes.ref.Book.index());
    assertTrue("Empty Books", contentAsString(result).contains("No books"));

    // Test GET /book on a database containing a single book
    String bookId = "Book-01";
    Book book = new Book(bookId, "Java", "1234", 2, 13.44);
    book.save();
    result = callAction(controllers.routes.ref.Book.index());
    assertTrue("One Book", contentAsString(result).contains(bookId));

    // Test GET /books/Book-01
    result = callAction(controllers.routes.ref.Book.details(bookId));
    assertTrue("Book detail", contentAsString(result).contains(bookId));

    // Test GET /book/Book-01 and make sure we get a 404
    result = callAction(controllers.routes.ref.Book.details("BadBookId"));
    assertEquals("Bad detail (bad)", NOT_FOUND, status(result));
    
    // Test POST /books (with simulated, valid form data)
    Map<String, String> bookData = new HashMap<String, String>();
    bookData.put("bookId", "Student-02");
    bookData.put("bookName", "Play");
    bookData.put("isbn", "213324");
    bookData.put("defaultPrice", "14.43");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(bookData);
    result = callAction(controllers.routes.ref.Book.newBook(), request);
    assertEquals("Create new Book", OK, status(result));

    // Test POST /books (with invalid form data)
    request = fakeRequest();
    result = callAction(controllers.routes.ref.Book.newBook(), request);
    assertEquals("Create new bad Book", BAD_REQUEST, status(result));

    // Test DELETE /books/Book-01 (a valid Book-Id)
    result = callAction(controllers.routes.ref.Book.delete(bookId));
    assertEquals("Delete current book OK", OK, status(result));
    result = callAction(controllers.routes.ref.Book.details(bookId));
    assertEquals("Delete book gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Book.delete(bookId));
    assertEquals("Delete missing book also OK", OK, status(result));

  }
  
}