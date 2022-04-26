package com.yulijava.testproject.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.util.List;

public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".sb-destination__input")
    private WebElement destinationInput;
    @FindBy(css = ".xp__dates__checkin .sb-date-field__select.-month-year select")
    private WebElement checkInMonthYear;
    @FindBy(css = ".xp__dates__checkin .sb-date-field__select.-day select")
    private WebElement checkInDay;
    @FindBy(css = ".xp__dates__checkout .sb-date-field__select.-month-year select")
    private WebElement checkOutMonthYear;
    @FindBy(css = ".xp__dates__checkout .sb-date-field__select.-day select")
    private WebElement checkOutDay;

    @FindBy(css = "#xp__guests__toggle")
    private WebElement guestsToggle;
    @FindBy(css = "#xp__guests__inputs-container")
    private WebElement guestsContainer;

    @FindBy(css = ".sb-group__field-adults .bui-stepper__display")
    private WebElement adultsCount;
    @FindBy(css = ".sb-group__field-adults .bui-stepper__subtract-button")
    private WebElement adultsCountSubtractButton;
    @FindBy(css = ".sb-group__field-adults .bui-stepper__add-button")
    private WebElement adultsCountAddButton;

    @FindBy(css = ".sb-group-children .bui-stepper__display")
    private WebElement childrenCount;
    @FindBy(css = ".sb-group-children .bui-stepper__subtract-button")
    private WebElement childrenCountSubtractButton;
    @FindBy(css = ".sb-group-children .bui-stepper__add-button")
    private WebElement childrenCountAddButton;

    @FindBy(css = ".sb-group__children__field select")
    private List<WebElement> ageSelectElements;

    @FindBy(css = ".sb-searchbox__button")
    private WebElement searchButton;

    public void setDestinationInput(String value) {
        destinationInput.sendKeys(value);
    }

    public void setCheckInDate(LocalDate value) {
        Select selectCheckInMonthYear = new Select(checkInMonthYear);
        selectCheckInMonthYear.selectByValue(value.getMonthValue() + "-" + value.getYear());

        Select selectCheckInDay = new Select(checkInDay);
        selectCheckInDay.selectByValue(Integer.toString(value.getDayOfMonth()));
    }

    public void setCheckOutDate(LocalDate value) {
        Select selectCheckOutMonthYear = new Select(checkOutMonthYear);
        selectCheckOutMonthYear.selectByValue(value.getMonthValue() + "-" + value.getYear());

        Select selectCheckOutDay = new Select(checkOutDay);
        selectCheckOutDay.selectByValue(Integer.toString(value.getDayOfMonth()));
    }

    public void setAdultsCount(Integer desiredCount) {
        setDesiredCount(desiredCount, adultsCountAddButton, adultsCountSubtractButton, adultsCount);
    }

    public void setChildrenCount(Integer desiredCount) {
        setDesiredCount(desiredCount, childrenCountAddButton, childrenCountSubtractButton, childrenCount);

        // Set children age
        for (int i = 0; i < ageSelectElements.size(); i++) {
            Select ageSelect = new Select(ageSelectElements.get(i));
            ageSelect.selectByValue("7"); // 7 years
        }
    }

    public void search() {
        safelyClickElement(searchButton);
    }

    private void setDesiredCount(Integer desiredCount, WebElement addButtonElement, WebElement subtractButtonElement, WebElement countElement) {
        if (!guestsContainer.isDisplayed()) {
            safelyClickElement(guestsToggle);
        }

        Integer count = Integer.parseInt(countElement.getText());
        if (count > desiredCount) {
            Integer repeat = count - desiredCount;
            while (repeat > 0) {
                safelyClickElement(subtractButtonElement);
                repeat--;
            }
        } else if (count < desiredCount) {
            Integer repeat = desiredCount - count;
            while (repeat > 0) {
                safelyClickElement(addButtonElement);
                repeat--;
            }
        }
    }

    private void safelyClickElement(WebElement element){
        JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }
}
