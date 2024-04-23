package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitCardApplicationTest {
    @Test
    void shouldSubmittingForm() {
        open("http://localhost:9999");
        SelenideElement form = $("data-test-id=name");
        $("[data-test-id=name] input").setValue("Спиридонов Николай");
        $("[data-test-id=phone] input").setValue("+79001112233");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldCheckInvalidName() {
        open("http://localhost:9999");
        SelenideElement form = $("data-test-id=name");
        $("[data-test-id] input").setValue("Petrova Inna");
        $("[data-test-id=phone] input").setValue("+79001112233");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                $("[data-test-id=name].input_invalid .input__sub").getText());
    }

    @Test
    void shouldCheckInvalidPhone() {
        open("http://localhost:9999");
        SelenideElement form = $("data-test-id=name");
        $("[data-test-id=name] input").setValue("Спиридонов Николай");
        $("[data-test-id=phone] input").setValue("+790011122333");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                $("[data-test-id=phone].input_invalid .input__sub").getText());
    }

    @Test
    void shouldNameFieldNotBeEmpty() {
        open("http://localhost:9999");
        SelenideElement form = $("data-test-id=name");
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79001112233");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        assertEquals("Поле обязательно для заполнения",
                $("[data-test-id=name].input_invalid .input__sub").getText());
    }

    @Test
    void shouldPhoneFieldNotBeEmpty() {
        open("http://localhost:9999");
        SelenideElement form = $("[data-test-id=name]");
        $("[data-test-id=name] input").setValue("Спиридонов Николай");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        assertEquals("Поле обязательно для заполнения",
                $("[data-test-id=phone].input_invalid .input__sub").getText());
    }

    @Test
    void shouldAgreementNotGiven() {
        open("http://localhost:9999");
        SelenideElement form = $("[data-test-id=name]");
        $("[data-test-id=name] input").setValue("Спиридонов Николай");
        $("[data-test-id=phone] input").setValue("+79001112233");
        $("[data-test-id=agreement]");
        $("button.button").click();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй",
                $("[data-test-id=agreement].input_invalid .checkbox__text").getText());
    }
}