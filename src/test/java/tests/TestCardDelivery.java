package tests;

import com.codeborne.selenide.Configuration;
import date.DataGenerator;
import date.RegistrationByCardInfo;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static date.DataGenerator.generateDate;

public class TestCardDelivery {

    @Test
    void shouldSuccessfulCardOrder() {
        String planningDate = DataGenerator.generateDate(15);
        String RedevelopedDate = DataGenerator.generateDate(20);

        open("http://localhost:9999");

        RegistrationByCardInfo firstMeeting = DataGenerator.Registration.generateByCard("ru");
        Configuration.holdBrowserOpen = true;

        $("[data-test-id='city'] input").setValue(firstMeeting.getCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue(firstMeeting.getName());
        $("[data-test-id='phone'] input").setValue(firstMeeting.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + planningDate), Duration.ofSeconds(15));
        $(".notification__content").click();

//      Перепланирование даты
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(RedevelopedDate);
        $(byText("Запланировать")).click();
        $(withText("У вас уже запланирована")).shouldBe(visible);
        $(byText("Перепланировать")).click();
        $(".notification__content").shouldBe(visible).shouldHave(exactText("Встреча успешно запланирована на " + RedevelopedDate), Duration.ofSeconds(15));


    }

}
