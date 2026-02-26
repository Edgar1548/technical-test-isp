package edgar.technical.test.isp.unitconverter.external.model;

import java.math.BigDecimal;
import java.util.Map;

public record CurrencyApiResponse(
    String result,
    String base_code,
    Map<String, BigDecimal> conversion_rates
) {}
