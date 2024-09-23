package ru.netology.bdd.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.bdd.data.DataHelper;
import ru.netology.bdd.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTrasnferTest {
    @BeforeEach
    void setUpEach() {
        open("http://localhost:9999");
    }

    @AfterEach
    void cleanUpEach() {
        closeWebDriver();
    }

    @Test
    public void shouldTransferMoneyFrom1To2()
    {
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var cardsPage = verificationPage.verify(verificationCode);

        var card1 = DataHelper.getFirstCard();
        var card2 = DataHelper.getSecondCard();

        var initialBalance1 = cardsPage.getCardBalance(card1.getId());
        var initialBalance2 = cardsPage.getCardBalance(card2.getId());

        var transferToCard1Page = cardsPage.goToTransferPage(card1.getId());
        var transferAmount = 100;

        cardsPage = transferToCard1Page.transfer(transferAmount, card2.getNumber());

        assertEquals(initialBalance1 + transferAmount, cardsPage.getCardBalance(card1.getId()));
        assertEquals(initialBalance2 - transferAmount, cardsPage.getCardBalance(card2.getId()));
    }

    @Test
    public void shouldTransferMoneyFrom2To1()
    {
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var cardsPage = verificationPage.verify(verificationCode);

        var card1 = DataHelper.getFirstCard();
        var card2 = DataHelper.getSecondCard();

        var initialBalance1 = cardsPage.getCardBalance(card1.getId());
        var initialBalance2 = cardsPage.getCardBalance(card2.getId());

        var transferToCard2Page = cardsPage.goToTransferPage(card2.getId());
        var transferAmount = 100;

        cardsPage = transferToCard2Page.transfer(transferAmount, card1.getNumber());

        assertEquals(initialBalance1 - transferAmount, cardsPage.getCardBalance(card1.getId()));
        assertEquals(initialBalance2 + transferAmount, cardsPage.getCardBalance(card2.getId()));
    }

    @Test
    public void shouldFailForNonExistentCard()
    {
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var cardsPage = verificationPage.verify(verificationCode);

        var card1 = DataHelper.getFirstCard();
        var card2 = DataHelper.getFirstCard();
        var nonExistentCard = DataHelper.getRandomCard();

        var initialBalance1 = cardsPage.getCardBalance(card1.getId());
        var initialBalance2 = cardsPage.getCardBalance(card2.getId());
        var transferAmount = 100;

        var transferToCard1Page = cardsPage.goToTransferPage(card1.getId());

        cardsPage = transferToCard1Page.failTransfer(transferAmount, nonExistentCard.getNumber());

        assertEquals(initialBalance1, cardsPage.getCardBalance(card1.getId()));
        assertEquals(initialBalance2, cardsPage.getCardBalance(card2.getId()));
    }

    @Test
    public void shouldFailForInsufficientFunds()
    {
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var cardsPage = verificationPage.verify(verificationCode);

        var card1 = DataHelper.getFirstCard();
        var card2 = DataHelper.getSecondCard();

        var initialBalance1 = cardsPage.getCardBalance(card1.getId());
        var initialBalance2 = cardsPage.getCardBalance(card2.getId());
        var transferAmount = initialBalance2 + 100;

        var transferToCard1 = cardsPage.goToTransferPage(card1.getId());

        cardsPage = transferToCard1.failTransfer(transferAmount, card2.getNumber());

        assertEquals(initialBalance1, cardsPage.getCardBalance(card1.getId()));
        assertEquals(initialBalance2, cardsPage.getCardBalance(card2.getId()));
    }
}
