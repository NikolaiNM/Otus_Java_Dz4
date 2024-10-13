package factory.impl;

import config.Mode;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class FirefoxSettings implements IWebDriverSettings {

    private final Mode mode;

    public FirefoxSettings(Mode mode) {
        this.mode = mode;
    }

    @Override
    public AbstractDriverOptions<FirefoxOptions> setting() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        if (mode != null && mode.getArgument() != null) {
            firefoxOptions.addArguments(mode.getArgument());
        }

        return firefoxOptions;
    }
}
