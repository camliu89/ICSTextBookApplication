package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import play.db.ebean.Model;

@Entity
public class Request extends Model {

  private static final long serialVersionUID = 3953766974613072828L;

  @Id
  private Long primaryKey;
  private String requestId;
  private String condition;
  private double offerPrice;

  @ManyToOne(cascade = CascadeType.ALL)
  private Book book;

  @ManyToOne(cascade = CascadeType.ALL)
  private Student student;

  public Request(String requestId, String condition, double offerPrice, Book book, Student student) {
    this.requestId = requestId;
    this.condition = condition;
    this.offerPrice = offerPrice;
    this.book = book;
    this.student = student;
  }

  public static Finder<Long, Request> find() {
    return new Finder<Long, Request>(Long.class, Request.class);
  }

  public Long getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(Long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
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

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Book getBook() {
    return book;
  }

  public void SetBook(Book book) {
    this.book = book;
  }

}
