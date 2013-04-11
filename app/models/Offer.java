package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import play.db.ebean.Model;

@Entity
public class Offer extends Model {
  
  private static final long serialVersionUID = 2036878281154324972L;
  
  @Id
  public Long id;
  public String condition;
  public double offerPrice;
  
  @OneToOne(cascade = CascadeType.ALL)
  public Book book;
  
  @ManyToOne(cascade = CascadeType.ALL)
  public Student student;
  
  public Offer (String condition, double offerPrice, Book book, Student student) {
    this.condition = condition;
    this.offerPrice = offerPrice;
    this.book = book;
    this.student = student;
  }
  
  public static Finder<Long, Offer> find () {
    return new Finder<Long, Offer> (Long.class, Offer.class);
  }
}
