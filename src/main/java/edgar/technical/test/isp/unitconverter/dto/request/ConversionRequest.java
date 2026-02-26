package edgar.technical.test.isp.unitconverter.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

import edgar.technical.test.isp.unitconverter.service.model.ConversionType;

public record ConversionRequest(
    @NotNull(message = "type is required")
    ConversionType type,

    @NotBlank(message = "from is required")
    String from,

    @NotBlank(message = "to is required")
    String to,

    @NotNull(message = "value is required")
    @DecimalMin(value = "0", inclusive = true, message = "value must be >= 0")
    BigDecimal value

) {

}
