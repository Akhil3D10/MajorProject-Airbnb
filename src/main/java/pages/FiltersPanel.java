package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FiltersPanel extends BasePage {

    private final By maxPrice = By.xpath("//input[@id='price_filter_max']");
    private final By applyDivAnchor = By.xpath("//div[@class='p1y54pk8 atm_7l_85zwdx dir dir-ltr']//a");
    private final By showBtn = By.xpath("//button[contains(.,'Show')]");
    private final By saveBtn = By.xpath("//button[contains(.,'Save')]");

    public FiltersPanel(WebDriver driver) {
        super(driver);
    }

    public void setMaxPrice(int value) {
        WebElement input = waits().visible(maxPrice);
        elems().clearAndType(input, String.valueOf(value));
    }

    public void apply() {
        try {
            waits().clickable(applyDivAnchor).click();
        } catch (Exception e) {
            try {
                waits().clickable(showBtn).click();
            } catch (Exception e2) {
                try {
                    waits().clickable(saveBtn).click();
                } catch (Exception ignored) {
                    elems().pressEscape(); // last resort; UI may auto-apply
                }
            }
        }
    }
}
