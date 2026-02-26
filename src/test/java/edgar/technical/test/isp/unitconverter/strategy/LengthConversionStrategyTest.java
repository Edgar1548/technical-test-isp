package edgar.technical.test.isp.unitconverter.strategy;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import edgar.technical.test.isp.unitconverter.exception.UnsupportedUnitException;
import edgar.technical.test.isp.unitconverter.service.strategy.LengthConversionStrategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LengthConversionStrategyTest {
    private final LengthConversionStrategy strategy = new LengthConversionStrategy();

    @Test
    void shouldConvertMetersToFeet() {
        BigDecimal result = strategy.convert("m", "ft", BigDecimal.valueOf(1));
        assertThat(result.doubleValue()).isCloseTo(3.2808, within(0.01));
    }

    @Test
    void shouldReturnSameValueIfSameUnit() {
        BigDecimal result = strategy.convert("m", "m", BigDecimal.valueOf(10));
        assertThat(result).isEqualByComparingTo("10");
    }

    @Test
    void shouldThrowExceptionIfUnsupportedUnit() {
        assertThatThrownBy(() -> strategy.convert("invalid", "meter", BigDecimal.TEN))
                .isInstanceOf(UnsupportedUnitException.class);
    }
}
