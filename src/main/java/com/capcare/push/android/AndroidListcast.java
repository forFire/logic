package com.capcare.push.android;

import com.capcare.push.AndroidNotification;


public class AndroidListcast extends AndroidNotification {
	public AndroidListcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "listcast");	
	}
	
	public void setDeviceToken(String token) throws Exception {
    	setPredefinedKeyValue("device_tokens", token);
    }

}