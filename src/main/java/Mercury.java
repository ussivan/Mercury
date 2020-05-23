import org.openqa.selenium.By;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import javax.security.auth.login.LoginException;
import java.util.concurrent.TimeUnit;

public class Mercury {
    private static FirefoxDriver driver;
    private static final String accLogin = "uss_dc_180622";
    private static final String accPasswd = "Rtgrf001";

    public static void clickButton(String xpath) throws InterruptedException {
        try {
            TimeUnit.SECONDS.sleep(1);
            WebElement button = driver.findElement(By.xpath(xpath));
            button.click();
        } catch (Exception e) {
            TimeUnit.SECONDS.sleep(1);
            WebElement button = driver.findElement(By.xpath(xpath));
            button.click();
        }
    }

    public static void login() throws InterruptedException {
        WebElement login = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/center[1]/div/form/div[1]/input"));
        login.sendKeys(accLogin);
        WebElement password = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/center[1]/div/form/div[2]/input"));
        password.sendKeys(accPasswd);
        clickButton("/html/body/div[2]/div/div[2]/center[1]/div/form/div[5]/button");
    }

    public static void doStuff() throws InterruptedException {
        clickButton("/html/body/div[1]/div/div[3]/form/table/tbody/tr[4]/td/div/button[1]");
        while(true) {
            try {
                clickButton("/html/body/div[1]/div/div[1]/div[2]/div[3]/ul/li[7]/a");
                clickButton("/html/body/div[1]/div/div[3]/table/tbody/tr/td[3]/ul/li/ul/li[1]/a");
                clickButton("/html/body/div[1]/div/div[3]/form/div[2]/table/tbody/tr[2]/td[8]/a/img");
                for(int i = 13; i < 20; i++) {
                    try {
                        clickButton("/html/body/div[1]/div/div[3]/form/table/tbody/tr[" + i + "]/td/div/button[1]");
                        break;
                    } catch (Exception ignored) {}
                }
                clickButton("/html/body/div[1]/div/div[3]/form/table/tbody/tr[4]/td/div/button[1]");
            } catch (Exception e) {
                break;
            }
        }
    }

    public static void main(String[] args) throws LoginException, InterruptedException {
        driver = new FirefoxDriver();
        try {
            driver.get("http://mercury.vetrf.ru/hs/");
            System.out.println("done");
            TimeUnit.SECONDS.sleep(5);
            login();
        }  catch (Exception ignored) {}
        clickButton("/html/body/div[1]/div/div[3]/form/table/tbody/tr[2]/td[2]/label");
        doStuff();
        clickButton("/html/body/div[1]/div/div[1]/div[1]/div[2]/ul/li[4]/a");
        clickButton("/html/body/div[1]/div/div[3]/form/table/tbody/tr[3]/td[2]/label");
        doStuff();
    }
}
