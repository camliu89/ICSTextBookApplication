import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import org.junit.Test;
import pages.IndexPage;
import pages.MakeOfferPage;
import pages.MakeRequestPage;
import pages.OfferEditPage;
import pages.RequestEditPage;
import play.libs.F.Callback;
import play.test.TestBrowser;
import static org.fest.assertions.Assertions.assertThat;

public class ViewTest {

  public final int PORT = 3333;

  @Test
  public void testIndexPage() {
    running(testServer(PORT, fakeApplication(inMemoryDatabase())), HTMLUNIT,
        new Callback<TestBrowser>() {
          public void invoke(TestBrowser browser) {
            IndexPage homePage = new IndexPage(browser.getDriver(), PORT);

            // Check index page
            browser.goTo(homePage);
            homePage.isAt();

            // check each other page, but before make sure to click on the index page again
            browser.goTo(homePage);
            homePage.gotoMakeOffer();
            assertThat(homePage.title()).isEqualTo("Make an Offer");

            browser.goTo(homePage);
            homePage.gotoMakeRequest();
            assertThat(homePage.title()).isEqualTo("Make a Request");

          }
        });
  }

  @Test
  public void testMakeOfferPage() {
    running(testServer(PORT, fakeApplication(inMemoryDatabase())), HTMLUNIT,
        new Callback<TestBrowser>() {
          public void invoke(TestBrowser browser) {
            MakeOfferPage makeOfferPage = new MakeOfferPage(browser.getDriver(), PORT, "Student-1");
            IndexPage homePage = new IndexPage(browser.getDriver(), PORT);

            // For development purpose, I am making a new student and some books the first the user
            // hits on the index page.
            // Therefore, I have to go to the index page first and then to the makeOffer page.
            browser.goTo(homePage);
            homePage.click("#makeOffer");
            makeOfferPage.isAt();

            // Make offer
            makeOfferPage.makeNewOffer("Operating System", "Excellent", "5");
            homePage.isAt();
            assertThat(homePage.pageSource()).contains("Operating System");
            assertThat(homePage.pageSource()).contains("Excellent");
            assertThat(homePage.pageSource()).contains("$5.0");

            // Delete offer
            homePage.deleteOffer();
            assertThat(homePage.pageSource()).doesNotContain("Operating System");
            assertThat(homePage.pageSource()).doesNotContain("Excellent");
            assertThat(homePage.pageSource()).doesNotContain("$5.0");
          }
        });
  }

  @Test
  public void testMakeRequestPage() {
    running(testServer(PORT, fakeApplication(inMemoryDatabase())), HTMLUNIT,
        new Callback<TestBrowser>() {
          public void invoke(TestBrowser browser) {
            MakeRequestPage makeRequestPage =
                new MakeRequestPage(browser.getDriver(), PORT, "Student-1");
            IndexPage homePage = new IndexPage(browser.getDriver(), PORT);

            // For development purpose, I am making a new student and some books the first the user
            // hits on the index page.
            // Therefore, I have to go to the index page first and then to the makeOffer page.
            browser.goTo(homePage);
            homePage.click("#makeRequest");
            makeRequestPage.isAt();

            // Make request
            makeRequestPage.makeNewRequest("Elements of Java", "Average", "5.51");
            homePage.isAt();
            assertThat(homePage.pageSource()).contains("Elements of Java");
            assertThat(homePage.pageSource()).contains("Average");
            assertThat(homePage.pageSource()).contains("$5.51");

            // Delete request
            homePage.deleteRequest();
            assertThat(homePage.pageSource()).doesNotContain("Elements of Java");
            assertThat(homePage.pageSource()).doesNotContain("Average");
            assertThat(homePage.pageSource()).doesNotContain("$5.51");

          }
        });
  }

  @Test
  public void testOfferEditPage() {
    running(testServer(PORT, fakeApplication(inMemoryDatabase())), HTMLUNIT,
        new Callback<TestBrowser>() {
          public void invoke(TestBrowser browser) {
            MakeOfferPage makeOfferPage = new MakeOfferPage(browser.getDriver(), PORT, "Student-1");
            IndexPage homePage = new IndexPage(browser.getDriver(), PORT);

            // For development purpose, I am making a new student and some books the first the user
            // hits on the index page.
            // Therefore, I have to go to the index page first and then to the makeOffer page.
            browser.goTo(homePage);
            homePage.click("#makeOffer");
            makeOfferPage.isAt();

            // Make offer
            makeOfferPage.makeNewOffer("Operating System", "Excellent", "5");
            homePage.isAt();
            assertThat(homePage.pageSource()).contains("Operating System");
            assertThat(homePage.pageSource()).contains("Excellent");
            assertThat(homePage.pageSource()).contains("$5.0");

            // Edit offer
            OfferEditPage offerEditPage = new OfferEditPage(browser.getDriver(), PORT, 1);
            browser.goTo(offerEditPage);
            offerEditPage.isAt();
            offerEditPage.editOffer("Elements of Java", "Bad", "6");

            // Check that the offer was edited and displayed at the home page
            homePage.isAt();
            assertThat(homePage.pageSource()).contains("Elements of Java");
            assertThat(homePage.pageSource()).contains("Bad");
            assertThat(homePage.pageSource()).contains("$6.0");

            // Check that the previous offer data is changed and not displayed anymore
            assertThat(homePage.pageSource()).doesNotContain("Operating System");
            assertThat(homePage.pageSource()).doesNotContain("Excellent");
            assertThat(homePage.pageSource()).doesNotContain("$5.0");
          }
        });
  }

  @Test
  public void testRequestEditPage() {
    running(testServer(PORT, fakeApplication(inMemoryDatabase())), HTMLUNIT,
        new Callback<TestBrowser>() {
          public void invoke(TestBrowser browser) {
            MakeRequestPage makeRequestPage =
                new MakeRequestPage(browser.getDriver(), PORT, "Student-1");
            IndexPage homePage = new IndexPage(browser.getDriver(), PORT);

            // For development purpose, I am making a new student and some books the first the user
            // hits on the index page.
            // Therefore, I have to go to the index page first and then to the makeOffer page.
            browser.goTo(homePage);
            homePage.click("#makeRequest");
            makeRequestPage.isAt();

            // Make request
            makeRequestPage.makeNewRequest("Elements of Java", "Average", "5.51");
            homePage.isAt();
            assertThat(homePage.pageSource()).contains("Elements of Java");
            assertThat(homePage.pageSource()).contains("Average");
            assertThat(homePage.pageSource()).contains("$5.51");

            // Edit request
            RequestEditPage requestEditPage = new RequestEditPage(browser.getDriver(), PORT, 1);
            browser.goTo(requestEditPage);
            requestEditPage.isAt();
            requestEditPage.editRequest("Java in a Nutshell", "Bad", "10");

            // Check that the request was edited and displayed at the home page
            homePage.isAt();
            assertThat(homePage.pageSource()).contains("Java in a Nutshell");
            assertThat(homePage.pageSource()).contains("Bad");
            assertThat(homePage.pageSource()).contains("$10.0");

            // Check that the previous request data is changed and not displayed anymore
            assertThat(homePage.pageSource()).doesNotContain("Elements of Java");
            assertThat(homePage.pageSource()).doesNotContain("Average");
            assertThat(homePage.pageSource()).doesNotContain("$5.51");
          }
        });
  }
}