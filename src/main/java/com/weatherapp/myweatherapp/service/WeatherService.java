package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

  @Autowired
  VisualcrossingRepository weatherRepo;

  public CityInfo forecastByCity(String city) {

    return weatherRepo.getByCity(city);
  }

  public int[] getDaylightHours(CityInfo city) {

    String[] sunrise = city.getCurrentConditions().getSunrise().split(":");
    String[] sunset = city.getCurrentConditions().getSunset().split(":");
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

    int[] daylightHours1 = getDaylightHours(city1);
    int[] daylightHours2 = getDaylightHours(city2);

    for (int i = 0; i < 3; i++) {

      if (daylightHours1[i] > daylightHours2[i]) {

        return city1;
      }

      if (daylightHours1[i] < daylightHours2[i]) {

        return city2;
      }
    }
    return city1;
  }

  public Boolean isRaining(CityInfo city) {
    return city.getCurrentConditions().getConditions().contains("Rain");
  }

  public String generateRainComparisonMessage(CityInfo ci1, CityInfo ci2, boolean ci1Rain, boolean ci2Rain) {
    if (ci1Rain && ci2Rain) {
        return "It's raining in both " + ci1.getAddress() + " and " + ci2.getAddress();
    } else if (ci1Rain) {
        return "It's currently raining in: " + ci1.getAddress();
    } else if (ci2Rain) {
        return "It's currently raining in: " + ci2.getAddress();
    } else {
        return "It's not raining in either city";
    }
  }
}