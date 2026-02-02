package base;
import utils.ConfigReader;
import utils.ElementUtils;
import utils.WaitUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final JavascriptExecutor js;

    protected final int explicitSeconds;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.explicitSeconds = Integer.parseInt(ConfigReader.get("explicit.wait.seconds", "12"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitSeconds));
        this.js = (JavascriptExecutor) driver;
    }

    protected WaitUtils waits() {
        return new WaitUtils(driver, wait);
    }

    protected ElementUtils elems() {
        return new ElementUtils(driver, wait, js);
    }
}