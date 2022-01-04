package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;


public class DashboardPage {
    private SelenideElement headingPage = $("[data-test-id=dashboard]");

    public void shouldBeVisible() {
        headingPage.shouldBe(Condition.visible);
    }
}
