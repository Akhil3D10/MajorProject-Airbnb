package tests;

import base.BaseTest;
import pages.*;
import utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ExperiencesTest extends BaseTest {

    @Test
    public void experiencesFlow_endToEnd() throws Exception {
        String testName = "experiencesFlow_endToEnd";
        String scenario = "Experiences";

        HomePage home = new HomePage(driver);
        System.out.println("-------------------Experiences execution starting------------------------");
        xlInfo(sheetExp, testName, scenario, "Start", "-------------------Experiences execution starting------------------------");

        home.goToExperiencesTab();
        ExperiencesSearchPage exp = new ExperiencesSearchPage(driver);
        exp.enterWhereWithSuggestion(ConfigReader.get("experiences.where", "Tokyo"));
        exp.selectDateRange(
            ConfigReader.get("checkin.month1", "May 2026"),
            ConfigReader.get("checkin.day1", "30"),
            ConfigReader.get("checkout.month2", "June 2026"),
            ConfigReader.get("checkout.day2", "3")
        );
        exp.setWho(
            Integer.parseInt(ConfigReader.get("adults", "3")),
            Integer.parseInt(ConfigReader.get("children", "2")),
            Integer.parseInt(ConfigReader.get("infants", "0"))
        );
        exp.clickSearch();

        ExperiencesResultsPage results = new ExperiencesResultsPage(driver);
        if (results.isNoExactMatches()) {
            System.out.println("No exact matches");
            xlWarn(sheetExp, testName, scenario, "NoMatches", "No exact matches");
            return;
        }
        xlInfo(sheetExp, testName, scenario, "Results", "Listings found, proceeding...");

        List<String> titles = results.getVisibleExperienceTitles();
        xlInfo(sheetExp, testName, scenario, "TitlesCount", "Titles found: " + titles.size());

        results.openRandomExperienceInNewTab();

        ExperienceDetailPage detail = new ExperienceDetailPage(driver);
        String heading = detail.getHeading();
        System.out.println("Place: " + heading);
        xlInfo(sheetExp, testName, scenario, "Detail", "Place: " + heading);

        String host = detail.getHostedByLine();
        System.out.println("It's " + host);
        xlInfo(sheetExp, testName, scenario, "Detail", "It's " + host);

        String location = detail.getLocation();
        System.out.println("Location: " + location);
        xlInfo(sheetExp, testName, scenario, "Detail", "Location: " + location);

        System.out.println("WHAT YOU'LL DO....");
        xlInfo(sheetExp, testName, scenario, "Detail", "WHAT YOU'LL DO....");
        List<String> what = detail.getWhatYouWillDoItems();
        what.forEach(s -> {
            System.out.println(s);
            xlInfo(sheetExp, testName, scenario, "Detail", s);
        });

        String total = detail.getTotalINR();
        System.out.println("TotalINR: " + total);
        xlInfo(sheetExp, testName, scenario, "Detail", "TotalINR: " + total);

        System.out.println("-------------------Experiences execution ended------------------------");
        xlInfo(sheetExp, testName, scenario, "End", "-------------------Experiences execution ended------------------------");
    }
}
