package edgar.technical.test.isp.unitconverter.external.client;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import edgar.technical.test.isp.unitconverter.exception.ExternalServiceException;
import edgar.technical.test.isp.unitconverter.external.model.CurrencyApiResponse;

@Component
public class CurrencyApiClient {
    private final RestClient restClient;
    private final String apiKey;

    public CurrencyApiClient(
            RestClient currencyRestClient,
            @Value("${currency.api.key:}") String apiKey) {
        this.restClient = currencyRestClient;
        this.apiKey = apiKey;
    }


    public BigDecimal getRate(String base, String target) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new ExternalServiceException("Missing currency.api.key", 500);
        }

        CurrencyApiResponse response = restClient.get()
                .uri("/v6/{apiKey}/latest/{base}", apiKey, base)
                .retrieve()
                .body(CurrencyApiResponse.class);

        if (response == null || response.conversion_rates() == null) {
            throw new ExternalServiceException("Invalid currency provider response", 502);
        }

        BigDecimal rate = response.conversion_rates().get(target);
        if (rate == null) {
            throw new ExternalServiceException("Rate not found for target currency: " + target, 502);
        }
        return rate;
    }
}
