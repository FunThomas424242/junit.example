package gh.funthomas424242.junit.example;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class CalculatorArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(0.0, 0.0, 0.0),
                Arguments.of(-1.0, 2.0, 1.0),
                Arguments.of(2.0, 2.0, 4.0),
                Arguments.of(4.0, 0.0, 4.0)
        );
    }
}
