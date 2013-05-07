package controllers;

import java.util.List;
import models.Student;
import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

  
  
  public static Result index() {
    Student student = new Student("Student-1", "John Smith", "smith@hawaii.edu");
    student.save();
    List<models.Offer> offers = student.getOffers();
    return ok(index.render(student.getStudName(), offers));
  }

}
