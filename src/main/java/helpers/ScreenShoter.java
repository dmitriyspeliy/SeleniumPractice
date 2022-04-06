package helpers;

import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;


public class ScreenShoter {

    @Attachment
    public static byte[] getScreen(WebDriver driver, WebElement element){
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            return Files.toByteArray(screenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Attachment
    public static byte[] getScreen(WebDriver driver){
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            return Files.toByteArray(screenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
