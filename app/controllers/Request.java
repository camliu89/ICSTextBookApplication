package controllers;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import play.data.Form;
import play.data.format.Formatters;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import static play.data.Form.form;

public class Request extends Controller {

  // /////////////// TESTING PURPOSE ///////////////////
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

  // /////////////// DEVELOPMENT PURPOSE ///////////////////
  public static Result create(String studentId) {

    Form<models.Request> requestForm = form(models.Request.class);
    return ok(makeRequest.render(studentId, requestForm));
  }

  public static Result save(String studentId) {

    // Bind the object Book to a String
    Formatters.register(models.Book.class, new Formatters.SimpleFormatter<models.Book>() {

      @Override
      public models.Book parse(String arg0, Locale arg1) throws ParseException {
        return models.Book.find().where().eq("bookName", arg0).findUnique();
      }

      @Override
      public String print(models.Book book, Locale arg1) {
        return book.getBookName().toString();
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

    // Bind all the request from the form
    Form<models.Request> requestForm = form(models.Request.class).bindFromRequest();
    // Get the data binder
    Map<String, String> requestData = requestForm.data();

    // Automatically add the offerId without having the user to fill it in the form
    int id = models.Request.find().all().size() + 1;
    requestData.put("requestId", "Request-" + id);
    requestData.put("Student", studentId);

    // update the form with the data from the form and the offerId
    requestForm = requestForm.bind(requestData);
    if (requestForm.hasErrors()) {
      System.out.println(requestForm.toString());
      return badRequest(makeRequest.render(studentId, requestForm));
    }

    models.Request request = requestForm.get();
    request.save();
    return redirect(routes.Application.index());
  }

  public static Result edit(Long primaryKey) {
    models.Request request = models.Request.find().byId(primaryKey);
    Form<models.Request> requestForm = form(models.Request.class).fill(request);
    return ok(requestEdit.render(primaryKey, requestForm));
  }

  public static Result update(Long primaryKey) {
    Form<models.Request> requestForm = form(models.Request.class).bindFromRequest();
    if (requestForm.hasErrors()) {
      System.out.println(requestForm.toString());
      return badRequest(requestEdit.render(primaryKey, requestForm));
    }
    requestForm.get().update(primaryKey);
    return redirect(routes.Application.index());
  }

  public static Result destroy(Long primaryKey) {
    models.Request request = models.Request.find().byId(primaryKey);
    request.setStudent(null);
    request.setBook(null);
    request.delete();
    return redirect(routes.Application.index());
  }
}
