package pages;

import base.BasePage;
import utils.DatePickerUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ExperiencesSearchPage extends BasePage {

    private final By whereInput = By.xpath("//input[@placeholder='Search by city or landmark']");
    private final By suggestion = By.xpath("(//b[@class='b1viecjw atm_cs_14spzga dir dir-ltr'])[1]");

    private final By whoBtn     = By.xpath("//div[text()='Who']");
    // Stepper XPaths based on your setStepperValue implementation
    private String stepperValue(String type) { return "//span[@data-testid='stepper-" + type + "-value']"; }
    private String stepperPlus(String type)  { return "//button[@data-testid='stepper-" + type + "-increase-button']"; }
    private String stepperMinus(String type) { return "//button[@data-testid='stepper-" + type + "-decrease-button']"; }

    private final By searchBtn  = By.xpath("//div[text()='Search']");

    public ExperiencesSearchPage(WebDriver driver) {
        super(driver);
    }

    public void enterWhereWithSuggestion(String city) throws InterruptedException {
        waits().visible(whereInput).sendKeys(city);
        Thread.sleep(3000);
        waits().clickable(suggestion).click();
    }

    public void selectDateRange(String month1, String day1, String month2, String day2) throws Exception {
        // Use the exact variant you used in the script for Experiences:
        DatePickerUtils.selectDateLoop(driver, month1, day1);
        DatePickerUtils.selectDateLoop(driver, month2, day2);
    }

    public void setWho(int adults, int children, int infants) {
        waits().clickable(whoBtn).click();
        setStepper("adults", adults);
        setStepper("children", children);
        setStepper("infants", infants);
    }

    private void setStepper(String type, int target) {
        WebElement valueElement = driver.findElement(By.xpath(stepperValue(type)));
        WebElement plusButton   = driver.findElement(By.xpath(stepperPlus(type)));
        WebElement minusButton  = driver.findElement(By.xpath(stepperMinus(type)));

        int current = Integer.parseInt(valueElement.getText());
        int diff = target - current;
        if (diff > 0) {
            for (int i = 0; i < diff; i++) plusButton.click();
        } else if (diff < 0) {
            for (int i = 0; i < Math.abs(diff); i++) minusButton.click();
        }
    }

    public void clickSearch() {
        waits().clickable(searchBtn).click();
    }
}
