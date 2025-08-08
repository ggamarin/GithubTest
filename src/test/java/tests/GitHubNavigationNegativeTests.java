package tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class GitHubNavigationNegativeTests extends BaseTest {

    @Test
    @DisplayName("Негатив: Репозиторий, которого не существует, возвращает 404")
    void nonExistingRepoReturns404() {
        open("https://github.com/some_org_that_does_not_exist_xyz/abc-repo-404-test");
        $("main").shouldHave(Condition.text("Page not found")
                .or(Condition.text("Not Found"))
                .or(Condition.text("This is not the web page you are looking for")));
    }

    @Test
    @DisplayName("Негатив: Поиск бессмысленной строки не дает результатов")
    void gibberishSearchNoResults() {
        open("https://github.com/");
        $("[data-target='qbsearch-input.inputButtonText']").click();
        $("[data-target='query-builder.input']").setValue("zzzqqqxxxyyy___no_such_repo___123456").pressEnter();
        $("main").shouldHave(Condition.text("We couldn’t find any repositories matching")
                .or(Condition.text("No results matched your search")));
    }

    @Test
    @DisplayName("Негатив: Доступ к закрытым настройкам без авторизации приводит к логину")
    void settingsRedirectsToLogin() {
        open("https://github.com/settings/profile");
        $("form[action='/session']").shouldBe(Condition.visible);
        $("h1").shouldHave(Condition.text("Sign in")
                .or(Condition.text("Login")));
    }
}
