package pages;

import base.BasePage;
import utils.WindowUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.*;

public class ExperiencesResultsPage extends BasePage {

    private final By noMatchesMsg = By.xpath("//*[contains(text(),'No exact matches')]");

    private final By GFF = By.xpath("(//div[contains(@class,'c14whb16')])[1]//a/following-sibling::div/div[2]/div[1]");
    private final By LCH = By.xpath("(//div[contains(@class,'c14whb16')])[2]//a/following-sibling::div/div[2]/div[1]");
    private final By Explore = By.xpath("//div[contains(@class,'g16uu4ny ')]//a/following-sibling::div/div[2]/div[1]");

    public ExperiencesResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isNoExactMatches() {
        try {
            driver.findElement(noMatchesMsg);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private By resolveActiveBlock() {
        if (!driver.findElements(GFF).isEmpty() && driver.findElement(GFF).isDisplayed()) return GFF;
        if (!driver.findElements(LCH).isEmpty() && driver.findElement(LCH).isDisplayed()) return LCH;
        if (!driver.findElements(Explore).isEmpty() && driver.findElement(Explore).isDisplayed()) return Explore;
        throw new RuntimeException("No visible experience block found (GFF/LCH/Explore).");
    }

    public List<String> getVisibleExperienceTitles() {
        By activeBlock = resolveActiveBlock();
        List<WebElement> Etitles = waits().visible(activeBlock) != null
                ? driver.findElements(activeBlock)
                : Collections.emptyList();
        List<String> out = new ArrayList<>();
        for (WebElement e : Etitles) out.add(e.getText());
        return out;
    }

    public void openRandomExperienceInNewTab() {
        By activeBlock = resolveActiveBlock();
        List<WebElement> Etitles = driver.findElements(activeBlock);
        if (Etitles.isEmpty()) throw new RuntimeException("No experiences in active block.");

        Map<Integer, String> map = new HashMap<>();
        int i = 1;
        for (WebElement t : Etitles) {
            map.put(i++, t.getText());
        }

        int p = (int)(Math.random() * (i-1)) + 1;
        String title = map.get(p);

        WebElement fixplace = waits().clickable(By.xpath(
            ".//div[@data-testid='listing-card-title' and normalize-space()=\"" + title + "\"]/ancestor::div/a"
        ));

        elems().jsClick(fixplace);

        int oldCount = driver.getWindowHandles().size();
        WindowUtils.waitForNewWindow(driver, oldCount, Duration.ofSeconds(10));

        // Close current and switch
        WindowUtils.closeCurrentAndSwitchToNew(driver);
    }
}
