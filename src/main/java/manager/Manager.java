package manager;

import helpers.Constants;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Manager {
    private static WebDriver currentDriver;

    public static WebDriver getCurrentDriver(){
        return currentDriver;
    }

    public static void initChrome(){
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments(List.of("start-maximized"));
        try {
            currentDriver = new ChromeDriver(options);
        }catch (SessionNotCreatedException ex){
            Assertions.fail("Данный драйвер не совметсим с текущим браузер. Используйте другой драйвер");
        }
        setDriverDefaultSettings();
    }

    private static void setDriverDefaultSettings(){
        currentDriver.manage().timeouts().implicitlyWait(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        currentDriver.manage().window().maximize();
        currentDriver.manage().deleteAllCookies();
    }

    public static void killCurrentDriver(){
        if(currentDriver!=null){
            currentDriver.quit();
            currentDriver = null;
        }
    }
}
