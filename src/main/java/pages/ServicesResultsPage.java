package pages;

import base.BasePage;
import utils.WindowUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServicesResultsPage extends BasePage {

    private final By results = By.xpath("//div[contains(@class,'g1sqkrme ')]/div");

    public ServicesResultsPage(WebDriver driver) {
        super(driver);
    }

    public void openRandomServiceInNewTab() throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> servs = driver.findElements(results);
        if (servs.isEmpty()) throw new RuntimeException("No service results found.");
        int randomNum2 = new Random().nextInt(Math.max(1, Math.min(servs.size(), 10)));

        int oldCount = driver.getWindowHandles().size();
        waits().clickable(servs.get(randomNum2)).click();
        WindowUtils.waitForNewWindow(driver, oldCount, Duration.ofSeconds(10));

        // switch to new tab and close current
        List<String> handles = new ArrayList<>(driver.getWindowHandles());
        driver.close();
        driver.switchTo().window(handles.get(1));
    }
}
