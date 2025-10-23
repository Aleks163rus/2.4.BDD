package ru.netology.testmode.test;


import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.DataHelper;
import ru.netology.testmode.page.DashBoardPage;
import ru.netology.testmode.page.LoginPageV2;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.testmode.data.DataHelper.getAuthInfo;

public class MoneyTransferTest {
    int firstCardBalanceBefore;
    int secondCardBalanceBefore;
    DashBoardPage dashBoardPage;
    DataHelper.CardInfo firstCard;
    DataHelper.CardInfo secondCard;


    @BeforeEach

    public void setup() {
        var info = getAuthInfo();
        var verificationCode = DataHelper.getVerificationCode(info);
        var loginPage = Selenide.open("http://localhost:9999", LoginPageV2.class);
        var verificationPage = loginPage.validLogin(info);
        dashBoardPage = verificationPage.validVerify(verificationCode);
        firstCard = DataHelper.getFirstCardInfo(); //данные карты 1
        secondCard = DataHelper.getSecondCardInfo();//данные карты 2
        //Получаем баланс карт
        firstCardBalanceBefore = dashBoardPage.getCardBalance(firstCard);
        secondCardBalanceBefore = dashBoardPage.getCardBalance(secondCard);
    }

    @Test
    public void shouldSuccessfulReplenishmentOfFirstCard() {          //Успешный перевод
        var validAmount = DataHelper.validAmount(firstCardBalanceBefore);
        var transferPage = dashBoardPage.selectCard(firstCard);
        transferPage.validTransferMoney(validAmount, secondCard);

        int firstCardBalanceAfter = dashBoardPage.getCardBalance(firstCard);
        int secondCardBalanceAfter = dashBoardPage.getCardBalance(secondCard);

        assertEquals(firstCardBalanceBefore + validAmount, firstCardBalanceAfter);
        assertEquals(secondCardBalanceBefore - validAmount, secondCardBalanceAfter);
    }


    @Test
    public void shouldUnsuccessfulCardToCardTransfer() {         //Перевод с ошибкой
        var validAmount = DataHelper.invalidAmount(firstCardBalanceBefore);
        var transferPage = dashBoardPage.selectCard(firstCard);
        transferPage.transferMoney(validAmount, secondCard);

        transferPage.shouldMessageError("Сумма списания превышает баланс карты.");

        int firstCardBalanceAfter = dashBoardPage.getCardBalance(firstCard);
        int secondCardBalanceAfter = dashBoardPage.getCardBalance(secondCard);

        assertEquals(firstCardBalanceBefore, firstCardBalanceAfter);
        assertEquals(secondCardBalanceBefore, secondCardBalanceAfter);
    }
}




