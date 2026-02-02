package base;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.PoiExcelLogger;
import utils.ScreenshotUtils;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

public abstract class BaseTest {

    protected WebDriver driver;
    protected PoiExcelLogger logger;

    protected String sheetHome = ConfigReader.get("excel.sheet.home", "Stays_Home");
    protected String sheetExp  = ConfigReader.get("excel.sheet.experiences", "Experiences");
    protected String sheetSvc  = ConfigReader.get("excel.sheet.services", "Services");

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() throws Exception {
        // Ensure artifact dirs exist
        Files.createDirectories(Path.of(ConfigReader.get("screenshots.dir", "screenshots")));
        Files.createDirectories(Path.of(ConfigReader.get("excel.output.dir", "excel-reports")));
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        String browser = ConfigReader.get("browser", "chrome");
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless", "false"));

        driver = DriverFactory.createInstance(browser, headless);
        driver.manage().window().maximize();

        int implicit = Integer.parseInt(ConfigReader.get("implicit.wait.seconds", "10"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicit));

        int pageLoad = Integer.parseInt(ConfigReader.get("page.load.timeout.seconds", "30"));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoad));

        driver.get(ConfigReader.get("base.url"));

        // Excel logger (single-file per test)
        String excelDir = ConfigReader.get("excel.output.dir", "excel-reports");
        String prefix   = ConfigReader.get("excel.workbook.prefix", "Airbnb_Run");
        boolean autoFlush = Boolean.parseBoolean(ConfigReader.get("excel.auto.flush", "true"));
        logger = new PoiExcelLogger(excelDir, prefix, autoFlush);

        // Initial log
        System.out.println("=== Test Started: " + method.getName() + " ===");
        logger.log(sheetHome, method.getName(), "INIT", "Setup", "INFO", "Test started");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result, Method method) {
        try {
            if (!result.isSuccess()) {
                String screenshotsDir = ConfigReader.get("screenshots.dir", "screenshots");
                String file = method.getName() + "_FAILED.png";
                ScreenshotUtils.capture(driver, screenshotsDir, file);
                System.out.println("Saved failure screenshot: " + screenshotsDir + "/" + file);
            }
        } catch (Exception e) {
            System.err.println("Error during teardown screenshot: " + e.getMessage());
        } finally {
            try {
                if (logger != null) {
                    logger.log(sheetHome, method.getName(), "TEARDOWN", "End", result.isSuccess() ? "INFO" : "ERROR",
                            "Test finished with status: " + (result.isSuccess() ? "PASS" : "FAIL"));
                    logger.flush();
                    logger.close();
                }
            } catch (Exception ignored) {}
            if (driver != null) {
                driver.quit();
            }
            System.out.println("=== Test Finished: " + method.getName() + " ===");
        }
    }

    // Convenience logging helpers
    protected void xlInfo(String sheet, String testName, String scenario, String step, String msg) {
        logger.log(sheet, testName, scenario, step, "INFO", msg);
    }

    protected void xlWarn(String sheet, String testName, String scenario, String step, String msg) {
        logger.log(sheet, testName, scenario, step, "WARN", msg);
    }

    protected void xlError(String sheet, String testName, String scenario, String step, String msg) {
        logger.log(sheet, testName, scenario, step, "ERROR", msg);
    }
}