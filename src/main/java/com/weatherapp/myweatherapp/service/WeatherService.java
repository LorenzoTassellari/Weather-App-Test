package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

  @Autowired
  VisualcrossingRepository weatherRepo;

  public CityInfo forecastByCity(String city) {

    return weatherRepo.getByCity(city);
  }

  public int[] getDaylight(CityInfo city) {

    String[] sunrise = city.getCurrentConditions().getsunrise().split(":");
    String[] sunset = city.getCurrentConditions().getsunset().split(":");
    int[] daylightHours = new int[3];

    for (int i = 2; i > -1; i--) {

      daylightHours[i] = daylightHours[i] + Integer.parseInt(sunset[i]) - Integer.parseInt(sunrise[i]);

      if (daylightHours[i] < 0) {

        daylightHours[i] = daylightHours[i] + 60;
        daylightHours[i - 1] = daylightHours[i - 1] - 1;


      }
    }

    return daylightHours;
  }

  public CityInfo compareDaylight(CityInfo city1, CityInfo city2) {

    int[] daylightHours1 = getDaylight(city1);
    int[] daylightHours2 = getDaylight(city2);

    for (int i = 0; i < 3; i++) {

      if (daylightHours1[i] > daylightHours2[i]) {

        return city1;
      }

      if (daylightHours1[i] < daylightHours2[i]) {

        return city2;
      }
    }
  return null;
  }
}
