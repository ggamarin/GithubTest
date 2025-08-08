package tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class GitHubAuthNegativeTests extends BaseTest {

    @Test
    @DisplayName("Негатив: Пустые логин/пароль — сообщение об ошибке")
    void emptyLoginShowsError() {
        open("https://github.com/login");
        $("#login_field").setValue("");
        $("#password").setValue("");
        $("input[type='submit']").click();
        $("#js-flash-container, .flash-error").shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Негатив: Неверные учетные данные — ошибка аутентификации")
    void wrongCredentialsShowsError() {
        open("https://github.com/login");
        $("#login_field").setValue("some.fake.user@example.com");
        $("#password").setValue("wrong_password_123");
        $("input[type='submit']").click();
        $("#js-flash-container, .flash-error").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Incorrect username or password")
                        .or(Condition.text("There have been several failed attempts"))
                        .or(Condition.text("Verify your account")));
    }
}