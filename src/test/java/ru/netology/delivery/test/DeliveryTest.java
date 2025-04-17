package ru.netology.delivery.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGeneration;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeAll
    static void setupAllureReports() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGeneration.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGeneration.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGeneration.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id='date'] [placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $("[data-test-id=city] input").setValue(validUser .getCity());
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser .getName());
        $("[data-test-id=phone] input").setValue("+7"+validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $("[class=button__content]").click();

        // Проверка сообщения об успешном планировании встречи
        $("[data-test-id=success-notification] [class='notification__content']").shouldBe(text("Встреча успешно запланирована на " + firstMeetingDate));

        // Перепланирование встречи
        $("[class=button__content]").click();
        $("[data-test-id='date'] [placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $("[data-test-id=replan-notification]") .click();

        // Проверка сообщения об успешном перепланировании встречи
        $("[data-test-id=success-notification] [class='notification__content']").shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate));
    }
}