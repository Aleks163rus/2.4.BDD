package ru.netology.testmode.data;

import lombok.Value

public class DataHelper {

    public class DataHelper() {

    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");

    }

public static VertificationCode getVeritificationCode(AuthInfo authInfo) {
    return new VertificationCode("12345");
}

@Value
public static class AuthInfo {
    String login;
    String password;
}

@Value
public static class VertificationCode {
    private String code;
}


}
