import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import org.eclipse.jetty.util.Predicate;
//import com.google.common.base.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.WebElement;




public class SeleniumDemo {
    public static void main(String[] args) throws InterruptedException {
        //Step 1. Создаем драйвер Chrome. Открываем страницу
        String property = System.getProperty("user.dir") + "/driver/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", property);

        final EventFiringWebDriver driver = new EventFiringWebDriver(new ChromeDriver());
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        EventHandler eventHandler = new EventHandler();
        driver.register(eventHandler);

        driver.manage().window().maximize();
        driver.get("http://www.bing.com/");

        // Step 2. Переход на вкладку "Изображение". Ожидание title страницы "Лента изображений Bing"
        WebElement queryInput = driver.findElement(By.xpath("//*[@id='scpl1']"));

        Actions builder = new Actions(driver);
        builder.click(queryInput).build().perform();

        System.out.println("Page title is: " + driver.getTitle());
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.titleContains("Лента изображений Bing"));

        // Step 3. Scroll page
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0, 250)", ""); // скролл на 250 px

        // ожидание картинок class "ming" - перезагрузка страницы
        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return ((JavascriptExecutor)webdriver).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });

        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)"); // скролл до конца страницы

        // ожидание картинок class "ming" - перезагрузка страницы
        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return ((JavascriptExecutor)webdriver).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });


        // Step 4. В поисковую строку ввести слово без последней буквы
        WebElement search = driver.findElement(By.className("b_searchbox"));
        search.sendKeys("automatio");
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.textToBePresentInElementLocated(By.className("sa_tm"), "automation")); // ожидание совпадение результата

       // WebElement automation = driver.findElement(By.cssSelector("#sa_ul > li:nth-child(3)")); // выбор пункта "automation"

        WebElement automation = driver.findElement(By.xpath("//*[@id='sa_ul']/li/div[contains(text(), 'automatio')]/strong[text()='n']"));
        //(new WebDriverWait(driver, 50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='sa_ul']/li")));
        driver.findElement(By.cssSelector("#sa_ul > li:nth-child(3)")).click(); // выбор пункта "automation"


        // (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='sa_ul']/li")));
        //driver.findElement(By.xpath("//*[@id='sa_ul']/li/div[contains(text(), 'automatio')]/strong[text()='n']/parent::strong/parent::div")).click();

        (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.className("dg_u"))); // ожидание результатов поиска

        // Step 5. Установить фильтр Даты "Прошлый месяц"
        WebElement dataTime = driver.findElement(By.cssSelector("#ftrB > ul > li:nth-child(6)"));
        dataTime.click();

        (new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ftrB > ul > li:nth-child(6) > div > div > a:nth-child(4)"))); // ожидание открытия выпадающего списка

        WebElement lastMonth = driver.findElement(By.cssSelector("#ftrB > ul > li:nth-child(6) > div > div > a:nth-child(4)"));
        lastMonth.click();

        (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.className("dg_u"))); // ожидание результатов поиска

        // Step 6. Нажать на первое изображение из результатов поиска. Дождаться перехода в режим слайд шоу
        WebElement firstImg = driver.findElement(By.cssSelector(".dg_u img"));
        firstImg.click();

         (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return ((JavascriptExecutor)webdriver).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });

        //Step 7
        String title = driver.getTitle();

        By iframe = By.id("OverlayIFrame");
        final By slider = By.id("iol_ip");
        // начинаем с основного фрейма (открытой страницы)
        driver.switchTo().defaultContent();
        // переключаемся во фрейм
        driver.switchTo().frame(driver.findElement(iframe));

        WebDriverWait waitright = new WebDriverWait(driver, 30);
        waitright.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#iol_navr")));
       driver.findElement(By.cssSelector("#iol_navr")).click();

        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return driver.findElement(slider).isDisplayed();
            }
        });

        WebDriverWait waitleft = new WebDriverWait(driver, 30);
        waitleft.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#iol_navl")));
        driver.findElement(By.cssSelector("#iol_navl")).click();

        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return driver.findElement(slider).isDisplayed();
            }
        });

        //Step 8
       WebElement bigImg = driver.findElement(By.cssSelector("#iol_imw > div.iol_imc.valign_cont.trans > span > span > img"));
        bigImg.click();

        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return ((JavascriptExecutor)webdriver).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });

        String titleImg = driver.getTitle();

        if (titleImg.compareTo(title) != 0) {
            System.out.println("Img opened on the same page!");
        } else{
            System.out.println("Img opened on the new page!");
        }

        //Закрываем браузер
        driver.quit();
    }

}
