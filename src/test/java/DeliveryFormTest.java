import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

class DeliveryFormTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
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
    void shouldReplanMeetingIfDateChanged() {
        var user = DataGenerator.generateUser();
        var firstDate = DataGenerator.generateDate(3);
        var secondDate = DataGenerator.generateDate(7);

        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(firstDate);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=success-notification]").shouldHave(Condition.text("Встреча успешно запланирована на " + firstDate)).shouldBe(Condition.visible, Duration.ofSeconds(15));

        // Повторная отправка формы с новой датой
        $("[data-test-id=date] input").doubleClick().sendKeys(secondDate);
        $("button.button").click();

        $("[data-test-id=replan-notification]").shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(Condition.visible, Duration.ofSeconds(15));

        $("[data-test-id=replan-notification] button").click();

        $("[data-test-id=success-notification]").shouldHave(Condition.text("Встреча успешно запланирована на " + secondDate)).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
