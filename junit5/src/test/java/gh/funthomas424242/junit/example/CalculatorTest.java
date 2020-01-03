package gh.funthomas424242.junit.example;

import com.github.funthomas424242.junit5.extensions.CountingWatcherExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(CountingWatcherExtension.class)
public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    private void setUpTestCase() {
        calculator = new Calculator();
    }


    @ParameterizedTest
    @MethodSource("getValidDoubleParameters")
    @DisplayName("wertC=add(wertA,wertB) //JUnit4 Test korrekt konvertiert und fixed")
    public void add(final Double wertA, Double wertB, Double expected) {

        final Double wertC = calculator.add(wertA, wertB);
        assertEquals(expected, wertC);
    }

    private static Stream<Arguments> getValidDoubleParameters() {
        return Stream.of(
                Arguments.of(Double.valueOf(1), 2.0, 3.0),
                Arguments.of(Double.valueOf(-1), 2.0, 1.0),
                Arguments.of(Double.valueOf(2), 2.0, 4.0),
                Arguments.of(Double.valueOf(4), 0.0, 4.0)
                // zum Vergleich die alten Werte aus JUnit 4
                //		parameters.add(new Object[] { 1L, 2L, 3.0 });
                //		parameters.add(new Object[] { -1L, 2L, 1.0 });
                //		parameters.add(new Object[] { 2L, 2L, 4.0 });
                //		parameters.add(new Object[] { 4L, 0L, 4.0 });
        );
    }


    @ParameterizedTest
    @MethodSource("getValidLongParameter")
    @DisplayName("wertC=add(wertA,wertB) //JUnit4 Test 1:1 konvertiert")
    public void addInvalid(final Long wertA, Long wertB, Double expected) {

        final Double wertC = assertDoesNotThrow(() -> {
            return calculator.add(wertA.doubleValue(), wertB.doubleValue());
        });
        assertEquals(expected, wertC);
    }


    private static Stream<Arguments> getValidLongParameter() {
        return Stream.of(
                Arguments.of(1L, 2L, 3.0),
                Arguments.of(-1L, 2L, 1.0),
                Arguments.of(2L, 2L, 4.0),
                Arguments.of(4L, 0L, 4.0)
                // zum Vergleich die alten Werte aus JUnit 4
                //		parameters.add(new Object[] { 1L, 2L, 3.0 });
                //		parameters.add(new Object[] { -1L, 2L, 1.0 });
                //		parameters.add(new Object[] { 2L, 2L, 4.0 });
                //		parameters.add(new Object[] { 4L, 0L, 4.0 });
        );
    }


    @ParameterizedTest
    @ArgumentsSource(CalculatorArgumentsProvider.class)
    @DisplayName("wertC=add(wertA,wertB) //per ArgumentsProvider Klasse")
    public void addInvalid(final Double wertA, Double wertB, Double expected) {

        final Double wertC = assertDoesNotThrow(() -> calculator.add(wertA, wertB));
        assertEquals(expected, wertC);
    }
}
