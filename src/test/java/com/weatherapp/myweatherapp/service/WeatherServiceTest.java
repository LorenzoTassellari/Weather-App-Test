package com.weatherapp.myweatherapp.service;
import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;
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
      int[] daylightHours = weatherService.getDaylightHours(cityInfo);
      assertNotNull(daylightHours);
      assertEquals(3, daylightHours.length);
      assertEquals(9, daylightHours[0]);
      assertEquals(36, daylightHours[1]);
      assertEquals(15, daylightHours[2]);
  }
  @Test
  void testGetDaylightForMilan() {
      System.out.println("Testing Milan daylight hours\n");
      CityInfo milan = weatherService.forecastByCity("milan");
      
      System.out.println("Milan sunrise: " + milan.getCurrentConditions().getsunrise());
      System.out.println("Milan sunset: " + milan.getCurrentConditions().getsunset());
      
      int[] daylightHours = weatherService.getDaylightHours(milan);
      
      System.out.println("Milan daylight hours: " + Arrays.toString(daylightHours) + "\n");
      
      assertNotNull(daylightHours);
      assertEquals(3, daylightHours.length);
      assertTrue(daylightHours[0] >= 0 && daylightHours[0] <= 24);
      assertTrue(daylightHours[1] >= 0 && daylightHours[1] < 60);
      assertTrue(daylightHours[2] >= 0 && daylightHours[2] < 60);
  }

  @Test
  void testCompareDaylightRealCities() {
      System.out.println("Testing daylight comparison between Oslo and Rome\n");
      
      CityInfo oslo = weatherService.forecastByCity("oslo");
      CityInfo rome = weatherService.forecastByCity("rome");
      
      System.out.println("Oslo sunrise: " + oslo.getCurrentConditions().getsunrise());
      System.out.println("Oslo sunset: " + oslo.getCurrentConditions().getsunset() + "\n");
      System.out.println("Rome sunrise: " + rome.getCurrentConditions().getsunrise());
      System.out.println("Rome sunset: " + rome.getCurrentConditions().getsunset() + "\n");
      System.out.println("Oslo daylight hours: " + Arrays.toString(weatherService.getDaylightHours(oslo)));
      System.out.println("Rome daylight hours: " + Arrays.toString(weatherService.getDaylightHours(rome)) + "\n");
      
      CityInfo cityWithMoreDaylight = weatherService.compareDaylight(oslo, rome);
      
      System.out.println("City with more daylight: " + cityWithMoreDaylight.getAddress() + "\n");
      
      assertNotNull(cityWithMoreDaylight);
  }

  @Test
  void testMultipleCities() {
      String[] cities = {"london", "paris", "berlin", "madrid", "rome"};
      
      System.out.println("\nTesting multiple European cities\n");
      
      for (String city : cities) {
          CityInfo cityInfo = weatherService.forecastByCity(city);
          int[] daylight = weatherService.getDaylightHours(cityInfo);
          
          System.out.println(city.toUpperCase());
          System.out.println("Sunrise: " + cityInfo.getCurrentConditions().getsunrise());
          System.out.println("Sunset: " + cityInfo.getCurrentConditions().getsunset());
          System.out.println("Daylight hours: " + Arrays.toString(daylight) + "\n");

          assertNotNull(daylight);
          assertEquals(3, daylight.length);
          assertTrue(daylight[0] >= 0 && daylight[0] <= 24);
          assertTrue(daylight[1] >= 0 && daylight[1] < 60);
          assertTrue(daylight[2] >= 0 && daylight[2] < 60);
      }
  }
}