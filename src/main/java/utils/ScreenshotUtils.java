package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotUtils {

    public static File capture(WebDriver driver, String dir, String filename) throws Exception {
        Files.createDirectories(Path.of(dir));
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(dir + File.separator + filename);
        FileUtils.copyFile(src, dest);
        return dest;
    }
}
