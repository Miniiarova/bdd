package ru.netology.bdd.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.AttributeWithValue;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardsPage {
    private SelenideElement header = $("h1");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public CardsPage() {
        header
            .shouldBe(visible, Duration.ofSeconds(1))
            .shouldHave(text("Ваши карты"));
    }

    public int getCardBalance(String cardId) {
        var t = findCardElement(cardId);
        return extractBalance(t.text());
    }

    public TransferPage goToTransferPage(String cardId) {
        var cardElement = findCardElement(cardId);
        cardElement.find(By.cssSelector("button")).click();

        return new TransferPage();
    }

    private SelenideElement findCardElement(String cardId) {
        return cards.findBy(new AttributeWithValue("data-test-id", cardId));
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
