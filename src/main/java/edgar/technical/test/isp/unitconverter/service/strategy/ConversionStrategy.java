package edgar.technical.test.isp.unitconverter.service.strategy;

import java.math.BigDecimal;

import edgar.technical.test.isp.unitconverter.service.model.ConversionType;


public interface ConversionStrategy {
  ConversionType supports();
  BigDecimal convert(String from, String to, BigDecimal value);
}
