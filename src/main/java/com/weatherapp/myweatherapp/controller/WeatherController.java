package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WeatherController {

  @Autowired
  WeatherService weatherService;

  @GetMapping("/forecast/{city}")
  public ResponseEntity<CityInfo> forecastByCity(@PathVariable("city") String city) {

    CityInfo ci = weatherService.forecastByCity(city);

    return ResponseEntity.ok(ci);
  }

  // TODO: given two city names, compare the length of the daylight hours and return the city with the longest day

  @GetMapping("/forecast/compare/daylight/{city1}/{city2}")
  public ResponseEntity<CityInfo> compareForecast(
    @PathVariable("city1") String city1,
    @PathVariable("city2") String city2
    ) {
    CityInfo ci1 = weatherService.forecastByCity(city1);
    CityInfo ci2 = weatherService.forecastByCity(city2);
    CityInfo returnCity = weatherService.compareDaylight(ci1, ci2);
    return ResponseEntity.ok(returnCity);
  }
  // TODO: given two city names, check which city its currently raining in
}
