//Copyright 2012 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming.location;

import com.mobvoi.streaming.util.StringUtil;

/**
 * Geo point, it only contains the lat and lng, city
 * 
 * @author Jimmy Chen, <ggm19890303@live.cn>
 */
public class GeoPoint {
  private String city = StringUtil.UNKNOW_STRING_EN;
  private double lat;
  private double lng;

  public GeoPoint() {
    this.lat = 0.0;
    this.lng = 0.0;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public boolean isValid() {
    if (lat == 0.0 || lng == 0.0) {
      return false;
    } else {
      return true;
    }
  }
  
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public static GeoPoint parseFromDouble(double lat, double lng) {
    GeoPoint geopoint = new GeoPoint();
    geopoint.lat = lat;
    geopoint.lng = lng;
    return geopoint;
  }

  public static GeoPoint parseFromString(String lat, String lng) {
    try {
      return parseFromDouble(Double.valueOf(lat), Double.valueOf(lng));
    } catch (Exception e) {
      return parseFromDouble(0.0, 0.0);
    }
  }

  public static GeoPoint parseFromInt(int lat, int lng) {
    return parseFromDouble(lat / 1.0E6, lng / 1.0E6);
  }

  public static GeoPoint parseFromStringInt(String lat, String lng) {
    try {
      return parseFromInt(Integer.valueOf(lat), Integer.valueOf(lng));
    } catch (Exception e) {
      return parseFromDouble(0.0, 0.0);
    }
  }
  
  /**
   * the geopoint str must layout by lat,lng, else return a invalid geopoint
   */
  public static GeoPoint parseFromStringComma(String geopointStr) {
    try {
      String[] points = geopointStr.split(",");
      if(points.length == 2) { 
      return parseFromString(points[0], points[1]);
      }
    } catch (Exception e) {
    }
    return parseFromDouble(0.0, 0.0);
  }

  @Override
  public String toString() {
    return "[lat=" + lat + ", lng=" + lng + "]";
  }
}
