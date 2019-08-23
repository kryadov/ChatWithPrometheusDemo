package chat;

import com.codeborne.selenide.SelenideElement;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.stream.IntStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.CombinableMatcher.both;

public class ChatTest {
    @Test
    @Ignore
    public void testSomeChatMessages() {
        open("http://localhost:12345");
        //
        $(By.id("nickname")).sendKeys("Test Robot 1\n");
        SelenideElement messageText = $(By.id("messageText"));
        IntStream.
                range(1, 15).
                mapToObj(i -> "Hello, this is text message " + i + "!!!\n").
                forEach(messageText::sendKeys);
        //
        assertThat($(By.className("panel-body")).getText(),
                both(containsString("Hello, this is text message"))
                        .and(containsString("Test Robot 1"))
        );
    }
}
