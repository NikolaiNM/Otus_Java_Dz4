package exceptions;

public class BrowserNotFoundException extends RuntimeException {

    public BrowserNotFoundException(String browserName) {
        super(String.format("Браузер %s не поддерживается", browserName));
    }
}
