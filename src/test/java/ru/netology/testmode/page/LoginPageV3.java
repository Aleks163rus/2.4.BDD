package ru.netology.testmode.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.netology.testmode.data.DataHelper;

public class LoginPageV3 {

    @FindBy(css = "[data-test-id=login] input")
    private SelenideElement LoginField;

    @FindBy(css = "[data-test-id=password] input")
    private SelenideElement passwordField;

    @FindBy(css = "[data-test-id=action-login]")
    private SelenideElement loginButton;

    public  VerificationPage validlogin(DataHelper.AuthInfo info) {
        LoginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return Selenide.page(VerificationPage.class);

    }
}



