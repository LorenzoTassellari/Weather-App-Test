package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;
import com.weatherapp.myweatherapp.exception.CityNotFoundException;
import com.weatherapp.myweatherapp.exception.WeatherServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class WeatherController {

  private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

  @Autowired
  WeatherService weatherService;

  @GetMapping("/forecast/{city}")
  public ResponseEntity<CityInfo> forecastByCity(@PathVariable("city") String city) {
    try {
      CityInfo ci = weatherService.forecastByCity(city);
      if (ci == null) {
        throw new CityNotFoundException("No forecast found for city: " + city);
      }
      return ResponseEntity.ok(ci);
    } catch (CityNotFoundException e) {
      logger.error("City not found: {}", city, e);
      throw e;
    } catch (Exception e) {
        logger.error("Error getting forecast for city: " + city, e);
        throw new WeatherServiceException("Error retrieving forecast for " + city, e);
    }
  }

  @GetMapping("/forecast/compare/daylight/{city1}/{city2}")
  public ResponseEntity<CityInfo> compareForecast(
    @PathVariable("city1") String city1,
    @PathVariable("city2") String city2
  ) {
    try {
      CityInfo ci1 = weatherService.forecastByCity(city1);
      CityInfo ci2 = weatherService.forecastByCity(city2);
      
      if (ci1 == null || ci2 == null) {
        throw new CityNotFoundException("One or both cities not found: " + city1 + ", " + city2);
      }
      
      CityInfo returnCity = weatherService.compareDaylight(ci1, ci2);
      return ResponseEntity.ok(returnCity);
    } catch (CityNotFoundException e) {
      logger.error("City not found while comparing daylight: " + city1 + " and " + city2, e);
      throw e;
    } catch (Exception e) {
      logger.error("Error comparing daylight between cities: " + city1 + " and " + city2, e);
      throw new WeatherServiceException("Error comparing daylight between " + city1 + " and " + city2, e);
    }
  }

  @GetMapping("/forecast/compare/rain/{city1}/{city2}")
  public ResponseEntity<String> compareRain(
    @PathVariable("city1") String city1,
    @PathVariable("city2") String city2
  ) {
    try {
      CityInfo ci1 = weatherService.forecastByCity(city1);
      CityInfo ci2 = weatherService.forecastByCity(city2);
      
      if (ci1 == null || ci2 == null) {
        throw new CityNotFoundException("One or both cities not found: " + city1 + ", " + city2);
      }
      
      boolean ci1Rain = weatherService.isRaining(ci1);
      boolean ci2Rain = weatherService.isRaining(ci2);
      String responseMessage = weatherService.generateRainComparisonMessage(ci1, ci2, ci1Rain, ci2Rain);
      return ResponseEntity.ok(responseMessage);
    } catch (CityNotFoundException e) {
      logger.error("City not found while comparing rain: {} and {}", city1, city2, e);
      throw e;
    } catch (Exception e) {
      logger.error("Error comparing rain between cities: {} and {}", city1, city2, e);
      throw new WeatherServiceException("Error comparing rain between " + city1 + " and " + city2, e);
    }
  }
  // Exception Handlers
  @ExceptionHandler(CityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleCityNotFoundException(CityNotFoundException e) {
    ErrorResponse error = new ErrorResponse(
      HttpStatus.NOT_FOUND.value(),
      "City Not Found",
      e.getMessage()
    );
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
    ErrorResponse error = new ErrorResponse(
      HttpStatus.BAD_REQUEST.value(),
      "Invalid Input",
      e.getMessage()
    );
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(WeatherServiceException.class)
  public ResponseEntity<ErrorResponse> handleWeatherServiceException(WeatherServiceException e) {
    ErrorResponse error = new ErrorResponse(
      HttpStatus.INTERNAL_SERVER_ERROR.value(),
      "Weather Service Error",
      e.getMessage()
    );
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
    ErrorResponse error = new ErrorResponse(
      HttpStatus.INTERNAL_SERVER_ERROR.value(),
      "Internal Server Error",
      "An unexpected error occurred"
    );
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
class ErrorResponse {
  private int status;
  private String error;
  private String message;

  public ErrorResponse(int status, String error, String message) {
    this.status = status;
    this.error = error;
    this.message = message;
  }

  // Getters and setters
  public int getStatus() { return status; }
  public void setStatus(int status) { this.status = status; }
  public String getError() { return error; }
  public void setError(String error) { this.error = error; }
  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }
}