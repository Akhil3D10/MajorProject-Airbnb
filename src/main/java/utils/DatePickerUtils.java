package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DatePickerUtils {

    // === Variant 1: select by exact data-state--date-string (e.g., "May 2026") ===
    public static void selectDateByDataState(WebDriver driver, WebDriverWait wait, String dateString) {
        By path = By.xpath("//button[@data-state--date-string='" + dateString + "']");
        WebElement dateButton = wait.until(ExpectedConditions.elementToBeClickable(path));
        dateButton.click();
    }

    // === Variant 2: loop months until targetMonth then click targetDate (using 's1uax1lc' container) ===
    public static void selectDateLoop(WebDriver driver, String targetMonth, String targetDate) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Thread.sleep(3000);
        while (true) {
            WebElement monthHeader = driver.findElement(By.xpath("//h2[@class='h1d0twz0 atm_c8_exct8b atm_g3_gktfv atm_cs_1mexzig atm_7l_1dmvgf5 atm_vy_1osqo2v atm_vb_glywfm atm_h3_qslrf5 atm_gq_qslrf5 atm_r3_1h6ojuz dir dir-ltr']"));
            String currentMonth = monthHeader.getText();

            if (currentMonth.equalsIgnoreCase(targetMonth)) {
                try {
                    WebElement date1 = driver.findElement(By.xpath(
                        "//div[@class='mjfhmhj atm_9s_11p5wf0 atm_dz_1u8mbql atm_n5_fvkkyg atm_84_hms6az atm_vy_1osqo2v atm_l8_1w78mzc atm_ks_zryt35 atm_fb_1cl4t0h s1uax1lc atm_gw_w1gvyb dir dir-ltr']" +
                        "/button[contains(text(),'" + targetDate + "')]"
                    ));
                    date1.click();
                    break;
                } catch (StaleElementReferenceException e) {
                    continue;
                }
            } else {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "(//button[contains(@class,'l1ovpqvx atm_npmupv_14b5rvc_10sa') and @type='button' and contains(@aria-label,'Move forward')])"
                ))).click();

                try {
                    wait.until(ExpectedConditions.stalenessOf(monthHeader));
                } catch (Exception ignored) {}
            }
        }
    }

    // === Variant 3: loop months (using 'm1sxtgte' container) ===
    public static void selectDateLoop(WebDriver driver, WebDriverWait wait, String targetMonth, String targetDate) {
        while (true) {
            WebElement monthHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[@class='h1d0twz0 atm_c8_exct8b atm_g3_gktfv atm_cs_1mexzig atm_7l_1dmvgf5 atm_vy_1osqo2v atm_vb_glywfm atm_h3_qslrf5 atm_gq_qslrf5 atm_r3_1h6ojuz dir dir-ltr']")));
            String currentMonth = monthHeader.getText();

            if (currentMonth.equalsIgnoreCase(targetMonth)) {
                try {
//                    WebElement dateElement = driver.findElement(By.xpath(
//                        "//div[@class='mjfhmhj atm_9s_11p5wf0 atm_dz_1u8mbql atm_n5_fvkkyg atm_84_hms6az atm_vy_1osqo2v atm_l8_1w78mzc atm_ks_zryt35 atm_fb_1cl4t0h m1sxtgte atm_gw_d4y9na dir dir-ltr']/button[contains(text(),'" + targetDate + "')]"));
//                    dateElement.click();
                	WebElement dateElement = driver.findElement(By.xpath("//div/div[2]/div/div[4]/div/div/button[contains(text(),'"+ targetDate +"')]"));   
                	dateElement.click();
                	return;
                } catch (StaleElementReferenceException ignored) {}
            } else {
                WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//button[contains(@class,'l1ovpqvx atm_npmupv_14b5rvc_10sa') and @type='button' and contains(@aria-label, 'Move forward')])")));
                nextBtn.click();
                try {
                    wait.until(ExpectedConditions.stalenessOf(monthHeader));
                } catch (Exception ignored) {}
            }
        }
    }
}
