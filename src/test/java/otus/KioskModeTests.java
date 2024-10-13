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

public class KioskModeTests {

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
        driver = factory.getDriver(Mode.KIOSK);
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
    public void secondTest() {

        // Нажимаем на кнопку для открытия модального окна
        WebElement openModalBtn = driver.findElement(By.cssSelector("#openModalBtn"));

        // Проверка открыта ли модальное окно
        WebElement modalWindow = driver.findElement(By.cssSelector("#myModal"));
        Assertions.assertFalse(modalWindow.isDisplayed(), "Модальное окно уже открыто");

        openModalBtn.click();

        // Ждем, пока элемент станет доступным
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement modalHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#myModal h2")));

        // Проверяем текст элемента
        Assertions.assertEquals("Это модальное окно", modalHeader.getText());
    }
}
