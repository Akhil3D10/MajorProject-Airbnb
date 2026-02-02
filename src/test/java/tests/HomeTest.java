package tests;

import base.BaseTest;
import pages.*;
import utils.ConfigReader;
import org.testng.annotations.Test;

import java.util.List;

public class HomeTest extends BaseTest {

    @Test
    public void staysFlow_shouldListTopFive() {
        String testName = "staysFlow_shouldListTopFive";
        String scenario = "Home";

        HomePage home = new HomePage(driver);
        home.dismissCookiesIfPresent();
        System.out.println("-------------------Home execution starting--------------------------");
        xlInfo(sheetHome, testName, scenario, "Start", "-------------------Home execution starting--------------------------");

        home.goToStaysTab();

        StaysSearchPage stays = new StaysSearchPage(driver);
        stays.enterWhere(ConfigReader.get("stays.where", "berlin"));
        stays.openDatePicker();
        stays.selectDateRange(
            ConfigReader.get("checkin.month1", "May 2026"),
            ConfigReader.get("checkin.day1", "30"),
            ConfigReader.get("checkout.month2", "June 2026"),
            ConfigReader.get("checkout.day2", "3")
        );
        stays.setAdultsViaThreeClicks();
        stays.clickSearch();

        StaysResultsPage results = new StaysResultsPage(driver);
        results.dismissPopupIfAny();

        FiltersPanel filters = results.openFilters();
        filters.setMaxPrice(Integer.parseInt(ConfigReader.get("max.price", "70000")));
        filters.apply();

        results.waitForResultsToStabilize();
        List<String> titles = results.getTopTitles(5);
        List<String> prices = results.getTopPrices(5);

        System.out.println("DEBUG: titles.size=" + titles.size() + ", prices.size=" + prices.size());
        xlInfo(sheetHome, testName, scenario, "DEBUG", "titles.size=" + titles.size() + ", prices.size=" + prices.size());

        int limit = Math.min(5, Math.min(titles.size(), prices.size()));
        if (limit == 0) {
            System.out.println("No listings matched the locator. The DOM may have changed or results are empty.");
            xlWarn(sheetHome, testName, scenario, "Top5", "No listings matched the locator. The DOM may have changed or results are empty.");
        }
        for (int i = 0; i < limit; i++) {
            String line = (i + 1) + ". " + titles.get(i) + " - " + prices.get(i);
            System.out.println(line);
            xlInfo(sheetHome, testName, scenario, "Top5", line);
        }

        System.out.println("-------------------Home execution ended------------------------");
        xlInfo(sheetHome, testName, scenario, "End", "-------------------Home execution ended------------------------");
    }
}

