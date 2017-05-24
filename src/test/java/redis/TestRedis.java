package redis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TestRedis {

	public static void main(String[] args) {
//		String config = "context.xml";
//		String[] paths = new String[] { config};
//		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(paths);

//		/IosTokenCache redisService = (IosTokenCache)context.getBean("redisService");
		
//		Position posi = redisService.getPosition("354188046973397");
//		
//		System.out.println(posi.toString());
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date d = new Date();
//		d.setTime(1559849642000L);
//		System.out.println(format.format(d));
		try {
			Date d1 = format.parse("2014-05-01 00:00:00");
			System.out.println(d1.getTime());
			
			Date d2 = format.parse("2010-04-01 00:00:00");
			System.out.println(d2.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
//		Date d2 = null;
//		try {
//			d1 = format.parse("2014-04-02 00:00:00");
//			d2 = format.parse("2014-04-03 00:00:00");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 
//		
//		System.out.println(d1.getTime());
//		System.out.println(d2.getTime());
		
	}
}
