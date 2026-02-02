//package utils;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//public class WaitUtils {
//    private final WebDriver driver;
//    private final WebDriverWait wait;
//
//    public WaitUtils(WebDriver driver, WebDriverWait wait) {
//        this.driver = driver;
//        this.wait  = wait;
//    }
//
//    public WebElement clickable(By by) {
//        return wait.until(ExpectedConditions.elementToBeClickable(by));
//    }
//
//    public WebElement visible(By by) {
//        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
//    }
//
//    public void presence(By by) {
//        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
//    }
//
//    public void staleness(WebElement ele) {
//        wait.until(ExpectedConditions.stalenessOf(ele));
//    }
//
//    public static void waitForCountToStabilize(WebDriver driver, By by, Duration timeout) {
//        long end = System.currentTimeMillis() + timeout.toMillis();
//        int lastCount = -1;
//        int stableTicks = 0;
//
//        while (System.currentTimeMillis() < end) {
//            int count = driver.findElements(by).size();
//            if (count == lastCount) {
//                stableTicks++;
//                if (stableTicks >= 3) return; // stable for ~3 polls
//            } else {
//                stableTicks = 0;
//            }
//            lastCount = count;
//            try { Thread.sleep(250); } catch (InterruptedException ignored) {}
//        }
//    }
//}
//


package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait  = wait;
    }

    public WebElement clickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    // ✅ NEW: overload for WebElement
    public WebElement clickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement visible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void presence(By by) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public void staleness(WebElement ele) {
        wait.until(ExpectedConditions.stalenessOf(ele));
    }

    public static void waitForCountToStabilize(WebDriver driver, By by, Duration timeout) {
        long end = System.currentTimeMillis() + timeout.toMillis();
        int lastCount = -1;
        int stableTicks = 0;

        while (System.currentTimeMillis() < end) {
            int count = driver.findElements(by).size();
            if (count == lastCount) {
                stableTicks++;
                if (stableTicks >= 3) return; // stable for ~3 polls
            } else {
                stableTicks = 0;
            }
            lastCount = count;
            try { Thread.sleep(250); } catch (InterruptedException ignored) {}
        }
    }
}
