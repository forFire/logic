package com.capcare.harbor.util;


public interface Constants {
	static final int PHONE_CODE_EXPIRE = 2; //验证码过期时间 分钟
	
	//zhws 室内地图存放的位置
//	static final String MAP_PIC_PATH = "upload"+File.separator; 
	public class PicDir{
		public static final String MAP_DIR = "/upload/map/";
		public static final String FEEDBACK_DIR = "/upload/feedback/";
		public static final String ALARM_DIR = "/upload/alarm/";
		public static final String DANGER_DIR = "/upload/danger/";
	}
	//友盟推送
	static final String APP_KEY = "56fc8632e0f55aea1f001100"; 
	static final String APP_MASTER_SECRET = "vf833t1bsghdiagobe8jzwsct7jn3lbd"; 
	
	
	static final String APP_IOS_KEY = "57c3b100e0f55a21f8001daf"; 
	static final String APP_IOS_MASTER_SECRET = "o7qbzx1e6jhcq4yfljzgeqpdjqpht1lj"; 
	
	
	//消息推送
	//通知栏提示文字
	static final String DANGER_TICKER = "推送数据"; 
	//通知标题
	static final String DANGER_TITLE = "推送数据"; 
	
	//推送类型（隐患、报警）
	static final String CAST_DANGER = "danger"; 
	static final String CAST_ALARM = "alarm"; 
	
	//推送用户拼接
	static final String ALIAS_PRE = "fire_cloud_user_"; 
	//友盟推送类型拼接
	static final String ALIAS_TYPE_PRE = "fire_cloud_org_"; 
	
	//状态 1有效 0禁用
	static final Integer STATUS_ENABLE = 1;
	static final Integer STATUS_DISABLE = 0;
	
	static final Integer STATUS_1 = 1;
	static final Integer STATUS_0 = 0;
	
	//app分配隐患
	static final Integer APP_FLAG_ASSGIGN = 1; //有
	static final Integer APP_FLAG_NOASSGIN = 0 ; //无
	static final String APP_ASSIGN_MENUKEY = "fc_danger_assign";
	
	static final String TREE_PRE = "z_";
	
}
