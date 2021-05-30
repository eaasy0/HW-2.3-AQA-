package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.Generate.generateDeliveryDate;


public class CardDeliveryTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void ValidRequest() {
        UserData user = DataGenerator.Generate.generateUserData("ru");
        SelenideElement form = $("[action]");
        form.$("[placeholder=Город]").setValue(user.getCity());
        form.$("[data-test-id=date] input.input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input.input__control").setValue(generateDeliveryDate(6));
        form.$("[name=name]").setValue(user.getName());
        form.$("[name=phone]").setValue(user.getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$(".button_view_extra").click();
        $("[data-test-id=success-notification]").waitUntil(Condition.visible, 15000).shouldHave(exactText("Успешно! Встреча успешно запланирована на " + generateDeliveryDate(6)));
        form.$("[data-test-id=date] input.input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input.input__control").setValue(generateDeliveryDate(10));
        form.$(".button_view_extra").click();
        $(byText("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(".notification_has-closer .button").click();
        $("[data-test-id=success-notification]").waitUntil(Condition.visible, 15000).shouldHave(exactText("Успешно! Встреча успешно запланирована на " + generateDeliveryDate(10)));
    }


}

