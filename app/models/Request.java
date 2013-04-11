package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import play.db.ebean.Model;

@Entity
public class Request extends Model {

  private static final long serialVersionUID = 3953766974613072828L;
  
  @Id
  public Long id;
  public String condition;
  public double offerPrice;
  
  @OneToOne(cascade = CascadeType.ALL)
  public Book book;
  
  @ManyToOne(cascade = CascadeType.ALL)
  public Student student;
  
  public Request (String condition, double offerPrice, Book book, Student student) {
    this.condition = condition;
    this.offerPrice = offerPrice;
    this.book = book;
    this.student = student;
  }
  
  public static Finder<Long, Request> find () {
    return new Finder<Long, Request> (Long.class, Request.class);
  }
}
