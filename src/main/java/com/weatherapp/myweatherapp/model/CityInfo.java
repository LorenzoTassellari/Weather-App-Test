package com.weatherapp.myweatherapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CityInfo {

  @JsonProperty("address")
  String address;

  @JsonProperty("description")
  String description;

  @JsonProperty("currentConditions")
  CurrentConditions currentConditions;

  @JsonProperty("days")
  List<Days> days;

  public String getAddress() {
    return address;
  }

  public String getDescription() {
      return description;
  }

  public CurrentConditions getCurrentConditions() {
      return currentConditions;
  }

  public List<Days> getDays() {
      return days;
  }
  public class CurrentConditions {
    @JsonProperty("temp")
    String currentTemperature;

    @JsonProperty("sunrise")
    String sunrise;

    @JsonProperty("sunset")
    String sunset;

    @JsonProperty("feelslike")
    String feelslike;

    @JsonProperty("humidity")
    String humidity;

    @JsonProperty("conditions")
    String conditions;

    public String getCurrentTemperature() {
      return currentTemperature;
    }

    public String getsunrise() {
      return sunrise;
    }

    public String getsunset() {
      return sunset;
    }

    public String getfeelslike() {
      return feelslike;
    }

    public String gethumidity() {
      return humidity;
    }

    public String getconditions() {
      return conditions;
    }
  }

  static class Days {

    @JsonProperty("datetime")
    String date;

    @JsonProperty("temp")
    String currentTemperature;

    @JsonProperty("tempmax")
    String maxTemperature;

    @JsonProperty("tempmin")
    String minTemperature;

    @JsonProperty("conditions")
    String conditions;

    @JsonProperty("description")
    String description;

  }

}
