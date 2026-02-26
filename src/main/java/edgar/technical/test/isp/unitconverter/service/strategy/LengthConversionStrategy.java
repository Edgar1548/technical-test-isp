package edgar.technical.test.isp.unitconverter.service.strategy;

import java.math.MathContext;
import java.math.BigDecimal;

import java.util.Map;

import org.springframework.stereotype.Component;

import edgar.technical.test.isp.unitconverter.exception.UnsupportedUnitException;
import edgar.technical.test.isp.unitconverter.service.model.ConversionType;

@Component
public class LengthConversionStrategy implements ConversionStrategy{
      private static final MathContext MC = MathContext.DECIMAL64;

  private static final Map<String, BigDecimal > TO_METERS = Map.of(
      "METER", BigDecimal.ONE,
      "M", BigDecimal.ONE,
      "INCH", new BigDecimal("0.0254"),
      "IN", new BigDecimal("0.0254"),
      "FEET", new BigDecimal("0.3048"),
      "FT", new BigDecimal("0.3048")
  );

  @Override
  public ConversionType supports() {
    return ConversionType.LENGTH;
  }

  @Override
  public BigDecimal convert(String from, String to, BigDecimal value) {
    String f = norm(from);
    String t = norm(to);

    BigDecimal fromToMeters = TO_METERS.get(f);
    BigDecimal toToMeters = TO_METERS.get(t);

    if (fromToMeters == null) throw new UnsupportedUnitException("Unsupported length unit: " + from);
    if (toToMeters == null) throw new UnsupportedUnitException("Unsupported length unit: " + to);

    BigDecimal meters = value.multiply(fromToMeters, MC);
    return meters.divide(toToMeters, MC);
  }

  private String norm(String u) {
    return u == null ? "" : u.trim().toUpperCase();
  }
}
