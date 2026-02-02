package pages;

import base.BasePage;
import utils.ScreenshotUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ServiceDetailPage extends BasePage {

    private final By title = By.xpath("//h1");
    private final By rating = By.xpath("//div[contains(@class,'s1dlmy8j ')]/parent::span");
    private final By startingPrice = By.xpath("//span[contains(@class,'p1wsu7fi ')][1]");

    public ServiceDetailPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return waits().visible(title).getText();
    }

    public String getRatingIfPresent() {
        try {
            return waits().visible(rating).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getStartingPrice() {
        return waits().visible(startingPrice).getText();
    }

    public void takeDetailScreenshot(String fileName, String screenshotsDir) throws Exception {
        ScreenshotUtils.capture(driver, screenshotsDir, fileName);
    }

    public void scrollPage() {
        elems().scrollToMiddle();
        elems().scrollToBottom();
    }
}
