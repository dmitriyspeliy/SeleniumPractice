package steps;

import helpers.ScreenShoter;
import io.qameta.allure.Step;
import manager.Manager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pages.YandexMarket;
import pages.YandexSearch;


import java.util.List;
import java.util.Map;

public class Steps {

    @Step("Проверка наличия имени иконки, если есть - кликает на нее: {name}")
    public static void checkContainsName(Map<String,Object> resultSearch, String name, YandexSearch object){

       if(resultSearch
               .keySet()
               .stream()
               .noneMatch(x->x.contains(name)))
        {
            ScreenShoter.getScreen(Manager.getCurrentDriver(),object.getIcons());
            Assertions.fail("Иконка " +name+ " не найдена");
        }
       else {
           WebElement element = (WebElement) resultSearch.get(resultSearch.keySet().stream().filter(x->x.contains(name)).findFirst().get());
           element.click();
       }
    }


    @Step("Проверка на наличие наименования раздела, который указали {selectionName}")
    public static void checkSectionName(String selectionName, List<WebElement> allSection, WebDriver driver, WebElement element){
        if(allSection.stream().noneMatch(x->x.getText().contains(selectionName))){
            ScreenShoter.getScreen(driver,element);
            Assertions.fail("Раздела с названием " +selectionName+ " нет");
        }
    }

    @Step("Проверяем сколько элементов")
    public static void checkCountOfItems(String selectorCountOfItems, WebDriver driver){
        List<WebElement> listOfElements = driver.findElements(By.xpath(selectorCountOfItems));
        if(listOfElements.size()==12){
            ScreenShoter.getScreen(driver);
            Assertions.fail("Количество не равно 12, а равно "+listOfElements.size());
        }
    }

    @Step("Проверка название первого элемента и после поиска")
    public static void checkNameOfItems(YandexMarket object){
        Assertions.assertEquals(object.getNameOfFirst(), object.getNameOfFirstAfterSearch(), "Названия разные");
    }


}
