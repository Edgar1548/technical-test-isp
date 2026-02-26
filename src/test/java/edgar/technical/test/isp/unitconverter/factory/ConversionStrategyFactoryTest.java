package edgar.technical.test.isp.unitconverter.factory;

import org.junit.jupiter.api.Test;

import java.util.List;

import edgar.technical.test.isp.unitconverter.service.factory.ConversionStrategyFactory;
import edgar.technical.test.isp.unitconverter.service.model.ConversionType;
import edgar.technical.test.isp.unitconverter.service.strategy.ConversionStrategy;
import edgar.technical.test.isp.unitconverter.service.strategy.LengthConversionStrategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ConversionStrategyFactoryTest {
    @Test
    void shouldReturnCorrectStrategy() {
        ConversionStrategy length = new LengthConversionStrategy();
        ConversionStrategyFactory factory = new ConversionStrategyFactory(List.of(length));

        ConversionStrategy result = factory.getStrategy(ConversionType.LENGTH);

        assertThat(result).isInstanceOf(LengthConversionStrategy.class);
    }

    @Test
    void shouldThrowIfTypeNotFound() {
        ConversionStrategyFactory factory = new ConversionStrategyFactory(List.of());

        assertThatThrownBy(() -> factory.getStrategy(ConversionType.LENGTH)).isInstanceOf(RuntimeException.class);
    }
}
