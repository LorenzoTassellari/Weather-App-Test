package com.weatherapp.myweatherapp.service;
import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {
  
  @Test
  void checkCorrectDaylight() {
    String city = "milan";
    CityInfo ci = weatherService.forecastByCity(city);

  }

}