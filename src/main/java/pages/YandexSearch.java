package pages;

import manager.Manager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Dmitriy Pospelov
 * Task 1.3
 * Класс инициализирует driver, проверяет все иконки главной страницы и переходит на нужным нам раздел,
 * в данном случае Яндекс.Маркет
 */
public class YandexSearch {
    /**
     * Локаторы для поиска элементов, переменная driver, лист на всех иконок
     */
    protected WebDriver driver = Manager.getCurrentDriver();
    private String selectorAllIcons = "//ul[@class='services-new__more-popup-services']/li";
    private String selectorForAnyIcon = "./a";
    private String selectorButtonOpenIcons = "//a[@href='https://yandex.ru/all']";
    private List<WebElement> icons;
    /**
     * Конструктор, который содержит текущию страницу и переключает на следующие окно
     */
    public YandexSearch() {
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        for (String tab : tabs) {
            driver.switchTo().window(tab);
        }
    }

    /**
     * Метод, который назначнает начальную страницу, и заполняет лист с иконками
     */
    public void init() {
        driver.get("https://yandex.ru");
        WebElement buttonOpenIcons = driver.findElement(By.xpath(selectorButtonOpenIcons));
        buttonOpenIcons.click();
        icons = driver.findElements(By.xpath(selectorAllIcons));
    }

    /**
     * @return мапу, где ключ - навазние иконки, значение - ссылка для перехода
     */
    public Map<String, Object> getCollectIcons() {
        return icons.stream()
                .collect(Collectors.toMap(WebElement::getText,
                        x -> x.findElement(By.xpath(selectorForAnyIcon))));
    }

    /**
     * @return меню дял скрина
     * @see steps.Steps#checkContainsName(Map, String, YandexSearch) 
     */
    public WebElement getIcons(){
        return driver.findElement(By.xpath("//div[@class='services-new__more-popup-content']"));
    }

}
