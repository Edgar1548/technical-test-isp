# Unit Converter API

Backend Developer Technical Challenge

API REST desarrollada en Java 21 + Spring Boot 3.5 para conversión de unidades y monedas.

---

## Live API

Hice deploy del API con herramientas AWS, especificamente ElasticBeansTalk y CloudFront (para tener HTTPS temporal). Se puede probar la API usando el siguiente URL como base: 

https://d2vup220m1rc4k.cloudfront.net

---

## Características

La API permite convertir entre:

1. Longitud (metros, pulgadas, pies)
2. Peso (kilogramos, libras, onzas)
3. Temperatura (Celsius, Fahrenheit)
4. Moneda (USD, PEN, EUR, etc.) usando API externa ExchangeRate-API

Los datos pueden enviarse tanto en el body (JSON) como mediante query parameters. Además todos los errores se devuelven en ingles para evitar lo mas posible "spanglish"

---

## Arquitectura

Se aplicaron:

- Principios REST
- Strategy Pattern
- Factory Pattern
- Global Exception Handling
- DTO separation
- External API Client desacoplado
- Validaciones con Bean Validation
- Tests unitarios con JUnit + Mockito

---

## Patrones Utilizados

### Strategy Pattern
Cada tipo de conversión tiene su propia implementación:

- LengthConversionStrategy
- WeightConversionStrategy
- TemperatureConversionStrategy
- CurrencyConversionStrategy

### Factory Pattern
`ConversionStrategyFactory` selecciona la estrategia adecuada según el tipo.

---

## Supported Types

### 🔹 LENGTH
- meter, m
- inch, in
- feet, ft

### 🔹 WEIGHT
- kilogram, kg
- libra, pound, lb, lbs
- onza, ounce, oz

### 🔹 TEMPERATURE
- celsius, c, °c
- fahrenheit, f, °f

### 🔹 CURRENCY
- ISO 4217 codes (USD, PEN, EUR, GBP, etc.)

---

## Endpoint Principal

```
POST /api/v1/conversions
```

### Request (JSON body)

```json
{
  "type": "LENGTH",
  "from": "meter",
  "to": "feet",
  "value": 10
}
```

### Request (Query Parameters)

También es posible enviar los parámetros mediante query params:

```
POST /api/v1/conversions?type=LENGTH&from=meter&to=feet&value=10
```


### Response

```json
{
  "type": "LENGTH",
  "from": "meter",
  "to": "feet",
  "originalValue": 10,
  "convertedValue": 32.8084,
  "timestamp": "2026-02-25T12:00:00Z"
}
```

---

## Tests

Se creo una serie de test que se pueden ejecutar con el comando

```
mvn test
```

Incluye:

- Unit tests para cada Strategy
- Tests de Factory
- Tests de Service
- Tests de Controller con MockMvc
- Tests del cliente externo mockeado

---

## Configuración

### Variables necesarias

Para conversión de moneda:


```
CURRENCY_API_KEY=your_api_key
```

---

## Docker

### Build

```
docker build -t unit-converter-api .
```

### Run

```
docker run -p 8080:8080 \
-e CURRENCY_API_KEY=your_api_key \
unit-converter-api
```

Acceder mediante:
```
http://localhost:8080
```

---

## DevContainer

El proyecto incluye `.devcontainer`, por lo que es compatible con devContainers y se puede abrir directamente en VS Code, Cursor o editor similares que contenga un plugin de DevContainers.

---

## Posibles Mejoras Futuras

- Cache para tasas de moneda
- Logging estructurado
- Rate limiting
- Tests de integración completos
- Posibilidad de elegir entre diferentes providers para la tasacion de monedas.

---

