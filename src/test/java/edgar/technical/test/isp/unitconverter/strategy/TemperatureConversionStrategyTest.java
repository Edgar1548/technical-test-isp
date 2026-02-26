package edgar.technical.test.isp.unitconverter.strategy;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import edgar.technical.test.isp.unitconverter.exception.UnsupportedUnitException;
import edgar.technical.test.isp.unitconverter.service.strategy.TemperatureConversionStrategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class TemperatureConversionStrategyTest {

    private final TemperatureConversionStrategy strategy = new TemperatureConversionStrategy();

    @Test
    void shouldConvertCelsiusToFahrenheit() {
        BigDecimal result = strategy.convert("celsius", "fahrenheit", BigDecimal.valueOf(1));
        assertThat(result.doubleValue()).isCloseTo(33.8, within(0.01));
    }

    @Test
    void shouldReturnSameValueIfSameUnit() {
        BigDecimal result = strategy.convert("celsius", "celsius", BigDecimal.valueOf(10));
        assertThat(result).isEqualByComparingTo("10");
    }

    @Test
    void shouldThrowExceptionIfUnsupportedUnit() {
        assertThatThrownBy(() -> strategy.convert("invalid", "fahrenheit", BigDecimal.TEN))
                .isInstanceOf(UnsupportedUnitException.class);
    }
}
