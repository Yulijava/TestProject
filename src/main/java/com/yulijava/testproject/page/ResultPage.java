package com.yulijava.testproject.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ResultPage {

    private WebDriver driver;

    public ResultPage(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div[data-testid='property-card']")
    private List<WebElement> propertyCards;

    public int getPropertyCount() {
        return propertyCards.size();
    }
}
