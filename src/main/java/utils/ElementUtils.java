package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtils {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    public ElementUtils(WebDriver driver, WebDriverWait wait, JavascriptExecutor js) {
        this.driver = driver;
        this.wait = wait;
        this.js = js;
    }

    public void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    public void scrollIntoViewCenter(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    public void scrollToMiddle() {
        js.executeScript("window.scrollTo(0,(document.body.scrollHeight/2));");
    }

    public void scrollToBottom() {
        js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
    }

    public void pressEscape() {
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }

    public void clearAndType(WebElement input, String value) {
        try {
            scrollIntoViewCenter(input);
            input.click();
            String selectAll = System.getProperty("os.name").toLowerCase().contains("mac")
                    ? Keys.chord(Keys.COMMAND, "a")
                    : Keys.chord(Keys.CONTROL, "a");
            input.sendKeys(selectAll);
            input.sendKeys(Keys.DELETE);

            String current = input.getAttribute("value");
            if (current != null && !current.isEmpty()) {
                input.sendKeys(selectAll);
                input.sendKeys(Keys.BACK_SPACE);
            }

            current = input.getAttribute("value");
            if (current != null && !current.isEmpty()) {
                js.executeScript("arguments[0].value=''; arguments[0].dispatchEvent(new Event('input',{bubbles:true}));", input);
            }

            input.sendKeys(value);
        } catch (Exception e) {
            js.executeScript(
                    "arguments[0].value=arguments[1]; arguments[0].dispatchEvent(new Event('input',{bubbles:true}));",
                    input, value
            );
        }
    }
}
