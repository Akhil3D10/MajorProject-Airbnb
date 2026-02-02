package tests;

import base.BaseTest;
import pages.*;
import utils.ConfigReader;
import org.testng.annotations.Test;

public class ServicesTest extends BaseTest {

    @Test
    public void servicesFlow_endToEnd() throws Exception {
        String testName = "servicesFlow_endToEnd";
        String scenario = "Services";

        System.out.println("-------------------Services execution------------------------");
        xlInfo(sheetSvc, testName, scenario, "Start", "-------------------Services execution------------------------");

        HomePage home = new HomePage(driver);
        home.dismissCookiesIfPresent();
        home.goToServicesTab();

        ServicesSearchPage svc = new ServicesSearchPage(driver);
        svc.enterWhere(ConfigReader.get("services.where", "Paris"));
        svc.selectDateRange(
            ConfigReader.get("checkin.month1", "May 2026"),
            ConfigReader.get("checkin.day1", "30"),
            ConfigReader.get("checkout.month2", "June 2026"),
            ConfigReader.get("checkout.day2", "3")
        );
        svc.chooseRandomServiceType();
        svc.clickSearch();

        ServicesResultsPage results = new ServicesResultsPage(driver);
        results.openRandomServiceInNewTab();

        ServiceDetailPage detail = new ServiceDetailPage(driver);
        String title = detail.getTitle();
        System.out.println("Title : " + title);
        xlInfo(sheetSvc, testName, scenario, "Detail", "Title : " + title);

        String rating = detail.getRatingIfPresent();
        if (!rating.isEmpty()) {
            System.out.println(rating + " Rated by visitors");
            xlInfo(sheetSvc, testName, scenario, "Detail", rating + " Rated by visitors");
        }

        String bill = detail.getStartingPrice();
        System.out.println("Strating price :\n " + bill);
        xlInfo(sheetSvc, testName, scenario, "Detail", "Strating price " + bill);

        // Screenshot & scroll
        String screenshotsDir = ConfigReader.get("screenshots.dir", "screenshots");
        detail.takeDetailScreenshot(testName + "_detail.png", screenshotsDir);
        detail.scrollPage();

        System.out.println("----------------------------End of Services-------------------------------");
        xlInfo(sheetSvc, testName, scenario, "End", "----------------------------End of Services-------------------------------");
    }
}
