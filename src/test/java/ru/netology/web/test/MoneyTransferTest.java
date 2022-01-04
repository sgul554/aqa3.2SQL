package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;
import ru.netology.web.sqlUtils.SqlUtils;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    SqlUtils mySql = new SqlUtils();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldCheckLogin() throws SQLException {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = SqlUtils.getVerificationCode(authInfo.getLogin());
        val verify = verificationPage.validVerify(verificationCode);
        verify.shouldBeVisible();
    }

    @Test
    void shouldBlockedAfterThreeInvalidPassword() throws SQLException {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getOtherAuthInfo();
        loginPage.validLogin(authInfo);
        loginPage.cleanLoginFields();
        loginPage.validLogin(authInfo);
        loginPage.cleanLoginFields();
        loginPage.validLogin(authInfo);
        val statusSQL = mySql.getStatusFromDb(authInfo.getLogin());
        assertEquals("blocked", statusSQL);
    }

    @AfterAll
    static void close() throws SQLException {
        SqlUtils.cleanDb();
    }

}
