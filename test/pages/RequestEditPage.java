package pages;

import static org.fest.assertions.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withId;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

public class RequestEditPage extends FluentPage {

  private String url;

  public RequestEditPage(WebDriver webDriver, int port, int primaryKey) {
    super(webDriver);
    this.url = "http://localhost:" + port + "/request/" + primaryKey;
  }

  public String getUrl() {
    return this.url;
  }

  public void isAt() {
    assertThat(title()).isEqualTo("Edit Request");
  }
  
  public void editRequest(String book, String condition, String price) {
    find("select", withId("Book")).find("option", withText(book)).click();
    find("select", withId("condition")).find("option", withText(condition)).click();
    fill("#requestPrice").with(price);
    submit("#update");
  }

}
