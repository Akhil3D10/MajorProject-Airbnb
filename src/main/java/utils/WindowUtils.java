package utils;

import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WindowUtils {

    public static void waitForNewWindow(WebDriver driver, int prevCount, Duration timeout) {
        long end = System.currentTimeMillis() + timeout.toMillis();
        while (System.currentTimeMillis() < end) {
            if (driver.getWindowHandles().size() > prevCount) return;
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        }
        throw new RuntimeException("Timed out waiting for new window");
    }

    public static void closeCurrentAndSwitchToNew(WebDriver driver) {
        String current = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        driver.close();
        for (String h : handles) {
            if (!h.equals(current)) {
                driver.switchTo().window(h);
                return;
            }
        }
        throw new RuntimeException("No other window to switch to");
    }

    public static void switchToWindowIndex(WebDriver driver, int index) {
        List<String> list = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(list.get(index));
    }
}
