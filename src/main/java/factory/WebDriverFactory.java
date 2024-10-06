package factory;

import exceptions.BrowserNotFoundException;
import factory.impl.ChromeSettings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

public class WebDriverFactory {

    private String browserName = System.getProperty("browser");

    public WebDriver getDriver() {
        switch (browserName) {
            case "chrome": {

                return new ChromeDriver((ChromeOptions) new ChromeSettings().setting());
            }
            case "firefox": {

                return new FirefoxDriver();
            }
        }
        throw new BrowserNotFoundException(browserName);
    }
}
