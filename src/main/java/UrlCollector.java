import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Driver;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//сбор/обновление ссылок с сайта
public class UrlCollector {
    private WebDriver webDriver;
    private WebDriver googleDriver;
    private WebDriver zenEditorDriver;
    private String googleLink;
    private String zenEditorLink;

    public UrlCollector() {
        webDriver = new FirefoxDriver();
        googleDriver = new FirefoxDriver();
        zenEditorDriver = new FirefoxDriver();
        googleLink = "https://translate.google.com/?hl=ru#view=home&op=translate&sl=ru&tl=en";
        zenEditorLink = "https://zen.yandex.ru/profile/editor/id/5f0b260a7bcdb04f18498795";
    }

    public void tmp() throws IOException {
        Path fileLinks = Paths.get("links.txt");
        Set<String> oldLinks = new HashSet<>(Files.readAllLines(fileLinks));
        webDriver.get("https://zen.yandex.ru");
        Set<String> uniqueLinks = webDriver.findElements(By.cssSelector(".card-image-view__clickable"))
                .stream()
                .map(element -> element.getAttribute("href"))
                .filter(link -> link.contains("zen.yandex.ru"))
                .filter(link -> !oldLinks.contains(link))
                .collect(Collectors.toSet());
        Files.write(fileLinks, uniqueLinks, StandardOpenOption.APPEND);

        oldLinks.addAll(uniqueLinks);

        Path result = Paths.get("result.txt");
        if (!Files.exists(result)) {
            Files.createFile(result);
        }

        oldLinks.forEach(link -> {
            webDriver.get(link);
            String text = webDriver.findElement(By.cssSelector(".article__title")).getText();
            text += "\n\n\n" + webDriver.findElements(By.cssSelector(".article-render__block"))
                    .stream()
                    .filter(element -> {
                        try {
                            element.findElement(By.tagName("span"));
                            return true;
                        } catch (NoSuchElementException e) {
                            return false;
                        }
                    })
                    .map(element -> {
                        String body = element.getText();
                        if (body == null) {
                            body = element.findElement(By.tagName("b")).getText();
                        }
                        return body;
                    }).collect(Collectors.joining("\n"));
            googleDriver.get(googleLink);
            googleDriver.findElement(By.id("source")).sendKeys(text);
            googleDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            try {
                String translation = googleDriver.findElement(By.cssSelector(".tlid-translation"))
                        .getText();
                translation += "\n----------------------------------\n";
                Files.write(result, translation.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException | NoSuchElementException e) {
                e.printStackTrace();
            }
        });

        WebDriver zenEditorDriver = new FirefoxDriver();
        zenEditorDriver.get("https://zen.yandex.ru/");
        zenEditorDriver.findElements(By.cssSelector(".auth-header-buttons-view__right-link")).get(1).click();
        zenEditorDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        zenEditorDriver.findElement(By.id("passp-field-login")).sendKeys("maralevti");
        zenEditorDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        zenEditorDriver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div[3]/div[2]/div/div/div[1]/form/div[3]/button")).click();
        zenEditorDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        zenEditorDriver.findElement(By.id("passp-field-passwd")).sendKeys("qwerty007");
        zenEditorDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        zenEditorDriver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div[3]/div[2]/div/div/form/div[3]/button")).click();
        zenEditorDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        zenEditorDriver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div/div/div/nav/div[3]/div[2]/button")).click();
        zenEditorDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        zenEditorDriver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div/div/div/nav/div[3]/div[3]/div/div[2]/a[2]")).click();
        zenEditorDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        zenEditorDriver.findElement(By.cssSelector(".new-publication-dropdown__add-button")).click();
        zenEditorDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        zenEditorDriver.findElements(By.cssSelector(".new-publication-dropdown__button")).get(0).click();
        try {
            zenEditorDriver.findElement(By.cssSelector(".close-cross")).click();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        webDriver.quit();
        googleDriver.quit();
    }

    //System.setProperty("webdriver.gecko.driver", "C:\\Users\\dasha\\Downloads\\geckodriver-v0.26.0-win64\\geckodriver.exe");
//    driver.findElement(By.cssSelector(".form-control-lg")).click();
//    /html/body/div[2]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[5]/div[1]/div[1]/div/div/div/a
//    /html/body/div[2]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[5]/div[1]/div[2]/div/div/div/a
//    /html/body/div[2]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[5]/div[1]/div[3]/div/div/div/a
//    /html/body/div[2]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[5]/div[2]/div[2]/div/div/div/a

}
