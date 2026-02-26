package edgar.technical.test.isp.unitconverter.service.factory;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import edgar.technical.test.isp.unitconverter.exception.InvalidConversionException;
import edgar.technical.test.isp.unitconverter.service.model.ConversionType;
import edgar.technical.test.isp.unitconverter.service.strategy.ConversionStrategy;

@Component
public class ConversionStrategyFactory {
    private final Map<ConversionType, ConversionStrategy> strategies = new EnumMap<>(ConversionType.class);

    public ConversionStrategyFactory(List<ConversionStrategy> strategyBean){
        for (ConversionStrategy s:strategyBean) strategies.put(s.supports(), s);
    }

    public ConversionStrategy getStrategy(ConversionType type){
        ConversionStrategy s = strategies.get(type);
        if (s == null){
            throw new InvalidConversionException("Unsupported conversion type: "+ type);
        }
        return s;
    }
}