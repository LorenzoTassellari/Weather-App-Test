
package com.weatherapp.myweatherapp.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.weatherapp.myweatherapp.service.WeatherService;

import com.weatherapp.myweatherapp.model.CityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WeatherController.class)
@Import(WeatherService.class)
class WeatherControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private WeatherService weatherService;

  @Test
  void compareRain_ShouldReturnCorrectMessage() throws Exception {
    System.out.println("Running compareRain corrects return message tests.");
    CityInfo newyork = new CityInfo();
    newyork.setAddress("newyork");
    CityInfo.CurrentConditions newyorkConditions = newyork.new CurrentConditions();
    newyorkConditions.setConditions("Rain");
    newyork.setCurrentConditions(newyorkConditions);

    CityInfo losangeles = new CityInfo();
    newyork.setAddress("losangeles");
    CityInfo.CurrentConditions losangelesConditions = losangeles.new CurrentConditions();
    losangelesConditions.setConditions("Clear");
    losangeles.setCurrentConditions(losangelesConditions);

    when(weatherService.forecastByCity("newyork")).thenReturn(newyork);
    when(weatherService.forecastByCity("losangeles")).thenReturn(losangeles);
    when(weatherService.isRaining(newyork)).thenReturn(true);
    when(weatherService.isRaining(losangeles)).thenReturn(false);
    when(weatherService.generateRainComparisonMessage(newyork, losangeles, true, false))
      .thenReturn("It's currently raining in: New York");
    mockMvc.perform(get("/forecast/compare/rain/newyork/losangeles"))
      .andExpect(status().isOk())
      .andExpect(content().string("It's currently raining in: New York"));
    System.out.println("Completed all the compareRain tests successfully.");
  }
}