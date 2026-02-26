package edgar.technical.test.isp.unitconverter.external;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestClient;

import edgar.technical.test.isp.unitconverter.external.client.CurrencyApiClient;
import edgar.technical.test.isp.unitconverter.external.model.CurrencyApiResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyApiClientTest {
    @Test
    void shouldReturnCorrectRate() {

        RestClient restClient = Mockito.mock(RestClient.class, Mockito.RETURNS_DEEP_STUBS);

        CurrencyApiResponse response = new CurrencyApiResponse("success", "USD",
                Map.of("PEN", BigDecimal.valueOf(3.8)));

        Mockito.when(
                restClient.get()
                        .uri(Mockito.anyString(), Mockito.any(), Mockito.any())
                        .retrieve()
                        .body(CurrencyApiResponse.class))
                .thenReturn(response);

        CurrencyApiClient client = new CurrencyApiClient(restClient, "fakeKey");

        BigDecimal rate = client.getRate("USD", "PEN");

        assertThat(rate).isEqualByComparingTo("3.8");
    }
}
