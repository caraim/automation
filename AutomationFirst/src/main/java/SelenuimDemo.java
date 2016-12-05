import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static javax.swing.text.html.CSS.getAttribute;

public class SelenuimDemo {
    public static void main(String[] args) {
        //Создаем драйвер Firefox. Открываем страницу
        //String property = System.getProperty("user.dir") + "/driver/geckodriver.exe";
        //System.setProperty("webdriver.gecko.driver", property);
        //WebDriver driver = new FirefoxDriver();

        //Создаем драйвер Chrome. Открываем страницу
        String property = System.getProperty("user.dir") + "/driver/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", property);

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("http://www.bing.com/");
        //driver.navigate().to("http://www.bing.com");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement go = driver.findElement(By.name("go"));
        wait.until(ExpectedConditions.elementToBeClickable(go));

        //Вводим поисковый запрос
        WebElement queryInput = driver.findElement(By.name("q"));
        queryInput.sendKeys("automation");
        queryInput.submit();

        //Получаем заголовок страницы результатов
        System.out.println("Page title is: " + driver.getTitle());

        //Получаем заголовки на странице поиска запроса
        /*System.out.println("List with title of all results of search:");
        List<WebElement> b_title = driver.findElements(By.className("b_title"));
        Iterator<WebElement> itr = b_title.iterator();
        while(itr.hasNext()) {
            //System.out.println(itr.next().getText());
            String text = itr.next().getText();
            String lines[] = text.split("\\n");
            System.out.println(lines[0]);
        }*/

        List<WebElement> b_title = driver.findElements(By.className("b_title"));
        for(WebElement element:b_title)
        {
            System.out.println(element.findElement(By.tagName("h2")).getText());
        }

        //Закрываем страницу
        //driver.close();

        //Закрываем браузер
        driver.quit();
    }
}
