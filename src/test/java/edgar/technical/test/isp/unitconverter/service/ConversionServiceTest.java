package edgar.technical.test.isp.unitconverter.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edgar.technical.test.isp.unitconverter.dto.request.ConversionRequest;
import edgar.technical.test.isp.unitconverter.service.factory.ConversionStrategyFactory;
import edgar.technical.test.isp.unitconverter.service.model.ConversionType;
import edgar.technical.test.isp.unitconverter.service.strategy.ConversionStrategy;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversionServiceTest {
    @Test
    void shouldConvertUsingStrategy() {

        ConversionStrategy strategy = Mockito.mock(ConversionStrategy.class);
        Mockito.when(strategy.supports()).thenReturn(ConversionType.LENGTH);
        Mockito.when(strategy.convert("m", "ft", BigDecimal.ONE))
                .thenReturn(BigDecimal.TEN);

        ConversionStrategyFactory factory = new ConversionStrategyFactory(java.util.List.of(strategy));

        ConversionService service = new ConversionService(factory);

        ConversionRequest request = new ConversionRequest(
                ConversionType.LENGTH,
                "m",
                "ft",
                BigDecimal.ONE);

        var response = service.convert(request);

        assertThat(response.convertedValue())
                .isEqualByComparingTo("10");
    }
}
