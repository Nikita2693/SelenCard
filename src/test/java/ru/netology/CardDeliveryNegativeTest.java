package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDeliveryNegativeTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldNotAddNameInLatinLetters() {

        open("http://localhost:9999");
        $("[data-test-id = 'city'] input ").sendKeys("Москва");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id = 'date'] input").sendKeys(currentDate);
        $("[data-test-id ='name'] input").sendKeys("Ivanov Nikita");
        $("[data-test-id=phone] input").sendKeys("+79157777777");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = $("[data-test-id=name].input_invalid .input__sub").getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddSymbolInName() {

        open("http://localhost:9999");
        $("[data-test-id = 'city'] input ").sendKeys("Москва");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id = 'date'] input").sendKeys(currentDate);
        $("[data-test-id ='name'] input").sendKeys("Никита@");
        $("[data-test-id=phone] input").sendKeys("+79157777777");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = $("[data-test-id=name].input_invalid .input__sub").getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddNumberOverLimit() {
        open("http://localhost:9999");
        $("[data-test-id = 'city'] input ").sendKeys("Москва");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id = 'date'] input").sendKeys(currentDate);
        $("[data-test-id ='name'] input").sendKeys("Никита Дмитриевич");
        $("[data-test-id=phone] input").sendKeys("+791577777778");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = $("[data-test-id=phone].input_invalid .input__sub").getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddNumberLowLimit() {

        open("http://localhost:9999");
        $("[data-test-id = 'city'] input ").sendKeys("Москва");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id = 'date'] input").sendKeys(currentDate);
        $("[data-test-id ='name'] input").sendKeys("Никита Дмитриевич");
        $("[data-test-id=phone] input").sendKeys("+7915777777");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = $("[data-test-id=phone].input_invalid .input__sub").getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotPassWithoutCheckBox() {

        open("http://localhost:9999");
        $("[data-test-id = 'city'] input ").sendKeys("Москва");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id = 'date'] input").sendKeys(currentDate);
        $("[data-test-id ='name'] input").sendKeys("Никита Иванов");
        $("[data-test-id=phone] input").sendKeys("+79157777777");
        $("button.button").click();
        Assertions.assertTrue(($("[data-test-id=agreement].input_invalid").isDisplayed()));


    }

    @Test
    void shouldNotPassWithEmptyName() {

        open("http://localhost:9999");
        $("[data-test-id = 'city'] input ").sendKeys("Москва");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id = 'date'] input").sendKeys(currentDate);
        $("[data-test-id ='name'] input").sendKeys("");
        $("[data-test-id=phone] input").sendKeys("+79157777777");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        String expected = "Поле обязательно для заполнения";
        String actual = $("[data-test-id=name].input_invalid .input__sub").getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotPassWithEmptyPhone() {
        open("http://localhost:9999");
        $("[data-test-id = 'city'] input ").sendKeys("Москва");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id = 'date'] input").sendKeys(currentDate);
        $("[data-test-id ='name'] input").sendKeys("Никита Иванов");
        $("[data-test-id=phone] input").sendKeys("");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        String expected = "Поле обязательно для заполнения";
        String actual = $("[data-test-id=phone].input_invalid .input__sub").getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddWrongCity() {
        open("http://localhost:9999");
        $("[data-test-id = 'city'] input ").sendKeys("Сочи");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id = 'date'] input").sendKeys(currentDate);
        $("[data-test-id ='name'] input").sendKeys("Никита Иванов");
        $("[data-test-id=phone] input").sendKeys("+79157777777");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        String expected = "Доставка в выбранный город недоступна";
        String actual = $("[data-test-id=city].input_invalid .input__sub").getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddDateUnderLimit() {
        open("http://localhost:9999");
        $("[data-test-id = 'city'] input ").sendKeys("Москва");
        String currentDate = generateDate(2, "dd.MM.yyyy");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id = 'date'] input").sendKeys(currentDate);
        $("[data-test-id ='name'] input").sendKeys("Никита Иванов");
        $("[data-test-id=phone] input").sendKeys("+79157777777");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        String expected = "Заказ на выбранную дату невозможен";
        String actual = $x(".//span[@data-test-id='date']//child::span[@class='input__sub']").getText();
        assertEquals(expected, actual);

    }
}
