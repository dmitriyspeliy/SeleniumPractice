package ru.yandex;

import io.qameta.allure.Feature;
import manager.Manager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.YandexMarket;
import pages.YandexSearch;
import steps.Steps;

import java.util.concurrent.TimeUnit;

/**
 * Класс для теста
 */
public class MainTest {
    private WebDriver chromeDriver;

    @BeforeEach
    void before() {
        Manager.initChrome();
        chromeDriver = Manager.getCurrentDriver();
    }

    @Feature("Проверка яндекс маркет")
    @DisplayName("Проверка поиска в яндекс маркет с помощью ПО")
    @ParameterizedTest(name = "{displayName} {arguments}")
    @Tag("Задание 1.3")
    @CsvSource({"Маркет,Компьютеры,Ноутбуки,10000,900000,HP,Lenovo"})
    void resultTest(String nameOfIcon, String sectionName,String subSection,int priceFrom,int priceTo,String producer1,String producer2) {
        YandexSearch yandexSearch = new YandexSearch();
        yandexSearch.init();
        Steps.checkContainsName(yandexSearch.getCollectIcons(),nameOfIcon, yandexSearch);
        YandexMarket yandexMarket = new YandexMarket();
        yandexMarket.selectSection(sectionName, subSection);
        yandexMarket.setParameterOfPrise(priceFrom, priceTo);
        yandexMarket.selectedBoxOfProducer(producer1, producer2);
        Steps.checkCountOfItems(yandexMarket.getSelectorCountOfItems(),chromeDriver);
        yandexMarket.rememberName();
        Steps.checkNameOfItems(yandexMarket);
    }


    @AfterEach
    void closeableTest() {
       Manager.killCurrentDriver();
    }
}
