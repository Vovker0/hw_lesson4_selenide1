package guru.qa;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class GithubSearchTest {

    @BeforeAll
    static void beforeAll() {

        Configuration.holdBrowserOpen = true;
        Configuration.baseUrl = "https://github.com";
    }

    @Test
    void jUnitCheckAtSelenideGithub() {

        // - Откройте страницу Selenide в Github
        open("/selenide/selenide");

        // - Перейдите в раздел Wiki проекта
        $("#wiki-tab").click();

        // - Убедитесь, что в списке страниц (Pages) есть страница SoftAssertions
        $(".Box-title").shouldHave(text("Pages"));
        $("#wiki-pages-box").$(".wiki-more-pages-link").$("button").click();
        $("#wiki-pages-box").$(byText("SoftAssertions")).shouldBe(visible);

        // - Откройте страницу SoftAssertions, проверьте что внутри есть пример кода для JUnit5
        $("#wiki-pages-box").$(byText("SoftAssertions")).click();

        $("#wiki-body").$(withText("Using JUnit5 extend test class")).sibling(0).
                shouldHave(text("""
                        @ExtendWith({SoftAssertsExtension.class})
                        class Tests {
                          @Test
                          void test() {
                            Configuration.assertionMode = SOFT;
                            open("page.html");
                                                
                            $("#first").should(visible).click();
                            $("#second").should(visible).click();
                          }
                        }
                        """));
    }
}
