package config;

import org.openqa.selenium.chrome.ChromeOptions;

public enum Mode {
    HEADLESS("--headless"),
    KIOSK("--kiosk"),
    FULL_SCREEN("--start-fullscreen");
    //DEFAULT(null);

    private final String argument;

    Mode(String argument) {
        this.argument = argument;
    }

    public String getArgument() {
        return argument;
    }
}
