package edgar.technical.test.isp.unitconverter.strategy;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import edgar.technical.test.isp.unitconverter.exception.UnsupportedUnitException;
import edgar.technical.test.isp.unitconverter.service.strategy.WeightConversionStrategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WeightConversionStrategyTest {
    private final WeightConversionStrategy strategy = new WeightConversionStrategy();

    @Test
    void shouldConvertToFahrenheit() {
        BigDecimal result = strategy.convert("kg", "lb", BigDecimal.valueOf(1));
        assertThat(result.doubleValue()).isCloseTo(2.2046, within(0.01));
    }

    @Test
    void shouldReturnSameValueIfSameUnit() {
        BigDecimal result = strategy.convert("kg", "kg", BigDecimal.valueOf(10));
        assertThat(result).isEqualByComparingTo("10");
    }

    @Test
    void shouldThrowExceptionIfUnsupportedUnit() {
        assertThatThrownBy(() -> strategy.convert("invalid", "kg", BigDecimal.TEN))
                .isInstanceOf(UnsupportedUnitException.class);
    }
}
