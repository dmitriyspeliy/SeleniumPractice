package pages;

import helpers.Constants;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import steps.Steps;

import java.util.List;

/**
 * Класс, который ищет на маркете нужным нам раздел, фильтрует элементы по цене, производителю,
 * проверят, что первый элемент после фильтрации будет найден в поисковике маркета
 */
public class YandexMarket extends YandexSearch {
    /**
     * Нужные локаторы для поиска элементов и переменные
     */
    private String selectorSectionButton = "//button[@id='catalogPopupButton']";
    private String selectorAllSection = "//ul[@class='_2OFlF']/li";
    private String selectorSubAllSelection = "//div[@class='_2V5Ge']//li";
    private String selectorOfPriceFrom = "//label[contains(text(),'от')]/../input";
    private String selectorOfPriceTo = "//label[contains(text(),'до')]/../input";
    private String selectorButtonOpenAll = "//legend[contains(text(),'Производитель')]/../footer/button";
    private String selectorFieldSearch = "//input[@id='7893318-suggester']";
    private String selectorOpenUl = "//ul[@data-tid='d509a945']//div";
    private String selectorButtonCountOfItems = "//button[@class='vLDMf']";
    private String selectorButton12Items = "//body//button[@class='_1KpjX _35Paz']";
    private String selectorCountOfItems = "//div[@class='cia-vs']//article";
    private String selectorMainSearch = "//input[@id='header-search']";
    private String selectorMainSearchButton = "//button[@data-r='search-button']";
    private String nameOfFirst;
    private String nameOfFirstAfterSearch;
    private WebDriverWait wait = new WebDriverWait(driver, Constants.DEFAULT_TIMEOUT_EX);

    /**
     * Метод, ищет нужные разделы, и проверяет, что такие разделы есть, либо ошибка
     * @see steps.Steps#checkSectionName(String, List, WebDriver, WebElement)
     * @param sectionName - название главного раздела
     * @param subSectionName - название подраздела
     */
    public void selectSection(String sectionName, String subSectionName) {
        WebElement buttonSection = driver.findElement(By.xpath(selectorSectionButton));
        buttonSection.click();
        Actions actions = new Actions(driver);
        List<WebElement> allSection = driver.findElements(By.xpath(selectorAllSection));
        Steps.checkSectionName(sectionName, allSection,driver,buttonSection);
        actions.moveToElement(allSection.stream().filter(x->x.getText().equals(sectionName)).findFirst().get()).perform();
        List<WebElement> subAllSection = driver.findElements(By.xpath(selectorSubAllSelection));
        Steps.checkSectionName(subSectionName, subAllSection,driver,buttonSection);
        subAllSection.stream().filter(x->x.getText().equals(subSectionName)).findFirst().get().click();
    }

    /**
     * Метод, который назначает конечную и начальную цену товара
     * @param fromPrice - начальная цена
     * @param toPrice - конечная цена
     */
    public void setParameterOfPrise(int fromPrice, int toPrice) {
        if(fromPrice>=toPrice){
            Assertions.fail("Цена \"от\" должна быть больше");
        }
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@aria-label='Результаты поиска']//article")));
        WebElement fromPriceElem = driver.findElement(By.xpath(selectorOfPriceFrom));
        WebElement toPriceElem = driver.findElement(By.xpath(selectorOfPriceTo));
        fromPriceElem.click();
        fromPriceElem.sendKeys(String.valueOf(fromPrice));
        toPriceElem.click();
        toPriceElem.sendKeys(String.valueOf(toPrice));
        wait.until(ExpectedConditions.attributeContains(By.xpath("//div[@class='_8v6CF']"),"className","_8v6CF"));
    }

    /**
     * Метод, который фильтрует поиск товара по производителям, можно передать несколько
     * Так же он устанавливает количетво отображаемых элементов на странице, в даннном случае по 12 штук
     * @param nameOfProducer - производители
     */
    public void selectedBoxOfProducer(String... nameOfProducer) {
        WebElement element = driver.findElement(By.xpath(selectorButtonOpenAll));
        element.click();
        WebElement element1 = driver.findElement(By.xpath(selectorFieldSearch));
        element1.click();
        for (String name : nameOfProducer) {
            element1.sendKeys(name);
            wait.until(ExpectedConditions.attributeContains(By.xpath("//div[@class='_8v6CF']"),"className","_8v6CF"));
            WebElement ulOpen = driver.findElement(By.xpath(selectorOpenUl));
            ulOpen.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-apiary-widget-name=\"@MarketNode/SearchResults\"]")));
            element1.clear();
        }
        wait.until(ExpectedConditions.attributeContains(By.xpath("//div[@class='_8v6CF']"),"className","_8v6CF"));
        WebElement buttonCountOfItems = driver.findElement(By.xpath(selectorButtonCountOfItems));
        buttonCountOfItems.click();
        WebElement button12 = driver.findElement(By.xpath(selectorButton12Items));
        button12.click();
    }

    /**
     * Метод, который запоминает название первого товара и вводит в поиск название
     */
    public void rememberName() {
        wait.until(ExpectedConditions.attributeContains(By.xpath("//div[@class='_8v6CF']"),"className","_8v6CF"));
        nameOfFirst = driver.findElement(By.xpath(selectorCountOfItems + "[1]//h3")).getText();
        WebElement element = driver.findElement(By.xpath(selectorMainSearch));
        element.sendKeys(nameOfFirst);
        WebElement element1 = driver.findElement(By.xpath(selectorMainSearchButton));
        element1.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selectorCountOfItems + "[1]//h3")));
        nameOfFirstAfterSearch = driver.findElement(By.xpath(selectorCountOfItems + "[1]//h3")).getText();
    }

    /**
     * @return элемент страницы с разделами со скрином
     * @see steps.Steps#checkCountOfItems(String, WebDriver) 
     */
    public WebElement getSection(){
        return driver.findElement(By.xpath(selectorSubAllSelection));
    }

    /**
     * @return название первого элемента
     * @see steps.Steps#checkNameOfItems(YandexMarket)
     */
    public String getNameOfFirst() {
        return nameOfFirst;
    }
    /**
     * @return название первого элемента
     * @see steps.Steps#checkNameOfItems(YandexMarket)
     */
    public String getNameOfFirstAfterSearch() {
        return nameOfFirstAfterSearch;
    }

    /**
     * @return локатор для скрина
     * @see steps.Steps#checkCountOfItems(String, WebDriver)
     */
    public String getSelectorCountOfItems(){
        return selectorCountOfItems;
    }

}
