package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Student extends Model {

  private static final long serialVersionUID = 115042688639203760L;

  @Id
  private Long primaryKey;
  @Required
  private String studentId;
  @Required
  private String studName;
  @Required
  private String email;

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
  private List<Request> requests = new ArrayList<>();

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
  private List<Offer> offers = new ArrayList<>();

  public Student(String studentId, String studName, String email) {
    this.studentId = studentId;
    this.studName = studName;
    this.email = email;
  }

  public static Finder<Long, Student> find() {
    return new Finder<Long, Student>(Long.class, Student.class);
  }

  public Long getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(Long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public String getStudName() {
    return studName;
  }

  public void setStudName(String studName) {
    this.studName = studName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Request> getRequests() {
    return requests;
  }

  public void setRequests(List<Request> requests) {
    this.requests = requests;
  }

  public List<Offer> getOffers() {
    return offers;
  }

  public void setOffers(List<Offer> offers) {
    this.offers = offers;
  }

  @Override
  public String toString() {
    return String.format("[Student %s %s, $s]", this.studentId, this.studName, this.email);
  }
}