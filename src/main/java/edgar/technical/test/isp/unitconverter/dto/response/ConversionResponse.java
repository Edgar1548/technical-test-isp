package edgar.technical.test.isp.unitconverter.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import edgar.technical.test.isp.unitconverter.service.model.ConversionType;

public record ConversionResponse(
    ConversionType type,
    String from,
    String to,
    BigDecimal originalValue,
    BigDecimal convertedValue,
    OffsetDateTime timestamp
) {}
