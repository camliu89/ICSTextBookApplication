package controllers;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import play.data.Form;
import play.data.format.Formatters;
import play.mvc.Controller;
import play.mvc.Result;
import static play.data.Form.form;

public class Request extends Controller {

  public static Result index() {
    List<models.Request> requests = models.Request.find().findList();
    return ok(requests.isEmpty() ? "No requests" : requests.toString());
  }

  public static Result details(String requestId) {
    models.Request request = models.Request.find().where().eq("requestId", requestId).findUnique();
    return (request == null) ? notFound("no request found") : ok(request.toString());
  }

  public static Result newRequest() {
    // Bind the object Book to a String
    Formatters.register(models.Book.class, new Formatters.SimpleFormatter<models.Book>() {

      @Override
      public models.Book parse(String arg0, Locale arg1) throws ParseException {
        return models.Book.find().where().eq("bookId", arg0).findUnique();
      }

      @Override
      public String print(models.Book book, Locale arg1) {
        return book.getBookId().toString();
      }
    });

    // Bind the object Student to a String
    Formatters.register(models.Student.class, new Formatters.SimpleFormatter<models.Student>() {

      @Override
      public models.Student parse(String arg0, Locale arg1) throws ParseException {
        return models.Student.find().where().eq("studentId", arg0).findUnique();
      }

      @Override
      public String print(models.Student student, Locale arg1) {
        return student.getStudentId().toString();
      }
    });

    Form<models.Request> requestForm = form(models.Request.class).bindFromRequest();
    if (requestForm.hasErrors()) {
      return badRequest("Request Id, book, student, and request price required");
    }
    models.Request request = requestForm.get();
    request.save();
    return ok(request.toString());
  }

  public static Result delete(String requestId) {
    models.Request request = models.Request.find().where().eq("requestId", requestId).findUnique();
    if (request != null) {
      request.delete();
    }
    return ok();
  }
}
