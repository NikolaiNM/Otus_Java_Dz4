package factory;

import config.Mode;
import exceptions.BrowserNotFoundException;
import factory.impl.ChromeSettings;
import factory.impl.FirefoxSettings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.Browser;

public class WebDriverFactory {

    private String browserName = System.getProperty("browser");
    private static final Logger logger = LogManager.getLogger(WebDriverFactory.class);


    public WebDriver getDriver(Mode mode) {
        logger.info("Запуск браузера: {}", browserName);
        switch (browserName.toLowerCase()) {
            case "chrome": {
                logger.info("Режим chrome: {}", mode);
                ChromeSettings settings = new ChromeSettings(mode);

                // Приведение типов для использования ChromeOptions
                AbstractDriverOptions options = settings.setting();
                if (options instanceof ChromeOptions) {
                    return new ChromeDriver((ChromeOptions) options);  // Приведение типов
                } else {
                    throw new BrowserNotFoundException("Неизвестный режим Chrome");
                }

                //return new ChromeDriver((ChromeOptions) new ChromeSettings().setting());
            }
            case "firefox": {
                logger.info("Режим firefox: {}", mode);
                FirefoxSettings settings = new FirefoxSettings(mode); // Используйте FirefoxSettings

                // Получение настроек для Firefox
                AbstractDriverOptions options = settings.setting();
                if (options instanceof FirefoxOptions) { // Убедитесь, что это FirefoxOptions
                    return new FirefoxDriver((FirefoxOptions) options);
                } else {
                    throw new BrowserNotFoundException("Неизвестный режим Firefox");
                }
            }
        }
        throw new BrowserNotFoundException(browserName);
    }
}
