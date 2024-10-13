package otus;

import config.Mode;
import factory.WebDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class FullScreenModeTests {

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
        driver = factory.getDriver(Mode.FULL_SCREEN);
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
    public void thirdTest() {
        WebElement elementName = driver.findElement(By.cssSelector("#name"));
        elementName.sendKeys("Nick");
        WebElement elementEmail = driver.findElement(By.cssSelector("#email"));
        elementEmail.sendKeys("Test@mail.ru");
        WebElement sendForm = driver.findElement(By.cssSelector("button[type='submit']"));
        sendForm.click();

        // Создаем явное ожидание
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement messageBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#messageBox")));
        String messageText = messageBox.getText();
        // Формируем ожидаемое сообщение
        String expectedMessage = "Форма отправлена с именем: " + elementName.getAttribute("value")
                + " и email: " + elementEmail.getAttribute("value");

        // Проверяем, что сообщение соответствует ожидаемому
        assertTrue(messageText.contains(expectedMessage),"Сообщение не соответствует ожидаемому: "
                + messageText);
    }
}
