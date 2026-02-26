package edgar.technical.test.isp.unitconverter.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import edgar.technical.test.isp.unitconverter.exception.ExternalServiceException;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient currencyRestClient(
            @Value("${currency.api.base-url}") String baseUrl,
            @Value("${currency.api.connect-timeout:2s}") Duration connectTimeout,
            @Value("${currency.api.read-timeout:3s}") Duration readTimeout) {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.defaults()
                .withConnectTimeout(connectTimeout).withReadTimeout(readTimeout);
                

        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactoryBuilder.detect().build(settings);

        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(requestFactory)
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    throw new ExternalServiceException(
                            "Currency provider error",
                            response.getStatusCode().value());
                })
                .build();
    }
}