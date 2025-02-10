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

  //getter methods
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
  //setter methods for testing
  public void setAddress(String address) {
    this.address = address;
  }

  public void setDescription(String description) {
      this.description = description;
  }

  public void setCurrentConditions(CurrentConditions currentConditions) {
      this.currentConditions = currentConditions;
  }

  public void setDays(List<Days> days) {
      this.days = days;
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

    //getter methods
    public String getCurrentTemperature() {
      return currentTemperature;
    }

    public String getSunrise() {
      return sunrise;
    }

    public String getSunset() {
      return sunset;
    }

    public String getFeelsLike() {
      return feelslike;
    }

    public String getHumidity() {
      return humidity;
    }

    public String getConditions() {
      return conditions;
    }
    
    //setter methods for testing
    public void setCurrentTemperature(String currentTemperature) {
      this.currentTemperature = currentTemperature;
    }
    
    public void setSunrise(String sunrise) {
      this.sunrise = sunrise;
    }
    
    public void setSunset(String sunset) {
      this.sunset = sunset;
    }
    
    public void setFeelsLike(String feelslike) {
      this.feelslike = feelslike;
    }
    
    public void setHumidity(String humidity) {
      this.humidity = humidity;
    }
    
    public void setConditions(String conditions) {
      this.conditions = conditions;
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
