package pages;

import static org.fest.assertions.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withId;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

public class OfferEditPage extends FluentPage {

  private String url;

  public OfferEditPage(WebDriver webDriver, int port, int primaryKey) {
    super(webDriver);
    this.url = "http://localhost:" + port + "/offer/" + primaryKey;
  }

  public String getUrl() {
    return this.url;
  }

  public void isAt() {
    assertThat(title()).isEqualTo("Edit Offer");
  }

  public void editOffer(String book, String condition, String price) {
    find("select", withId("Book")).find("option", withText(book)).click();
    find("select", withId("condition")).find("option", withText(condition)).click();
    fill("#offerPrice").with(price);
    submit("#update");
  }

}
