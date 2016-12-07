import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeleniumDemo {
    public static void main(String[] args) {
        //Step 1. Создаем драйвер Chrome. Открываем страницу
        String property = System.getProperty("user.dir") + "/driver/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", property);

        EventFiringWebDriver driver = new EventFiringWebDriver(new ChromeDriver());
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
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.className("mimg"))); // ожидание картинок class "ming"
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)"); // скролл до конца страницы
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.className("mimg")));  // ожидание картинок class "ming"

        // Step 4. В поисковую строку ввести слово без последней буквы
        WebElement search = driver.findElement(By.className("b_searchbox"));
        search.sendKeys("automatio");
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.textToBePresentInElementLocated(By.className("sa_tm"), "automation")); // ожидание совпадение результата

        WebElement automation = driver.findElement(By.cssSelector("#sa_ul > li:nth-child(5)")); // выбор пункта "automation"
        automation.click();

        (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.className("dg_u"))); // ожидание результатов поиска

        // Step 5. Установить фильтр Даты "Прошлый месяц"
        WebElement dataTime = driver.findElement(By.cssSelector("#ftrB > ul > li:nth-child(6)"));
        dataTime.click();

        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ftrB > ul > li:nth-child(6) > div > div > a:nth-child(4)"))); // ожидание открытия выпадающего списка

        WebElement lastMonth = driver.findElement(By.cssSelector("#ftrB > ul > li:nth-child(6) > div > div > a:nth-child(4)"));
        lastMonth.click();

        (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.className("dg_u"))); // ожидание результатов поиска

        // Step 6. Нажать на первое изображение из результатов поиска. Дождаться перехода в режим слайд шоу
        WebElement firstImg = driver.findElement(By.cssSelector("#dg_c > div > div > div:nth-child(1) > div > a > img"));
        firstImg.click();

        (new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(By.className("dismissible"))); // ожидание режима слайд-шоу

        //Step 7
        String title = driver.getTitle();
        WebElement next = driver.findElement(By.cssSelector("#iol_fsc > div:nth-child(1) > a:nth-child(2)"));
        next.click();
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.className("iol_fsc"))); // ожидание очереди картинок

        WebElement prev = driver.findElement(By.cssSelector("#iol_fsc > div:nth-child(1) > a:nth-child(1)"));
        prev.click();
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.className("iol_fsc"))); // ожидание очереди картинок

        //Step 8
        WebElement bigImg = driver.findElement(By.cssSelector("#iol_imw > div.iol_imc.valign_cont.trans > span > span > img"));
        bigImg.click();

        String titleImg = driver.getTitle();

        if (titleImg.compareTo(title) == 0) {
            System.out.println("Img opened on the same page!");
        } else{
            System.out.println("Img opened on the new page!");
        }

        //Закрываем браузер
        driver.quit();
    }
}
