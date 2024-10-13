package otus;

import config.Mode;
import factory.WebDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HeadlessModeTests {

    private WebDriver driver;
    private TestInfo testInfo;
    private static final Logger logger = LogManager.getLogger(FullScreenModeTests.class);
    private static final String TESTING_URL = System.getProperty("testing.url") + "/training.html";

    @BeforeAll
    public static void webDriverInstall(){
    }

    @BeforeEach
    public void webDriverStart(TestInfo testInfo) {
        this.testInfo = testInfo;
        logger.info("Запуск теста: " + testInfo.getDisplayName());

        WebDriverFactory factory = new WebDriverFactory();
        driver = factory.getDriver(Mode.HEADLESS);
        driver.get(TESTING_URL);
    }

    @AfterEach
    public void webDriverStop() {
        logger.info("Тест {} завершен.", testInfo.getDisplayName());
        if (driver != null) {
            driver.quit();
            driver = null;
            logger.info("Веб-драйвер закрыт.");
        }
    }

    @Test
    public void firstTest() {
        WebElement element = driver.findElement(By.cssSelector("#textInput"));
        element.sendKeys("ОТУС");
        Assertions.assertEquals("ОТУС", element.getAttribute("value"));
    }
}
