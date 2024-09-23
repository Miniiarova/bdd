package ru.netology.bdd.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.bdd.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import java.time.Duration;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public VerificationPage() {
        codeField.shouldBe(visible, Duration.ofSeconds(1));
    }

    public CardsPage verify(DataHelper.VerificationCode verificationCode)
    {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();

        return new CardsPage();
    }
}
