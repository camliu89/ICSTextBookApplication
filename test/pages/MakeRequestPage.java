package pages;

import static org.fest.assertions.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withId;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

public class MakeRequestPage extends FluentPage {

  private String url;

  public MakeRequestPage(WebDriver webDriver, int port, String studentId) {
    super(webDriver);
    this.url = "http://localhost:" + port + "/request/create?studentId=" + studentId;
  }

  public String getUrl() {
    return this.url;
  }

  public void isAt() {
    assertThat(title()).isEqualTo("Make a Request");
  }

  // For testing purposes, use the same string for both ID and name.
  public void makeNewRequest(String book, String condition, String price) {
    find("select", withId("Book")).find("option", withText(book)).click();
    find("select", withId("condition")).find("option", withText(condition)).click();
    fill("#RequestPrice").with(price);
    submit("#create");
  }

}
