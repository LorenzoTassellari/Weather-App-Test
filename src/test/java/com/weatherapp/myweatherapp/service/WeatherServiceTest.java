package com.weatherapp.myweatherapp.service;
import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeatherServiceTest {

  @Autowired
  WeatherService weatherService;

  // Simple test to check basic implementatio of the get day light method
  @Test
  void testGetDaylight() {
      CityInfo cityInfo = weatherService.forecastByCity("london");
      int[] daylightHours = weatherService.getDaylight(cityInfo);
      assertNotNull(daylightHours);
      assertEquals(3, daylightHours.length);
      System.out.println(Arrays.toString(daylightHours));
      assertEquals(9, daylightHours[0]);
      assertEquals(36, daylightHours[1]);
      assertEquals(15, daylightHours[2]);
  }
/*
@Test
void testCompareDaylight() {

}
*/
}