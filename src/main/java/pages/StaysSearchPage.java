package pages;

import base.BasePage;
import utils.DatePickerUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StaysSearchPage extends BasePage {

    private final By whereContainer = By.xpath("//div[text()='Where']/parent::div");
    private final By whereInput     = By.xpath("//div[text()='Where']/following-sibling::div/input");

    // WHEN open preferred
    private final By whenOpen = By.xpath("//div[@class='f67r5k6 atm_mk_h2mmj6 atm_l8_1ieir7h atm_wq_cs5v99 atm_vy_1osqo2v atm_jb_idpfg4 atm_ks_zryt35 dir dir-ltr']");
    private final By checkInBtn = By.xpath("//button[contains(@aria-label,'Check-in')]");

    // WHO
    private final By whoButton = By.xpath("//div[text()='Who']");
    private final By adultPlusSpan = By.xpath("(//span[contains(@class,'atm_e2_qslrf5 ')])[2]");

    // SEARCH
    private final By searchBtn = By.xpath("//div[text()='Search']");

    public StaysSearchPage(WebDriver driver) {
        super(driver);
    }

    public void enterWhere(String city) {
        waits().visible(whereContainer).click();
        waits().visible(whereInput).sendKeys(city);
    }

    public void openDatePicker() {
        try {
            waits().clickable(whenOpen).click();
        } catch (Exception e) {
            try {
                waits().clickable(checkInBtn).click();
            } catch (Exception ignored) {}
        }
    }

    public void selectDateRange(String month1, String day1, String month2, String day2) {
        DatePickerUtils.selectDateLoop(driver, wait, month1, day1);
        DatePickerUtils.selectDateLoop(driver, wait, month2, day2);
    }

    // keep behavior identical to original script: open Who and click plus 3 times
    public void setAdultsViaThreeClicks() {
        waits().clickable(whoButton).click();
        for (int i = 0; i < 3; i++) {
            waits().clickable(adultPlusSpan).click();
        }
    }

    public void clickSearch() {
        waits().clickable(searchBtn).click();
    }
}
