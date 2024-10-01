import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestOtus {

    public enum Mode {
        HEADLESS("--headless"),
        KIOSK("--kiosk"),
        FULL_SCREEN("--start-fullscreen"),
        DEFAULT(null);

        private final String argument;

        Mode(String argument) {
            this.argument = argument;
        }

        public String getArgument() {
            return argument;
        }
    }

    private Mode mode = Mode.DEFAULT;
    WebDriver driver;

    @BeforeAll
    public static void webDriverInstall() {
        WebDriverManager.chromedriver().setup();
    }

//    @BeforeEach
//    public void setUp() {
//    }

    @Test
    public void firstTest() throws InterruptedException {
        mode = Mode.HEADLESS;
        webDriverStart();
        WebElement element = driver.findElement(By.cssSelector("#textInput"));
        element.sendKeys("ОТУС");
        Assertions.assertEquals("ОТУС", element.getAttribute("value"));
        //Thread.sleep(5000);
    }

    @Test
    public void secondTest() throws InterruptedException {
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
        //Thread.sleep(5000);
    }

    @Test
    public void thirdTest() throws InterruptedException {
        mode = Mode.FULL_SCREEN;
        webDriverStart();
        WebElement elementName = driver.findElement(By.cssSelector("#name"));
        elementName.sendKeys("Nick");
        WebElement elementEmail = driver.findElement(By.cssSelector("#email"));
        elementEmail.sendKeys("Test@mail.ru");
        WebElement sendForm = driver.findElement(By.cssSelector("button[type='submit']"));
        sendForm.click();
        //Thread.sleep(1000);
        // Создаем явное ожидание
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement messageBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#messageBox")));
        String messageText = messageBox.getText();
        // Формируем ожидаемое сообщение
        String expectedMessage = "Форма отправлена с именем: " + elementName.getAttribute("value") + " и email: " + elementEmail.getAttribute("value");

        // Проверяем, что сообщение соответствует ожидаемому
        assertTrue(messageText.contains(expectedMessage),
                "Сообщение не соответствует ожидаемому: " + messageText);
        //Thread.sleep(5000);
    }

    private void webDriverStart() {
        ChromeOptions options = new ChromeOptions();
        if (mode.getArgument() != null) {
            options.addArguments(mode.getArgument());
        }
        driver = new ChromeDriver(options);
        driver.get("https://otus.home.kartushin.su/training.html");
    }

    @AfterEach
    public void webDriverStop() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
}
//    @Test
//    public void windowTest() throws InterruptedException {
////        Группа А: Запустить тест в полном окне («не киоск»), получить его размер
//        driver.manage().window().maximize();
//        Dimension size = driver.manage().window().getSize();
//        System.out.println(size);
//        Thread.sleep(3000);
////        Группа Б: Запустить тест в расширении 800 на 600, получить его позицию
//        driver.manage().window().setSize(new Dimension(800, 600));
//        Thread.sleep(3000);
//        System.out.println(driver.manage().window().getPosition());
//
////        Группа В: Тоже, что группа Б + передвинуть браузер по квадрату (четырем точкам)
//        Point point = driver.manage().window().getPosition();
//        point.x += 100;
//        point.y += 0;
//        driver.manage().window().setPosition(point);
//        Thread.sleep(3000);
//
//        point.x += 0;
//        point.y += 100;
//        driver.manage().window().setPosition(point);
//        Thread.sleep(3000);
//
//        point.x -= 100;
//        point.y += 0;
//        driver.manage().window().setPosition(point);
//        Thread.sleep(3000);
//
//        point.x += 0;
//        point.y -= 100;
//        driver.manage().window().setPosition(point);
//        Thread.sleep(3000);
//    }

