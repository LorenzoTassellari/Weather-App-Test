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
  @GetMapping("/forecast/compare/rain/{city1}/{city2}")
  public ResponseEntity<String> compareRain(
    @PathVariable("city1") String city1,
    @PathVariable("city2") String city2
    ) {
      CityInfo ci1 = weatherService.forecastByCity(city1);
      CityInfo ci2 = weatherService.forecastByCity(city2);
      boolean ci1Rain = weatherService.isRaining(ci1);
      boolean ci2Rain = weatherService.isRaining(ci2);
      if (ci1Rain && ci2Rain) {
        return ResponseEntity.ok("It's currently raining in both cities");
      } else if (ci1Rain) {
        return ResponseEntity.ok("It's currently raining in: " + ci1.getAddress());
      } else if (ci2Rain) {
        return ResponseEntity.ok("It's currently raining in: " + ci2.getAddress());
      } else {
        return ResponseEntity.ok("It's currently not raining in these cities.");
      }
    }
}
