package org.mo.entity;

import java.io.Serializable;

/**
 * Created by moziqi on 2015/1/19 0019.
 */
public class WeatherInfo implements Serializable {

    private String city;

    private String temp;

    private String time;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
