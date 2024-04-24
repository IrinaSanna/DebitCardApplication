package ru.netology;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DebitCardApplicationTest {
    @Test
    void shouldSubmittingForm() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Спиридонов Николай");
        $("[data-test-id=phone] input").setValue("+79001112233");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=order-success]").shouldHave(
                exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldCheckInvalidName() {
        open("http://localhost:9999");
        $("[data-test-id] input").setValue("Petrova Inna");
        $("[data-test-id=phone] input").setValue("+79001112233");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(
                exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldCheckInvalidPhone() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Спиридонов Николай");
        $("[data-test-id=phone] input").setValue("+790011122333");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(
                exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNameFieldNotBeEmpty() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79001112233");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldPhoneFieldNotBeEmpty() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Спиридонов Николай");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldAgreementNotGiven() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Спиридонов Николай");
        $("[data-test-id=phone] input").setValue("+79001112233");
        $("button.button").click();
        $("[data-test-id=agreement].input_invalid").shouldBe(visible);
    }
}