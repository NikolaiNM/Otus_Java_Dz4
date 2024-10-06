package otus;

import config.Config;
import config.Mode;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Dz4Tests {

    WebDriver driver;
    private static final Logger logger = LogManager.getLogger(Dz4Tests.class);
    private TestInfo testInfo;
    private Mode mode = Mode.DEFAULT;

    private void webDriverStart() {
        logger.info("Запуск веб-драйвера с режимом: {}", mode);
        ChromeOptions options = new ChromeOptions();
        if (mode.getArgument() != null) {
            options.addArguments(mode.getArgument());
        }
        driver = new ChromeDriver(options);
        driver.get(Config.getTestingUrl());
        logger.info("Веб-драйвер запущен и открыта страница.");
    }

    @BeforeAll
    public static void webDriverInstall() {
        logger.info("Старт прогона \n--------------------------------------------------------------------------");
        WebDriverManager.chromedriver().setup();
        logger.info("Установка WebDriver...");
    }

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        this.testInfo = testInfo;
        logger.info("Запуск теста: " + testInfo.getDisplayName());
    }

    @Test
    public void firstTest() {
        mode = Mode.HEADLESS;
        webDriverStart();
        WebElement element = driver.findElement(By.cssSelector("#textInput"));
        element.sendKeys("ОТУС");
        Assertions.assertEquals("ОТУС", element.getAttribute("value"));
    }

    @Test
    public void secondTest() {
        mode = Mode.KIOSK;
        webDriverStart();

        // Нажимаем на кнопку для открытия модального окна
        WebElement openModalBtn = driver.findElement(By.cssSelector("#openModalBtn"));
        openModalBtn.click();

        // Ждем, пока элемент <h2> станет доступным
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement modalHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#myModal h2")));

        // Проверяем текст элемента <h2>
        Assertions.assertEquals("Это модальное окно", modalHeader.getText());
    }

    @Test
    public void thirdTest() {
        mode = Mode.FULL_SCREEN;
        webDriverStart();
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

    @AfterEach
    public void webDriverStop() {
        logger.info("Тест {} завершен.", testInfo.getDisplayName());
        if (driver != null) {
            driver.quit();
            driver = null;
            logger.info("Веб-драйвер закрыт.");
        }
    }

    @AfterAll
    public static void endTesting() {
        logger.info("Завершение прогона \n--------------------------------------------------------------------------");
    }
}
