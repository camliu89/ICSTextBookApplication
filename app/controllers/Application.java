package controllers;

import java.util.List;
import models.Student;
import models.Book;
import models.Offer;
import models.Request;
import play.mvc.*;
import views.html.index;

;

public class Application extends Controller {

  public static Result index() {
    // For development purpose, create programatically a student and some books the first time the
    // user hits the home page.
    if (Student.find().all().size() <= 0) {
      Student student = new Student("Student-1", "John Smith", "smith@hawaii.edu");
      student.save();
    }
    if (Book.find().all().size() <= 0) {
      Book book = new Book("Book-1", "Operating System", "123456", 6, 19.00);
      Book book1 = new Book("Book-2", "Elements of Java", "213434", 1, 3.50);
      Book book2 = new Book("Book-3", "Effective Java", "784823", 2, 10.50);
      Book book3 = new Book("Book-4", "Java in a Nutshell", "998663", 4, 20.00);
      book.save();
      book1.save();
      book2.save();
      book3.save();
    }
    Student smith = Student.find().where().eq("studentId", "Student-1").findUnique();
    List<Offer> offers = smith.getOffers();
    List<Request> requests = smith.getRequests();
    return ok(index.render(smith, offers, requests));
  }

}
