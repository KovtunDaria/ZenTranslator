import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\dasha\\Downloads\\geckodriver-v0.26.0-win64\\geckodriver.exe");
        Translator translator = new Translator();
        translator.translate();
        translator.stop();
    }
}
