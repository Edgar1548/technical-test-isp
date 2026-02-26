package edgar.technical.test.isp.unitconverter.service.strategy;

import java.math.BigDecimal;
import java.math.MathContext;

import org.springframework.stereotype.Component;

import edgar.technical.test.isp.unitconverter.exception.InvalidConversionException;
import edgar.technical.test.isp.unitconverter.external.client.CurrencyApiClient;
import edgar.technical.test.isp.unitconverter.service.model.ConversionType;

@Component
public class CurrencyConversionStrategy implements ConversionStrategy {

    private static final MathContext MathCtx = MathContext.DECIMAL64;

    private final CurrencyApiClient currencyApiClient;

    public CurrencyConversionStrategy(CurrencyApiClient currencyApiClient) {
        this.currencyApiClient = currencyApiClient;
    }

    @Override
    public ConversionType supports() {
        return ConversionType.CURRENCY;
    }

    @Override
    public BigDecimal convert(String from, String to, BigDecimal value) {
        String base = normCurrency(from);
        String target = normCurrency(to);

        if (base.length() != 3)
            throw new InvalidConversionException("Invalid currency code: " + from);
        if (target.length() != 3)
            throw new InvalidConversionException("Invalid currency code: " + to);

        if (base.equals(target))
            return value;

        BigDecimal rate = currencyApiClient.getRate(base, target);
        return value.multiply(rate, MathCtx);
    }

    private String normCurrency(String c) {
        return c == null ? "" : c.trim().toUpperCase();
    }

}
