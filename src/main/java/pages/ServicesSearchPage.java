package pages;

import base.BasePage;
import utils.DatePickerUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class ServicesSearchPage extends BasePage {

    private final By whereInput = By.xpath("//div[text()='Where']/following-sibling::div/input");
    private final By suggestion0 = By.id("bigsearch-query-location-suggestion-0");
    private final By typeOfService = By.xpath("//div[text()='Type of service']");
    private final By enabledServiceButtons = By.xpath("//button[not(@disabled)][contains(@id,'service-type-item-service_type_tag')]");
    private final By searchBtn = By.xpath("//div[text()='Search']");

    public ServicesSearchPage(WebDriver driver) {
        super(driver);
    }

    public void enterWhere(String city) throws InterruptedException {
        WebElement in = waits().visible(whereInput);
        in.click();
        in.sendKeys(city);
        Thread.sleep(3000);
        waits().clickable(suggestion0).click();
    }

    public void selectDateRange(String month1, String day1, String month2, String day2) {
        DatePickerUtils.selectDateLoop(driver, wait, month1, day1);
        DatePickerUtils.selectDateLoop(driver, wait, month2, day2);
    }

    public void chooseRandomServiceType() {
        waits().clickable(typeOfService).click();
        try {
            List<WebElement> serv = driver.findElements(enabledServiceButtons);
            int randomNum = new Random().nextInt(Math.max(1, Math.min(serv.size(), 10)));
            serv.get(randomNum).click();
        } catch (Exception ignored) {}
    }

    public void clickSearch() throws InterruptedException {
        Thread.sleep(3000);
        waits().clickable(searchBtn).click();
    }
}
