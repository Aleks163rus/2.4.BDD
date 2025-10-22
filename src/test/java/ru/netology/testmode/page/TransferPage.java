package ru.netology.testmode.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.testmode.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement header = $("h1");
    private final SelenideElement sumInput = $("[data-test-id='amount'] input");
    private final SelenideElement fromCardTransfer = $("[data-test-id='from'] input");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement errorMessage = $("[data-test-id='error-notification']");

    public TransferPage() {
        header.should(Condition.visible);
    }

    //Перевод на карту безупешный
    public void transferMoney(int amount, DataHelper.CardInfo fromCard) {
        sumInput.setValue(String.valueOf(amount));
        fromCardTransfer.setValue(fromCard.getCardNumber());
        transferButton.click();
    }

    //Перевод с карты на карту успешный
    public void validTransferMoney(int amount, DataHelper.CardInfo fromCard) {
        transferMoney(amount, fromCard);
        new DashBoardPage();
    }

    public void shouldMessageError(String expectedText) {
        errorMessage.should(Condition.visible).should(Condition.exactText(expectedText));
    }
}