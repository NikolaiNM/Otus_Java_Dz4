package factory.impl;

import config.Mode;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class ChromeSettings implements IWebDriverSettings {

    private final Mode mode;

    // Конструктор принимает Mode
    public ChromeSettings(Mode mode) {
        this.mode = mode;
    }

    @Override
    public AbstractDriverOptions setting() {
        ChromeOptions chromeOptions = new ChromeOptions();

        // Добавляем аргумент в зависимости от переданного режима
        if (mode != null && mode.getArgument() != null) {
            chromeOptions.addArguments(mode.getArgument());
        }


        return chromeOptions;
    }
}


