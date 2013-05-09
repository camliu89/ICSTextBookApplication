package pages;

import static org.fest.assertions.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withId;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

public class MakeOfferPage extends FluentPage {

  private String url;

  public MakeOfferPage(WebDriver webDriver, int port, String studentId) {
    super(webDriver);
    this.url = "http://localhost:" + port + "/offer/create?studentId=" + studentId;
  }

  public String getUrl() {
    return this.url;
  }

  public void isAt() {
    assertThat(title()).isEqualTo("Make an Offer");
  }

  // For testing purposes, use the same string for both ID and name.
  public void makeNewOffer(String book, String condition, String price) {
    find("select", withId("Book")).find("option", withText(book)).click();
    find("select", withId("condition")).find("option", withText(condition)).click();
    fill("#offerPrice").with(price);
    submit("#create");
  }
  
}
