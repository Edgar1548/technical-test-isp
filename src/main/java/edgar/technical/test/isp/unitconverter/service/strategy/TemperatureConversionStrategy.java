package edgar.technical.test.isp.unitconverter.service.strategy;

import java.math.BigDecimal;
import java.math.MathContext;

import org.springframework.stereotype.Component;

import edgar.technical.test.isp.unitconverter.exception.UnsupportedUnitException;
import edgar.technical.test.isp.unitconverter.service.model.ConversionType;

@Component
public class TemperatureConversionStrategy implements ConversionStrategy {

  private static final MathContext MC = MathContext.DECIMAL64;
  private static final BigDecimal NINE = new BigDecimal("9");
  private static final BigDecimal FIVE = new BigDecimal("5");
  private static final BigDecimal THIRTY_TWO = new BigDecimal("32");

  @Override
  public ConversionType supports() {
    return ConversionType.TEMPERATURE;
  }

  @Override
  public BigDecimal convert(String from, String to, BigDecimal value) {
    String f = norm(from);
    String t = norm(to);

    boolean fromC = isC(f);
    boolean fromF = isF(f);
    boolean toC = isC(t);
    boolean toF = isF(t);

    if (!(fromC || fromF)) throw new UnsupportedUnitException("Unsupported temperature unit: " + from);
    if (!(toC || toF)) throw new UnsupportedUnitException("Unsupported temperature unit: " + to);

    if (fromC && toC) return value;
    if (fromF && toF) return value;

    if (fromC && toF) {
      // F = C * 9/5 + 32
      return value.multiply(NINE, MC).divide(FIVE, MC).add(THIRTY_TWO, MC);
    }

    // C = (F - 32) * 5/9
    return value.subtract(THIRTY_TWO, MC).multiply(FIVE, MC).divide(NINE, MC);
  }

  private boolean isC(String u) {
    return u.equals("CELSIUS") || u.equals("C") || u.equals("°C");
  }

  private boolean isF(String u) {
    return u.equals("FAHRENHEIT") || u.equals("F") || u.equals("°F");
  }

  private String norm(String u) {
    return u == null ? "" : u.trim().toUpperCase();
  }
}
