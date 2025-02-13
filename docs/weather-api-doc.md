# Weather API Documentation

## Overview

This API provides weather forecasts and comparisons for different cities. It allows users to retrieve weather information, compare daylight duration, and check rainfall differences between two cities.

### Base URL

```
http://localhost:8080
```

## Endpoints

The api is not deployed yet, however it's currently available locally with the following endpoints.

### 1. Get Weather Forecast by City

**Endpoint:**

http://localhost:8080/forecast/city

**Description:** Retrieves the weather forecast for a specific city.

**Path Parameters:**

- `city` (string) – The name of the city.

**Response:**

```json
{
  "address": "london",
  "description": "Warming up with no rain expected.",
  "currentConditions": {
    "feelsLike": "39.2",
    "temp": "39.2",
    "sunrise": "07:17:52",
    "sunset": "17:12:25",
    "feelslike": "39.2",
    "humidity": "71.1",
    "conditions": "Overcast"
  },  "days": [
    {
      "datetime": "2025-02-13",
      "temp": "39.0",
      "tempmax": "40.2",
      "tempmin": "36.0",
      "conditions": "Overcast",
      "description": "Cloudy skies throughout the day."
    },
    .
    .
    .
     {
      "datetime": "2025-02-27",
      "temp": "47.8",
      "tempmax": "50.8",
      "tempmin": "43.9",
      "conditions": "Partially cloudy",
      "description": "Partly cloudy throughout the day."
    }
  ]
}   
```

**Errors:**

- `404 Not Found` – City not found.
- `500 Internal Server Error` – Error retrieving the forecast.

#### **Design choices:** Not many choices were made here, as the skeleton code was already provided. Some error handling was added.
---

### 2. Compare Daylight Duration Between Two Cities

**Endpoint:**

```
http://localhost:8080/forecast/compare/daylight/{city1}/{city2}
```

**Description:** Compares daylight hours between two cities and returns the one with longer daylight.

**Path Parameters:**

- `city1` (string) – The first city.
- `city2` (string) – The second city.

**Response:** This endpoint will return the json file of the city with the longest daylight.

```json
{
  "address": "london",
  "description": "Warming up with no rain expected.",
  "currentConditions": {
    "feelsLike": "39.2",
    "temp": "39.2",
    "sunrise": "07:17:52",
    "sunset": "17:12:25",
    "feelslike": "39.2",
    "humidity": "71.1",
    "conditions": "Overcast"
  },  "days": [
    {
      "datetime": "2025-02-13",
      "temp": "39.0",
      "tempmax": "40.2",
      "tempmin": "36.0",
      "conditions": "Overcast",
      "description": "Cloudy skies throughout the day."
    },
    .
    .
    .
     {
      "datetime": "2025-02-27",
      "temp": "47.8",
      "tempmax": "50.8",
      "tempmin": "43.9",
      "conditions": "Partially cloudy",
      "description": "Partly cloudy throughout the day."
    }
  ]
}   
```

**Errors:**

- `404 Not Found` – One or both cities not found.

#### **Design choices:** This endpoint will return a cityinfo object, in this case the return type is guaranteed. As there will always be a city with a longer sunlight. If both cities have the same amount of sunlight, the endpoint will just return the first one.
---

### 3. Compare Rain Conditions Between Two Cities

**Endpoint:**

```
http://localhost:8080/forecast/compare/rain/{city1}/{city2}
```

**Description:** Compares rain conditions between two cities and returns a message describing the difference.


**Path Parameters:**

- `city1` (string) – The first city.
- `city2` (string) – The second city.

**Response:**

```String
"It's raining in both "city1" and "city2""
"It's currently raining in "city1""
"It's currently raining in "city2""
"It's not raining in either city"
```

**Errors:**

- `404 Not Found` – One or both cities not found.

#### **Design choices:** I made the decision to return a string for this endpoint, as it's more robust than returning an optional value. If needed a cityinfo object can also be returned. However, in the cases where both or neither of the 2 cities it's currently raining in, the return object is not obvious anymore.

---

### How the Code Works

#### Project Structure

- The project follows a standard Spring Boot architecture, organizing code into controllers, services, models, exceptions, and repositories:

- Controller (WeatherController): Handles API requests and responses.

- Service (WeatherService): Contains business logic and interacts with the repository.

- Repository (VisualcrossingRepository): Retrieves weather data from an external source.

- Model (CityInfo): Represents weather data for a city.

- Exception Handling: Custom exceptions like CityNotFoundException ensure proper error handling.

#### Flow of Execution

1. The Controller (WeatherController) receives the HTTP request.

2. It calls the Service (WeatherService), which processes the request and fetches data from VisualcrossingRepository.

3. The Repository fetches weather data and returns a CityInfo object.

4. The Service processes the data (e.g., calculating daylight hours, checking for rain).

5. The Controller returns the processed result as an HTTP response.

#### Key Functionalities

- Fetching Weather Data: forecastByCity(String city) retrieves weather info using VisualcrossingRepository.

- Daylight Comparison: compareDaylight(CityInfo city1, CityInfo city2) calculates which city has more daylight.

- Rain Comparison: isRaining(CityInfo city) checks if it’s raining, and generateRainComparisonMessage(...) formats a message accordingly.
---

## Error Handling

All endpoints return standard HTTP status codes:

- `200 OK` – Successful response.
- `404 Not Found` – The requested city or cities were not found.
- `500 Internal Server Error` – An unexpected error occurred.

---
---
