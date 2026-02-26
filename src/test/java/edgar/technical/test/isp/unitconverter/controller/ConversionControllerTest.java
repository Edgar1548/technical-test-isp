package edgar.technical.test.isp.unitconverter.controller;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import edgar.technical.test.isp.unitconverter.service.ConversionService;
import edgar.technical.test.isp.unitconverter.dto.response.ConversionResponse;
import edgar.technical.test.isp.unitconverter.service.model.ConversionType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConversionController.class)
public class ConversionControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private ConversionService service;

        @Test
        void shouldReturn200() throws Exception {

                Mockito.when(service.convert(Mockito.any()))
                                .thenReturn(
                                                new ConversionResponse(
                                                                ConversionType.LENGTH,
                                                                "m",
                                                                "ft",
                                                                BigDecimal.ONE,
                                                                BigDecimal.TEN,
                                                                OffsetDateTime.now()));

                mockMvc.perform(post("/api/v1/conversions")
                                .contentType("application/json")
                                .content("""
                                                {
                                                  "type": "LENGTH",
                                                  "from": "m",
                                                  "to": "ft",
                                                  "value": 1
                                                }
                                                """))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.convertedValue").value(10));
        }
}
