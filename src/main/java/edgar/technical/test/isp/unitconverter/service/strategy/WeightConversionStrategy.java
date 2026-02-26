package edgar.technical.test.isp.unitconverter.service.strategy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

import org.springframework.stereotype.Component;

import edgar.technical.test.isp.unitconverter.exception.UnsupportedUnitException;
import edgar.technical.test.isp.unitconverter.service.model.ConversionType;

@Component
public class WeightConversionStrategy implements ConversionStrategy {
    private static final MathContext MC = MathContext.DECIMAL64;

    private static final Map<String, BigDecimal> TO_KG = Map.of(
            "KILOGRAM", BigDecimal.ONE,
            "KG", BigDecimal.ONE,
            "LIBRA", new BigDecimal("0.45359237"),
            "POUND", new BigDecimal("0.45359237"),
            "LB", new BigDecimal("0.45359237"),
            "LBS", new BigDecimal("0.45359237"),
            "ONZA", new BigDecimal("0.028349523125"),
            "OUNCE", new BigDecimal("0.028349523125"),
            "OZ", new BigDecimal("0.028349523125"));

    @Override
    public ConversionType supports() {
        return ConversionType.WEIGHT;
    }

    @Override
    public BigDecimal convert(String from, String to, BigDecimal value) {
        String f = norm(from);
        String t = norm(to);

        BigDecimal fromToKg = TO_KG.get(f);
        BigDecimal toToKg = TO_KG.get(t);

        if (fromToKg == null)
            throw new UnsupportedUnitException("Unsupported weight unit: " + from);
        if (toToKg == null)
            throw new UnsupportedUnitException("Unsupported weight unit: " + to);

        BigDecimal kg = value.multiply(fromToKg, MC);
        return kg.divide(toToKg, MC);
    }

    private String norm(String u) {
        return u == null ? "" : u.trim().toUpperCase();
    }
}
