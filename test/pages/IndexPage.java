package pages;

import static org.fest.assertions.Assertions.assertThat;
import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

public class IndexPage extends FluentPage {
  
  private String url;
  
  public IndexPage (WebDriver webDriver, int port) {
    super(webDriver);
    this.url = "http://localhost:" + port;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public void isAt() {
    assertThat(title()).isEqualTo("Your books");
  }
  
  public void gotoMakeOffer() {
    click("#makeOffer");
  }
  
  public void gotoMakeRequest() {
    click("#makeRequest");
  }
  
  public void deleteOffer () {
    click("#deleteOffer");
  }
  
  public void deleteRequest () {
    click("#deleteRequest");
  }

}
