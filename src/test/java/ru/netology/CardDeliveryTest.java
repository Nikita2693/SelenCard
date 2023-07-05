package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldFillGapsCorrectInformation() {
        open("http://localhost:9999");
        $("[data-test-id = 'city'] input ").sendKeys("Москва");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id = 'date'] input").sendKeys(currentDate);
        $("[data-test-id ='name'] input").sendKeys("Иванов Никита");
        $("[data-test-id=phone] input").sendKeys("+79157777777");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));

    }

    @Test
    void shouldDoubleNameTest() {

        open("http://localhost:9999");
        $("[data-test-id = 'city'] input ").setValue("Санкт-Петербург");
        String currentDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id = 'date'] input").sendKeys(currentDate);
        $("[data-test-id ='name'] input").setValue("Никита-Иванович");
        $("[data-test-id=phone] input").setValue("+79157777777");
        $x("//*[text() = 'Я соглашаюсь с условиями обработки и использования моих персональных данных']").click();
        $x("//*[text() ='Забронировать'] ").click();
        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=notification]").should(text("Встреча успешно забронирована на " + currentDate));

    }

}
