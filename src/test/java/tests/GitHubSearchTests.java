package tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.byAttribute;

public class GitHubSearchTests extends BaseTest {

    @Test
    @DisplayName("Позитив: Поиск репозитория 'selenide' выдает результаты")
    void searchSelenideGivesResults() {
        open("https://github.com/");
        $("[data-target='qbsearch-input.inputButtonText']").click();
        $("[data-target='query-builder.input']").setValue("selenide").pressEnter();
        $$("ul.repo-list li").shouldBe(CollectionCondition.sizeGreaterThan(0));
        $("main").shouldHave(Condition.text("repository results"));
    }

    @Test
    @DisplayName("Позитив: Переход на страницу репозитория 'selenide/selenide'")
    void openSelenideRepositoryPage() {
        open("https://github.com/");
        $("[data-target='qbsearch-input.inputButtonText']").click();
        $("[data-target='query-builder.input']").setValue("selenide/selenide").pressEnter();
        $$("ul.repo-list li").first().$("a").click();
        $("strong.mr-2.flex-self-stretch").shouldHave(Condition.text("selenide"));
        $("[data-view-component='true'][href$='/issues']").shouldBe(Condition.visible);
        $("[data-view-component='true'][href$='/pulls']").shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Позитив: Видна навигация по вкладкам репозитория (Code, Issues, Pull requests)")
    void repoTabsAreVisible() {
        open("https://github.com/selenide/selenide");
        $("[data-tab-item='code-tab']").shouldBe(Condition.visible);
        $("[data-tab-item='issues-tab']").shouldBe(Condition.visible);
        $("[data-tab-item='pull-requests-tab']").shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Позитив: Explore открывается и содержит раздел Trending")
    void exploreTrendingVisible() {
        open("https://github.com/explore");
        $("main").shouldHave(Condition.text("Explore"));
        $("main").shouldHave(Condition.text("Trending"));
    }

    @Test
    @DisplayName("Позитив: Поиск по фильтру Language=Java в репозиториях")
    void searchWithLanguageFilterJava() {
        open("https://github.com/search?q=selenide&type=repositories");
        // Найти и нажать фильтр языка Java, разные варианты селекторов на случай изменений
        $$(".menu a").findBy(Condition.text("Java")).click();
        $("nav[aria-label='Search results']").shouldBe(Condition.visible);
        $$("ul.repo-list li").shouldBe(CollectionCondition.sizeGreaterThan(0));
        // Проверим, что в фильтрах выбран Java
        $(byAttribute("aria-current", "page")).shouldHave(Condition.text("Java"));
    }
}
