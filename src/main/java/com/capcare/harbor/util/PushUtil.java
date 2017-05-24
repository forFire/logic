package com.capcare.harbor.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capcare.harbor.service.logic.PushAlarmToApp;
import com.capcare.push.AndroidNotification;
import com.capcare.push.PushClient;
import com.capcare.push.android.AndroidCustomizedcast;
import com.capcare.push.android.AndroidListcast;
import com.capcare.push.ios.IOSUnicast;


/**
 * This Class Function Description:友盟消息推送 
 * @author   张万胜
 * @version  2016年5月13日 下午4:01:13
 * @since    JDK 1.7
 */
public class PushUtil {
	
	private PushClient client = new PushClient();
	private static Logger logger = LoggerFactory.getLogger(PushUtil.class);
	/**
	 * Method Description:向 Android设备以“定制方式”推送消息 
	 * @param alias：推送对象，多个alias用英文逗号隔开
	 * @param aliasType：类型
	 * @param text：推送内容
	 * @return
	 * @throws Exception
	 * @author   张万胜
	 * @version  2016年5月13日 下午3:56:08
	 * @since    JDK 1.7
	 */
	public boolean sendAndroidCustomizedcast(String alias, String aliasType,CastData cast){
		
		AndroidCustomizedcast customizedcast;
		try {
			customizedcast = new AndroidCustomizedcast(Constants.APP_KEY,Constants.APP_MASTER_SECRET);
			customizedcast.setAlias(alias, aliasType);
			customizedcast.setTicker(Constants.DANGER_TICKER);
			customizedcast.setTitle(Constants.DANGER_TITLE);
			customizedcast.setText(JsonUtil.toJson(cast));
			customizedcast.goAppAfterOpen();
			customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
			customizedcast.setProductionMode();
			return client.send(customizedcast);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean sendListcast(String deviceTokens,CastData cast,String key,Integer num){
		boolean flag = true;
		//根据deviceToken判断是IOS 还是Android
		String deviceTokensArr[] = deviceTokens.split(",");
		for(String deviceToken : deviceTokensArr){
			if(!StringUtils.isEmpty(deviceToken)){
				//ios
				if (deviceToken.length() == 64) {
					
					logger.info("ios===========================================deviceToken=="+deviceToken+"===num================="+num);
					flag = sendIOSUnicast(deviceToken, key, num);
					if(flag == false){
						return false;
					}
				}else{
					logger.info("android===========================================deviceToken=="+deviceToken+"===num================="+num);

					//android
					flag = sendAndroidListcast(deviceToken,cast);
					if(flag == false){
						return false;
					}
					
				}
			}
		}
		return flag;
	}
	
	
	
	/**
	 * Method Description:按照deviceToken进行消息推送
	 * @param diviceTokens：推送对象，多个deviceToken用英文逗号拼接，最多500个 
	 * @param cast：推送内容
	 * @return：true成功，fals推送失败；
	 * @author   张万胜
	 * @version  2016年7月4日 上午11:08:01
	 * @since    JDK 1.7
	 */
	/** Method Description 
	 * @param deviceTokens
	 * @param cast
	 * @return
	 * @author   张万胜
	 * @version  2016年7月13日 下午4:23:10
	 * @since    JDK 1.7
	 */
	public boolean sendAndroidListcast(String deviceTokens,CastData cast){
		
		try {
			AndroidListcast listcast;
			listcast = new AndroidListcast(Constants.APP_KEY,Constants.APP_MASTER_SECRET);
			//设置diviceToken
			listcast.setDeviceToken(deviceTokens);
			listcast.setTicker(Constants.DANGER_TICKER);
			listcast.setTitle(Constants.DANGER_TITLE);
//			listcast.setText(JsonUtil.toJson(cast));
			listcast.goAppAfterOpen();
//			listcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
			listcast.setDisplayType(AndroidNotification.DisplayType.MESSAGE);
			listcast.setCustomField(JsonUtil.toJson(cast));
			logger.info("listcast================"+listcast);

			listcast.setProductionMode();
			//(display_type=notification)生效。可以配合通知到达后,打开App,打开URL,打开Activity使用，目前不需要。
			//listcast.setExtraField("test", "helloworld");
			return client.send(listcast);
		} catch (Exception e) {
			logger.info("e=========================>"+e);
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 *发送到IOS  
	 *单个deviceToken
	 */
	public boolean sendIOSUnicast(String deviceToken,String key,Integer num){
		
		try{
			IOSUnicast unicast = new IOSUnicast(Constants.APP_IOS_KEY,Constants.APP_IOS_MASTER_SECRET);
			// TODO Set your device token
			unicast.setDeviceToken(deviceToken);
			// 必填   
			unicast.setAlert("收到一条报警信息。");
			unicast.setBadge(0);
			unicast.setSound( "default");
			// TODO set 'production_mode' to 'true' if your app is under production mode
			unicast.setTestMode();
			// Set customized fields
//			unicast.setCustomizedField(JsonUtil.toJson(cast));
			unicast.setCustomizedField(key, String.valueOf(num));
			
			return client.send(unicast);
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {
		PushUtil push = new PushUtil();
		CastData cast = new CastData();
		cast.setNum(1);
		cast.setType(Constants.CAST_ALARM);
		push.sendAndroidListcast("AvGXnNwxyNv5s-q-Jls0UvxsM5iwrFG44azJSeRINC7x", cast);
	}

}
