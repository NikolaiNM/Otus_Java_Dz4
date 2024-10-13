package factory;

import config.Mode;
import exceptions.BrowserNotFoundException;
import factory.impl.ChromeSettings;
import factory.impl.FirefoxSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

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
                    return new ChromeDriver((ChromeOptions) options);
                } else {
                    throw new BrowserNotFoundException("Неизвестный режим Chrome");
                }
            }

            case "firefox": {
                logger.info("Режим firefox: {}", mode);
                FirefoxSettings settings = new FirefoxSettings(mode);

                // Получение настроек
                AbstractDriverOptions options = settings.setting();
                if (options instanceof FirefoxOptions) {
                    return new FirefoxDriver((FirefoxOptions) options);
                } else {
                    throw new BrowserNotFoundException("Неизвестный режим Firefox");
                }
            }
        }
        throw new BrowserNotFoundException(browserName);
    }
}
