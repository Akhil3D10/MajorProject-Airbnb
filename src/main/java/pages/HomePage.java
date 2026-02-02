package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By popupGotIt = By.xpath("//button[text()='Got it']");
    private final By tabStays   = By.id("search-block-tab-STAYS");
    private final By tabExp     = By.id("search-block-tab-EXPERIENCES");
    private final By tabSvc     = By.xpath("//a[@id='search-block-tab-SERVICES']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void dismissCookiesIfPresent() {
        try {
            waits().visible(popupGotIt).click();
        } catch (Exception ignored) {}
    }

    public void goToStaysTab() {
        try {
            waits().clickable(tabStays).click();
        } catch (Exception ignored) {}
    }

    public void goToExperiencesTab() {
        waits().clickable(tabExp).click();
    }

    public void goToServicesTab() {
        waits().clickable(tabSvc).click();
    }
}
