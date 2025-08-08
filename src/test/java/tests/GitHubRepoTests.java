package tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class GitHubRepoTests extends BaseTest {

    @Test
    @DisplayName("Позитив: README отображается на главной странице репозитория")
    void readmeIsVisible() {
        open("https://github.com/selenide/selenide");
        $("#readme").shouldBe(Condition.visible);
        $("#readme").shouldHave(Condition.text("Selenide"));
    }
}
