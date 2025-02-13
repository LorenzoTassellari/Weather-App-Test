package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;
import com.weatherapp.myweatherapp.exception.CityNotFoundException;
import com.weatherapp.myweatherapp.exception.WeatherServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
      logger.error("City not found while comparing rain: " + city1 + ", " + city2, e);
      throw e;
    }
  }
}