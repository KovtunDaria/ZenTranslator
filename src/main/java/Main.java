import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* методы:
- сбор/обновление url с сайта (UrlCollector)
- открытие сайтов из файла с ссылками (UrlOpener)
- копирование текста с сайтов (TextCollector)
- вставка текста и копирование перевода (TextTraslator)
- вставка перевода в поле авторской статьи с проверкой на сохранение (ArticleCreator) */

public class Main {
    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\dasha\\Downloads\\geckodriver-v0.26.0-win64\\geckodriver.exe");
        UrlCollector urlCollector = new UrlCollector();
        urlCollector.tmp();
        urlCollector.stop();


    }
}
