package com.mobvoi.streaming.util;

import com.mobvoi.streaming.location.GeoPoint;
import com.mobvoi.streaming.location.Location;

public class LocationUtil {

  public static Location decodeAddressToLocation(String addresses) {
    Location location = new Location();
    if (StringUtil.notNullOrEmpty(addresses)) {
      String[] address = addresses.split(",");
      location.setCountry(StringUtil.safeStringEn(address[0]));
      location.setProvince(StringUtil.safeStringEn(address[1]));
      location.setCity(StringUtil.safeStringEn(address[2]));
      location.setSublocality(StringUtil.safeStringEn(address[3]));
      location.setStreet(StringUtil.safeStringEn(address[4]));
      location.setStreetNumber(StringUtil.safeStringEn(address[5]));
      GeoPoint geoPoint = GeoPoint.parseFromString(address[6], address[7]);
      geoPoint.setCity(StringUtil.safeStringEn(address[2]));
      location.setGeoPoint(geoPoint);
    }

    return location;
  }
}
