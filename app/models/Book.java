package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import play.db.ebean.Model;

@Entity
public class Book extends Model {
  
  private static final long serialVersionUID = 2037421865215437569L;
  
  @Id
  public Long id;
  public String bookName;
  public String isbn;
  public int edition;
  public double defaultPrice;
  
  @OneToOne(mappedBy="book", cascade = CascadeType.ALL)
  public Offer offer;
  
  @OneToOne(mappedBy="book", cascade = CascadeType.ALL)
  public Request request;
  
  
  public Book(String bookName, String isbn, int edition, double defaultPrice) {
    this.bookName = bookName;
    this.isbn = isbn;
    this.edition = edition;
    this.defaultPrice = defaultPrice;
  }
  
  public static Finder<Long, Book> find () {
    return new Finder<Long, Book> (Long.class, Book.class);
  }

}
