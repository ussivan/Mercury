import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import javax.security.auth.login.LoginException;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {


    private static FirefoxDriver driver;
    private static JDA jda;
    private static HashSet<String> sentMessages;
    private static final String accLogin = "shirogiro";
    private static final String accPasswd = "chertopoloh";
    private static final String TOKEN = "NjQxMDk2MjgzODg4NjgwOTgw.XcDZdQ.oVruYxUdd_FwsjFwoJHkrXnobeU";

    public static void clickButton(String xpath) {
        WebElement button = driver.findElement(By.xpath(xpath));
        button.click();
    }
    public static void login() {
        WebElement login = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[1]/form/table/tbody/tr[1]/td[2]/input"));
        login.sendKeys(accLogin);
        WebElement password = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[1]/form/table/tbody/tr[2]/td[2]/input"));
        password.sendKeys(accPasswd);
        clickButton("//*[@id=\"s1\"]");
    }

    public static void sendMessage(String message) {
        System.out.println(jda.getStatus());
        for(net.dv8tion.jda.api.entities.TextChannel channel : jda.getTextChannels()) {
            System.out.println(channel.canTalk());
            if(channel.getName().equals("для-бота")) {
                channel.sendMessage(message).queue();
            } else {
                System.out.println(channel.getName());
            }
        }
        System.out.println(message);
    }


    //analysing single village for attacks
    public static void analyse(int x, int y) {
        clickButton("/html/body/div[3]/div[2]/div[1]/ul[1]/li[2]/a");
        clickButton("/html/body/div[3]/div[2]/div[2]/div[2]/div[2]/div[1]/div[1]/div/div[39]/div");
        WebElement num = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[2]/div[2]/div[1]/div[2]/div[4]/h4[1]"));
        String numText = num.getText();
        try {
            for (int i = 0; i < Math.min(20, Integer.parseInt(numText.substring(20, numText.length() - 1))); i++) {
                try {
                    WebElement cur = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[2]/div[2]/div[1]/div[2]/div[4]/table[" + (i + 1) + "]/thead/tr/td[2]/a[2]"));
                    String arr = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[2]/div[2]/div[1]/div[2]/div[4]/table[" + (i + 1) + "]/thead/tr/td[1]/a")).getText();
                    String time = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[2]/div[2]/div[1]/div[2]/div[4]/table[" + (i + 1) + "]/tbody[3]/tr/td/div[2]/span[1]")).getText();
                    String after = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[2]/div[2]/div[1]/div[2]/div[4]/table[" + (i + 1) + "]/tbody[3]/tr/td/div[1]/span")).getText();
                    String attackerXStr = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[2]" +
                            "/div[2]/div[1]/div[2]/div[4]/table[" + (i + 1) + "]/tbody[1]/tr/th/span/span[1]")).getText();
                    String attackerYStr = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[2]" +
                            "/div[2]/div[1]/div[2]/div[4]/table[" + (i + 1) + "]/tbody[1]/tr/th/span/span[3]")).getText();
                    int attackerX = parseInt(attackerXStr);
                    int attackerY = parseInt(attackerYStr);
                    double distance = Math.sqrt(Math.pow((double)attackerX - x, 2) + Math.pow(attackerY - y, 2));
                    if(sentMessages.contains(arr + cur.getText() + accLogin + time)) {
                        continue;
                    } else {
                        sentMessages.add(arr + cur.getText() + accLogin + time);
                    }
                    sendMessage("@here " + arr + "(" + attackerX + "|" + attackerY + ")" + " игрока " + cur.getText()
                            + "(" + x + "|" + y + ")" + " игрока " + accLogin + ",  прибывает через "
                            + after + " " + time + "\n со скоростью " + countSpeed(distance, after));
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        }
    }

    public static double countSpeed(double distance, String after) {
        String [] split = after.split(":");
        int h = Integer.parseInt(split[0]), m = Integer.parseInt(split[1]), s = Integer.parseInt(split[2]);
        double sumH = h + (double) m / 60 + (double) s / 60 / 60;
        return distance / sumH;
    }

    public static int parseInt(String toParse) {
        Pattern p = Pattern.compile("[^0-9-]");
        Matcher m = p.matcher(toParse);
        if(toParse.contains("−")) {
            return -Integer.parseInt(m.replaceAll(""));
        }
        return Integer.parseInt(m.replaceAll(""));
    }

    public static void main(String[] args) throws LoginException, InterruptedException {
        jda = new JDABuilder(TOKEN).build();
        sentMessages = new HashSet<String>();
        driver = new FirefoxDriver();
        try {
            driver.get("http://ts2.travian.ru/");
            login();
        } catch (Exception ignored) {}
        while(true) {
            try {
                for (int i = 0; i < 125; i++) {
                    try {
                        clickButton("/html/body/div[3]/div[2]/div[2]/div[3]/div[2]/div[2]/div[2]/ul/li[" + (i + 1) + "]/a/div");
                        String x = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[3]/div[2]/div[2]/div[2]/ul/li[" + (i + 1) + "]/a/span/span[1]")).getText();
                        String y = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[3]/div[2]/div[2]/div[2]/ul/li[" + (i + 1) + "]/a/span/span[3]")).getText();
                        analyse(parseInt(x), parseInt(y));
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
            } catch (Exception e) {
                driver.get("http://ts2.travian.ru/");
                login();
            }
            TimeUnit.MINUTES.sleep(1);
        }
    }
}