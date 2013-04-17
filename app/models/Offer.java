package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import play.db.ebean.Model;

@Entity
public class Offer extends Model {

  private static final long serialVersionUID = 2036878281154324972L;

  @Id
  private Long primaryKey;
  private String offerId;
  private String condition;
  private double offerPrice;

  @ManyToOne(cascade = CascadeType.ALL)
  private Book book;

  @ManyToOne(cascade = CascadeType.ALL)
  private Student student;

  public Offer(String offerId, String condition, double offerPrice, Book book, Student student) {
    this.offerId = offerId;
    this.condition = condition;
    this.offerPrice = offerPrice;
    this.book = book;
    this.student = student;
  }

  public static Finder<Long, Offer> find() {
    return new Finder<Long, Offer>(Long.class, Offer.class);
  }

  public Long getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(Long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getOfferId() {
    return offerId;
  }

  public void setOfferId(String offerId) {
    this.offerId = offerId;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public double getOfferPrice() {
    return offerPrice;
  }

  public void setOfferPrice(double offerPrice) {
    this.offerPrice = offerPrice;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

}