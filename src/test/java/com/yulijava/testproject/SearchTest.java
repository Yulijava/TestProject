package com.yulijava.testproject;

import com.yulijava.testproject.page.HomePage;
import com.yulijava.testproject.page.ResultPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class SearchTest {
    private WebDriver driver;
    private HomePage homePage;
    private ResultPage resultPage;

    @BeforeMethod
    public void beforeTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.booking.com/");
        homePage = new HomePage(driver);
        resultPage = new ResultPage(driver);
    }

    @AfterMethod
    public void afterTest(){
        driver.quit();
    }

    @Test
    public void searchMalagaHasResults() {
        LocalDate now = LocalDate.now();
        LocalDate nextStartOfWeek = now.plusWeeks(1).minusDays(now.getDayOfWeek().getValue() - 1);
        LocalDate nextEndOfWeek = now.plusWeeks(1).plusDays(7 - now.getDayOfWeek().getValue());

        homePage.setDestinationInput("Malaga");
        homePage.setCheckInDate(nextStartOfWeek);
        homePage.setCheckOutDate(nextEndOfWeek);
        homePage.setAdultsCount(2);
        homePage.setChildrenCount(1);
        homePage.search();

        Assert.assertTrue(resultPage.getPropertyCount() > 0);
    }
}
