package gh.funthomas424242.junit.example;

import com.github.funthomas424242.junit5.extensions.CountingWatcherExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(CountingWatcherExtension.class)
public class RegexPatternTest {


    private static Pattern pattern;

    @BeforeAll
    public static void setUp() {
        pattern = Pattern.compile("[0-9]{1,5}");
    }


    @Test
    @DisplayName("check [0-9]{1,5} mit \"\" ")
    public void checkLeerstring() {

        Matcher m = pattern.matcher("");
        assertEquals(0, m.results().count());
    }

    @Test
    @DisplayName("check [0-9]{1,5} mit \"0123456789d999a787\" ")
    public void checkMultiTreffer() {

        Matcher m = pattern.matcher("0123456789d999a787");
        assertEquals(4, m.results().count());

        Matcher m1 = pattern.matcher("0123456789d999a787");
        m1.results().forEach((result) -> System.out.println("##:"+ result.group()));
    }

}
