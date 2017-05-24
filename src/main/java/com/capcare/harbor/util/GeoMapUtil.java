package com.capcare.harbor.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import module.util.HttpContent;
import module.util.JsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoMapUtil {

	private static final Logger log = LoggerFactory.getLogger(GeoMapUtil.class);
	
	private static Pattern gaddr= Pattern.compile("<formatted_address>([^<]+)</formatted_address>");
	private static final  String BAIDU_AK = "A00ea0faad22ed06ae0b537d30ff63ff";

	
	/** gps坐标转换 为 百度坐标 */
	private static Double[] gps2baidu(Double lngDouble, Double latDouble) {
		Double lng = 0D;
		Double lat = 0D;
		try {
			String url = "http://api.map.baidu.com/geoconv/v1/?ak="+BAIDU_AK+"&from=1&to=5&coords="+lngDouble+","+latDouble;
			String content = HttpContent.get(url);
			Map<String, Object> map = JsonUtils.str2Map(content);
			List<Map> list = (List) map.get("result");
			Map point=list.get(0);
			 lng = (Double) point.get("x");
			 lat = (Double) point.get("y");
			
		} catch (Exception ex) {
			log.error("gps2baidu", ex);
		}
		Double[] lngLat = new Double[] { lng, lat };
		return lngLat;
	}
	
	 
     
     /**
      * gps转百度地址
     * @param lng
     * @param lat
     * @return
     */
    public static String getAddrByBaidu(Double lng, Double lat) {
 		String rs = "";
 		String location = lat + "," + lng;
 		String url = "http://api.map.baidu.com/geocoder/v2/?ak=" + BAIDU_AK + "&coordtype=wgs84ll&location=" + location
 				+ "&output=json&pois=0";
 		try {
 			String content=HttpContent.get(url);
 			Map<String, Object> map = JsonUtils.str2Map(content);
 			Map<String, Object> result = (Map<String, Object>) map.get("result");
 			if(result!=null){
 				Object address= result.get("formatted_address");
 	 			if(address!=null){
 	 				rs=(String) address;
 	 			}
 			}
 			
 		} catch (Exception e) {
 			log.error("get addr from baidu failed!");
 		}
 		return rs;
 	}
 	
 	/**
 	 * gps转google地址
 	 * @param lat
 	 * 纬度
 	 * @param lng
 	 * 经度
 	 * @return
 	 * 英文地址
 	 */
 	public static String getAddrByGoogle(Double lng,Double lat) {
 		String rs = "";
 		String location = lat + "," + lng;
 		String url = "http://maps.googleapis.com/maps/api/geocode/xml?latlng="+location+"&sensor=false";
 		try {
 			String info=HttpContent.get(url);
 			if(info!=null){
 				Matcher m = gaddr.matcher(info);
 	 			if (m.find()){
 	 				rs= m.group(1);
 	 		    }
 			}
 			
 		} catch (Exception e) {
 			log.error("getAddrByGoogle", e);
 		}
 		return rs;
 	}
 	
 	

     
}
