package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import play.db.ebean.Model;

@Entity
public class Student extends Model {

  private static final long serialVersionUID = 115042688639203760L;
  
  @Id
  public long id;
  public String studName;
  public String email;
  
  @OneToMany(mappedBy="student", cascade = CascadeType.ALL)
  public List<Request> requests = new ArrayList<> ();
  
  @OneToMany(mappedBy="student", cascade = CascadeType.ALL)
  public List<Offer> offers = new ArrayList<> ();
  
  public Student (String studName, String email) {
    this.studName = studName;
    this.email = email;
  }
  
  public static Finder<Long, Student> find () {
    return new Finder<Long, Student> (Long.class, Student.class);
  }
  
  public List<Request> getRequests () {
    return this.requests;
  }
}
