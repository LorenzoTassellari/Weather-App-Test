package com.weatherapp.myweatherapp.service;
import com.weatherapp.myweatherapp.model.CityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeatherServiceTest {

  @Autowired
  WeatherService weatherService;

  @Test
  void testGetDaylightForManchester() {
    System.out.println("Testing Manchester daylight hours\n");
    CityInfo manchester = weatherService.forecastByCity("manchester");
    
    System.out.println("Manchester sunrise: " + manchester.getCurrentConditions().getSunrise());
    System.out.println("Manchester sunset: " + manchester.getCurrentConditions().getSunset());
    
    int[] daylightHours = weatherService.getDaylightHours(manchester);
    
    System.out.println("Manchester daylight hours: " + Arrays.toString(daylightHours) + "\n");
    
    assertNotNull(daylightHours);
    assertEquals(3, daylightHours.length);
    assertTrue(daylightHours[0] >= 0 && daylightHours[0] <= 24);
    assertTrue(daylightHours[1] >= 0 && daylightHours[1] < 60);
    assertTrue(daylightHours[2] >= 0 && daylightHours[2] < 60);
  }
  
  @Test
  void testGetDaylightForMilan() {
    System.out.println("Testing Milan daylight hours\n");
    CityInfo milan = weatherService.forecastByCity("milan");
    
    System.out.println("Milan sunrise: " + milan.getCurrentConditions().getSunrise());
    System.out.println("Milan sunset: " + milan.getCurrentConditions().getSunset());
    
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
    
    System.out.println("Oslo sunrise: " + oslo.getCurrentConditions().getSunrise());
    System.out.println("Oslo sunset: " + oslo.getCurrentConditions().getSunset() + "\n");
    System.out.println("Rome sunrise: " + rome.getCurrentConditions().getSunrise());
    System.out.println("Rome sunset: " + rome.getCurrentConditions().getSunset() + "\n");
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
      System.out.println("Sunrise: " + cityInfo.getCurrentConditions().getSunrise());
      System.out.println("Sunset: " + cityInfo.getCurrentConditions().getSunset());
      System.out.println("Daylight hours: " + Arrays.toString(daylight) + "\n");

      assertNotNull(daylight);
      assertEquals(3, daylight.length);
      assertTrue(daylight[0] >= 0 && daylight[0] <= 24);
      assertTrue(daylight[1] >= 0 && daylight[1] < 60);
      assertTrue(daylight[2] >= 0 && daylight[2] < 60);
    }
  }


  @Test
  void testIsRaining_WithRain() {
    CityInfo city = new CityInfo();
    CityInfo.CurrentConditions conditions = city.new CurrentConditions();
    conditions.setConditions("Rain");
    city.setCurrentConditions(conditions);

    Boolean result = weatherService.isRaining(city);

    System.out.println("Conditions: " + conditions.getConditions());
    System.out.println("Is it raining? " + result);
    assertTrue(result);
    }

  @Test
  void testIsRaining_WithoutRain() {
    CityInfo city = new CityInfo();
    CityInfo.CurrentConditions conditions = city.new CurrentConditions();
    conditions.setConditions("Clear");
    city.setCurrentConditions(conditions);

    Boolean result = weatherService.isRaining(city);

    System.out.println("Conditions: " + conditions.getConditions());
    System.out.println("Is it raining? " + result);
    assertFalse(result);
    }

  @Test
  void testIsRaining_WithMixedConditions() {
    CityInfo city = new CityInfo();
    CityInfo.CurrentConditions conditions = city.new CurrentConditions();
    conditions.setConditions("Rain, Overcast");
    city.setCurrentConditions(conditions);

    Boolean result = weatherService.isRaining(city);

    System.out.println("Conditions: " + conditions.getConditions());
    System.out.println("Is it raining? " + result);
    assertTrue(result);
    }

  @Test
  void testIsRaining_RealCities() {
    String[] cities = {"london", "paris", "berlin", "madrid", "rome"};
    
    System.out.println("\nTesting real cities for rain...");
    
    for (String cityName : cities) {
      CityInfo city = weatherService.forecastByCity(cityName);
      Boolean isRaining = weatherService.isRaining(city);
      
      System.out.println("\n" + cityName.toUpperCase());
      System.out.println("Conditions: " + city.getCurrentConditions().getConditions());
      System.out.println("Is it raining? " + isRaining);
      
      assertNotNull(isRaining);
    }
  }
}