package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import play.db.ebean.Model;

@Entity
public class Book extends Model {

  private static final long serialVersionUID = 2037421865215437569L;

  @Id
  private Long primaryKey;
  private String bookId;
  private String bookName;
  private String isbn;
  private int edition;
  private double defaultPrice;

  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
  private List<Offer> offers = new ArrayList<>();

  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
  private List<Request> requests = new ArrayList<>();

  public Book(String bookId, String bookName, String isbn, int edition, double defaultPrice) {
    this.bookId = bookId;
    this.bookName = bookName;
    this.isbn = isbn;
    this.edition = edition;
    this.defaultPrice = defaultPrice;
  }

  public static Finder<Long, Book> find() {
    return new Finder<Long, Book>(Long.class, Book.class);
  }

  public Long getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(Long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getBookId() {
    return bookId;
  }

  public void setBookId(String bookId) {
    this.bookId = bookId;
  }

  public String getBookName() {
    return bookName;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public int getEdition() {
    return edition;
  }

  public void setEdition(int edition) {
    this.edition = edition;
  }

  public double getDefaultPrice() {
    return defaultPrice;
  }

  public void setDefaultPrice(double defaultPrice) {
    this.defaultPrice = defaultPrice;
  }

  public List<Offer> getOffers() {
    return offers;
  }

  public void setOffers(List<Offer> offers) {
    this.offers = offers;
  }

  public List<Request> getRequests() {
    return requests;
  }

  public void setRequests(List<Request> requests) {
    this.requests = requests;
  }

}