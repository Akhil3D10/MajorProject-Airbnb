package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ExperienceDetailPage extends BasePage {

    private final By heading = By.xpath("//h1[@elementtiming='LCP-target']");
    private final By hostedBy = By.xpath("//div[contains(text(),'Hosted by')]");
    private final By location = By.xpath("(//div[@class='toa59ve atm_c8_cvmmj6 atm_g3_1obqfcl atm_fr_frkw1s atm_cs_ml5b3k atm_c8_1gcojkr__1v156lz atm_g3_15xinxl__1v156lz atm_fr_8r8w0r__1v156lz atm_cs_1mexzig__1v156lz atm_c8_cvmmj6__jx8car atm_g3_1obqfcl__jx8car atm_fr_frkw1s__jx8car atm_cs_ml5b3k__jx8car dir dir-ltr'])[2]");
    private final By whatYouDo = By.xpath("//div/button[contains(@class,'l1ovpqvx')]/div/div[2]/h3");
    private final By priceOpen = By.xpath("(//div[@class='d7gzrif atm_cs_1mexzig dir dir-ltr'])[1]");
    private final By totalInr = By.xpath("//div[text()='Total']/parent::div/following-sibling::div");

    public ExperienceDetailPage(WebDriver driver) {
        super(driver);
    }

    public String getHeading() {
        return waits().visible(heading).getText();
    }

    public String getHostedByLine() {
        return waits().visible(hostedBy).getText();
    }

    public String getLocation() {
        WebElement loc = waits().visible(location);
        return loc.getText();
    }

    public List<String> getWhatYouWillDoItems() {
        List<WebElement> items = driver.findElements(whatYouDo);
        List<String> out = new ArrayList<>();
        int i = 1;
        for (WebElement w : items) {
            out.add((i++) + ") " + w.getText());
        }
        return out;
    }

    public String getTotalINR() {
        WebElement el = waits().clickable(priceOpen);
        elems().scrollIntoViewCenter(el);
        el.click();
        return waits().visible(totalInr).getText();
    }
}
