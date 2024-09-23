package ru.netology.bdd.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import java.time.Duration;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement header = $("h1");
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement cancelButton = $("[data-test-id=action-cancel]");

    public TransferPage() {
        checkIfWeAreOnTransferPage();
    }

    public CardsPage transfer(int amount, String fromNumber)
    {
        clearField(amountField);
        amountField.setValue(String.valueOf(amount));

        clearField(fromField);
        fromField.setValue(fromNumber);

        transferButton.click();

        return new CardsPage();
    }

    public CardsPage failTransfer(int amount, String fromNumber)
    {
        clearField(amountField);
        amountField.setValue(String.valueOf(amount));

        clearField(fromField);
        fromField.setValue(fromNumber);

        transferButton.click();

        // При ошибке перевода мы должны остаться на текущей странице
        checkIfWeAreOnTransferPage();
        // Должна показываться ошибка
        checkIfErrorIsShown();

        // Возвращаемся обратно
        cancelButton.click();

        return new CardsPage();
    }

    private void clearField(SelenideElement field)
    {
        field.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
    }

    private void checkIfWeAreOnTransferPage()
    {
        header
            .shouldBe(visible, Duration.ofSeconds(1))
            .shouldHave(text("Пополнение карты"));
    }

    private void checkIfErrorIsShown()
    {
        $("[data-test-id=error-notification] .notification__content")
            .shouldBe(visible, Duration.ofSeconds(1))
            .shouldHave(text("Произошла ошибка"));
    }
}
