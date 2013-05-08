package controllers;

import static play.data.Form.form;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import play.data.Form;
import play.data.format.Formatters;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Offer extends Controller {

  // /////////////// TESTING PURPOSE ///////////////////
  public static Result index() {
    List<models.Offer> offers = models.Offer.find().findList();
    return ok(offers.isEmpty() ? "No offers" : offers.toString());
  }

  public static Result details(String offerId) {
    models.Offer offer = models.Offer.find().where().eq("offerId", offerId).findUnique();
    return (offer == null) ? notFound("No offer found") : ok(offer.toString());
  }

  public static Result newOffer() {
    // Bind the object Book to a String
    Formatters.register(models.Book.class, new Formatters.SimpleFormatter<models.Book>() {

      @Override
      public models.Book parse(String arg0, Locale arg1) throws ParseException {
        return models.Book.find().where().eq("bookName", arg0).findUnique();
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

    Form<models.Offer> offerForm = form(models.Offer.class).bindFromRequest();
    if (offerForm.hasErrors()) {
      return badRequest("Offer Id, book, student and offer price required.");
    }
    models.Offer offer = offerForm.get();
    offer.save();
    return ok(offer.toString());
  }

  public static Result delete(String offerId) {
    models.Offer offer = models.Offer.find().where().eq("offerId", offerId).findUnique();
    if (offer != null) {
      offer.delete();
    }
    return ok();
  }

  // /////////////// DEVELOPMENT PURPOSE ///////////////////
  public static Result create(String studentId) {

    Form<models.Offer> offerForm = form(models.Offer.class);
    return ok(makeOffer.render(studentId, offerForm));
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
    Form<models.Offer> offerForm = form(models.Offer.class).bindFromRequest();
    // Get the data binder
    Map<String, String> offerData = offerForm.data();

    // Automatically add the offerId without having the user to fill it in the form
    int id = models.Offer.find().all().size() + 1;
    offerData.put("offerId", "Offer-" + id);
    offerData.put("Student", studentId);

    // update the form with the data from the form and the offerId
    offerForm = offerForm.bind(offerData);
    if (offerForm.hasErrors()) {
      System.out.println(offerForm.toString());
      return badRequest(makeOffer.render(studentId, offerForm));
    }

    models.Offer offer = offerForm.get();
    offer.save();
    return redirect(routes.Application.index());
  }

  public static Result edit(Long primaryKey) {
    models.Offer offer = models.Offer.find().byId(primaryKey);
    Form<models.Offer> offerForm = form(models.Offer.class).fill(offer);
    return ok(offerEdit.render(primaryKey, offerForm));
  }

  public static Result update(Long primaryKey) {
    Form<models.Offer> offerForm = form(models.Offer.class).bindFromRequest();
    if (offerForm.hasErrors()) {
      System.out.println(offerForm.toString());
      return badRequest(offerEdit.render(primaryKey, offerForm));
    }
    offerForm.get().update(primaryKey);
    return redirect(routes.Application.index());
  }

  public static Result destroy(Long primaryKey) {
    models.Offer offer = models.Offer.find().byId(primaryKey);
    offer.setStudent(null);
    offer.setBook(null);
    offer.delete();
    return redirect(routes.Application.index());
  }

}
