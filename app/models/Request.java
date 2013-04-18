package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Request extends Model {

  private static final long serialVersionUID = 3953766974613072828L;

  @Id
  private Long primaryKey;
  @Required
  private String requestId;
  private String condition;
  @Required
  private Double requestPrice;

  @Required
  @ManyToOne(cascade = CascadeType.ALL)
  private Book book;
  @Required
  @ManyToOne(cascade = CascadeType.ALL)
  private Student student;

  public Request(String requestId, String condition, Double requestPrice, Book book, Student student) {
    this.requestId = requestId;
    this.condition = condition;
    this.requestPrice = requestPrice;
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

  public Double getRequestPrice() {
    return requestPrice;
  }

  public void setRequestPrice(Double requestPrice) {
    this.requestPrice = requestPrice;
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

  public void setBook(Book book) {
    this.book = book;
  }

  @Override
  public String toString() {
    return String.format("[Request %s, %s, %f, %s, %s]", this.requestId, this.condition,
        this.requestPrice, this.book.getBookId(), this.student.getStudentId());
  }

}