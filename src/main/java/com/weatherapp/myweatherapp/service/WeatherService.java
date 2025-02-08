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

  public CityInfo compareDaylight(CityInfo city1, CityInfo city2) {

    String[] sunrise1 = city1.getCurrentConditions().getsunrise().split(":");
    String[] sunset1 = city1.getCurrentConditions().getsunset().split(":");
    String[] sunrise2 = city2.getCurrentConditions().getsunrise().split(":");
    String[] sunset2 = city2.getCurrentConditions().getsunset().split(":");

    int[] daylightHours1 = new int[3];
    int[] daylightHours2 = new int[3];
    
    for (int i = 2; i > -1; i--) {

      daylightHours1[i] = Integer.parseInt(sunset1[i]) - Integer.parseInt(sunrise1[i]);

      if (daylightHours1[i] < 0) {

        daylightHours1[i] = daylightHours1[i] + 60;
        daylightHours1[i - 1] = daylightHours1[i - 1] - 1;

      }

      daylightHours2[i] = Integer.parseInt(sunset2[i]) - Integer.parseInt(sunrise2[i]);

      if (daylightHours2[i] < 0) {

        daylightHours2[i] = daylightHours2[i] + 60;
        daylightHours2[i - 1] = daylightHours2[i - 1] - 1;

      }
    }

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
