import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SelenuimDemo {
    public static void main(String[] args) {
        //Создаем драйвер Firefox. Открываем страницу
        WebDriver driver = new FirefoxDriver();

        String property = System.getProperty("driver.path");
        System.setProperty("webdrive.gecko.driver", property);
        WebDriver.Options manage = driver.manage();
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

        List<WebElement> b_algo = driver.findElements(By.className("b_algo"));
        System.out.println("List with title of all results of search: " + b_algo);

        //Закрываем страницу
        driver.close();

        //Закрываем браузер
        driver.quit();
    }
}
