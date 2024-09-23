package ru.netology.bdd.data;

import com.github.javafaker.Faker;
import lombok.Value;
import org.checkerframework.checker.units.qual.C;

public class DataHelper {

    public static AuthInfo getAuthInfo()
    {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo()
    {
        return new AuthInfo("petya", "123qwerty");
    }

    public static VerificationCode getVerificationCode()
    {
        return new VerificationCode("12345");
    }

    public static CardInfo getFirstCard()
    {
        return new CardInfo("92df3f1c-a033-48e6-8390-206f6b1f56c0", "5559000000000001");
    }

    public static CardInfo getSecondCard()
    {
        return new CardInfo("0f3f5c2a-249e-4c3d-8287-09f7a039391d", "5559000000000002");
    }

    public static CardInfo getRandomCard()
    {
        Faker faker = new Faker();
        return new CardInfo(faker.internet().uuid(), faker.numerify("################"));
    }

    @Value
    public static class AuthInfo
    {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode
    {
        String code;
    }

    @Value
    public static class CardInfo
    {
        String id;
        String number;
    }
}
