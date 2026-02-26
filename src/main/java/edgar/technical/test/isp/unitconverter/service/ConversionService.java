package edgar.technical.test.isp.unitconverter.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import edgar.technical.test.isp.unitconverter.dto.request.ConversionRequest;
import edgar.technical.test.isp.unitconverter.dto.response.ConversionResponse;
import edgar.technical.test.isp.unitconverter.service.factory.ConversionStrategyFactory;
import edgar.technical.test.isp.unitconverter.service.strategy.ConversionStrategy;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConversionService {
    private final ConversionStrategyFactory factory;

    public ConversionResponse convert(ConversionRequest request) {
        ConversionStrategy strategy = factory.getStrategy(request.type());
        BigDecimal converted = strategy.convert(request.from(), request.to(), request.value());

        return new ConversionResponse(
                request.type(),
                request.from(),
                request.to(),
                request.value(),
                converted,
                OffsetDateTime.now());
    }
}
