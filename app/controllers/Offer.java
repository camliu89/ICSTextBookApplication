package controllers;

import static play.data.Form.form;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import play.data.Form;
import play.data.format.Formatters;
import play.mvc.Controller;
import play.mvc.Result;

public class Offer extends Controller {

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

}
