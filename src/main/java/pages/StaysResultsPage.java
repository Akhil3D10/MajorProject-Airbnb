package pages;

import base.BasePage;
import utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class StaysResultsPage extends BasePage {

    private final By popupGotIt = By.xpath("//button[text()='Got it']");
    private final By filterBtn  = By.xpath("//button[@data-testid='category-bar-filter-button']");
    private final By titlesBy   = By.xpath("//div[contains(@class,'gsgwcjk ')]/div//div[@data-testid='listing-card-title']");
    private final By pricesBy   = By.xpath("//span[@class='u1opajno atm_7l_1dmvgf5 atm_cs_bs05t3 atm_rd_us8791 atm_rq_glywfm atm_cs_l3jtxx__1v156lz dir dir-ltr']");

    public StaysResultsPage(WebDriver driver) {
        super(driver);
    }

    public void dismissPopupIfAny() {
        try {
            waits().visible(popupGotIt).click();
        } catch (Exception ignored) {}
    }

    public FiltersPanel openFilters() {
        waits().clickable(filterBtn).click();
        return new FiltersPanel(driver);
    }

    public void waitForResultsToStabilize() {
        waits().presence(titlesBy);
        WaitUtils.waitForCountToStabilize(driver, titlesBy, Duration.ofSeconds(8));
    }

    public List<String> getTopTitles(int limit) {
        List<String> out = new ArrayList<>();
        List<WebElement> titles = driver.findElements(titlesBy);
        int n = Math.min(limit, titles.size());
        for (int i = 0; i < n; i++) {
            try {
                titles = driver.findElements(titlesBy);
                out.add(titles.get(i).getText());
            } catch (StaleElementReferenceException sere) {
                i--; // retry
            } catch (Exception ignored) {}
        }
        return out;
    }

    public List<String> getTopPrices(int limit) {
        List<String> out = new ArrayList<>();
        List<WebElement> prices = driver.findElements(pricesBy);
        int n = Math.min(limit, prices.size());
        for (int i = 0; i < n; i++) {
            try {
                prices = driver.findElements(pricesBy);
                out.add(prices.get(i).getText());
            } catch (StaleElementReferenceException sere) {
                i--;
            } catch (Exception ignored) {}
        }
        return out;
    }
}
