package edgar.technical.test.isp.unitconverter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edgar.technical.test.isp.unitconverter.dto.request.ConversionRequest;
import edgar.technical.test.isp.unitconverter.dto.response.ConversionResponse;
import edgar.technical.test.isp.unitconverter.service.ConversionService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/conversions")
public class ConversionController {
    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ConversionResponse> convert(@Valid @RequestBody ConversionRequest request) {
        return ResponseEntity.ok(conversionService.convert(request));
    }

    @PostMapping()
    public ResponseEntity<ConversionResponse> convertParams(
            @Valid ConversionRequest request) {

        return ResponseEntity.ok(conversionService.convert(request));
    }
}
