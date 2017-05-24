package com.capcare.harbor.service.location;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.capcare.harbor.model.Cell;
import com.capcare.harbor.service.cache.CellCache;

//基站定位实现类
@Component
public class LocationService {
	
	private static Logger logger = LoggerFactory.getLogger(LocationService.class);
	public static double latDis1 = 111; // 纬度1度大约相差111Km
	//private final static double EARTH_RADIUS = 6378137.0;

	private static final double pi = 3.1415926535898;
	//private static final double X0 = 8;
	private static final double K = 3;// 98899
	private static final double iPI = pi / 180.0;
	private static final double a = 6378137.0;// WGS84:版本长半轴
	private static final double f = 1 / 298.257223563;// WGS84:版本扁率
	private static final int ZoneWide = 6; // //带宽

	@Resource
	private CellCache cellCache;

	private PlaneCoordinate planceOne1;

	private PlaneCoordinate planceOne2;

	private PlaneCoordinate planceTwo1;

	private PlaneCoordinate planceTwo2;
	
	private PlaneCoordinate planceThree1;

	private PlaneCoordinate planceThree2;
	
	/**
	 * ?*? @Title: getDistance ?*? @Description: 根据rssi计算出与基站距离 KM ?*? @author
	 * jf.gong? DateTime 2014年5月19日 下午2:50:50 ?*? @return double ?*? @param
	 * rssiVal ?*? @return ?
	 */
	public double getDistance(double rssiVal) {
		/*
		 * ?* RSSI=发射功率(Pt)+天线增益(Pf)一路径损耗(PL(d)) 说是根据该公式可以推算出距离与 RSSI 的关系公式： D =
		 * 10^((Pt+Pf-RSSI-PL(d0)-X0)/(10*K))
		 * 
		 * 
		 * 
		 * ?*
		 */

		// return (10*((195-rssiVal)/(10 * K)))*0.001;
		// return (rssiVal+X0)*K*0.001;
		// return Math.pow(10,(-rssiVal-97.5239)/(38));
		return Math.pow(10, (Math.abs(rssiVal) - 60) / (10 * K)) * 0.001;
	}

	/**
	 * ?*? @Title: getWantDouble ?*? @Description: double保留指定小数位 ?*? @author
	 * jf.gong? DateTime 2014年5月19日 下午7:48:42 ?*? @return double ?*? @param val
	 * ?*? @param unit ?*? @return ?
	 */
	private double getWantDouble(double val, int unit) {
		BigDecimal bg = new BigDecimal(val);
		double re_value = bg.setScale(unit, BigDecimal.ROUND_HALF_UP).doubleValue();
		return re_value;
	}

	/**
	 * ?*? @Title: distance ?*? @Description: 两坐标距离计算 ?*? @author jf.gong?
	 * DateTime 2014年5月30日 下午3:40:34 ?*? @return double ?*? @param lat_a ?*? @param
	 * lng_a ?*? @param lat_b ?*? @param lng_b ?*? @return ?
	 */
//	private static double distance(double lat_a, double lng_a, double lat_b, double lng_b) {
//		double radLat1 = (lat_a * Math.PI / 180.0);
//		double radLat2 = (lat_b * Math.PI / 180.0);
//		double a = radLat1 - radLat2;
//		double b = (lng_a - lng_b) * Math.PI / 180.0;
//		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
//				* Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
//		s = s * EARTH_RADIUS;
//		s = Math.round(s * 10000) / 10000;
//		return s;
//	}

	/**
	 * ?*? @Title: getPlanCoordinate ?*? @Description: 由经纬度反算成高斯投影坐标 ?*? @author
	 * jf.gong? DateTime 2014年5月30日 下午3:39:06 ?*? @return PlaneCoordinate ?*? @param
	 * longitude ?*? @param latitude ?*? @return ?
	 */
	private PlaneCoordinate getPlanCoordinate(double longitude, double latitude) {
		int ProjNo = 0;
		double longitude1, latitude1, longitude0, X0, Y0, xval, yval;
		double e2, ee, NN, T, C, A, M;

		ProjNo = (int) (longitude / ZoneWide);
		longitude0 = ProjNo * ZoneWide + ZoneWide / 2;
		longitude0 = longitude0 * iPI;
		longitude1 = longitude * iPI; // 经度转换为弧度
		latitude1 = latitude * iPI; // 纬度转换为弧度
		e2 = 2 * f - f * f;
		ee = e2 * (1.0 - e2);
		NN = a / Math.sqrt(1.0 - e2 * Math.sin(latitude1) * Math.sin(latitude1));
		T = Math.tan(latitude1) * Math.tan(latitude1);
		C = ee * Math.cos(latitude1) * Math.cos(latitude1);
		A = (longitude1 - longitude0) * Math.cos(latitude1);
		M = a
				* ((1 - e2 / 4 - 3 * e2 * e2 / 64 - 5 * e2 * e2 * e2 / 256) * latitude1
						- (3 * e2 / 8 + 3 * e2 * e2 / 32 + 45 * e2 * e2 * e2 / 1024)
						* Math.sin(2 * latitude1) + (15 * e2 * e2 / 256 + 45 * e2 * e2 * e2 / 1024)
						* Math.sin(4 * latitude1) - (35 * e2 * e2 * e2 / 3072)
						* Math.sin(6 * latitude1));
		xval = NN
				* (A + (1 - T + C) * A * A * A / 6 + (5 - 18 * T + T * T + 72 * C - 58 * ee) * A
						* A * A * A * A / 120);
		yval = M
				+ NN
				* Math.tan(latitude1)
				* (A * A / 2 + (5 - T + 9 * C + 4 * C * C) * A * A * A * A / 24 + (61 - 58 * T + T
						* T + 600 * C - 330 * ee)
						* A * A * A * A * A * A / 720);
		X0 = 1000000L * (ProjNo + 1) + 500000L;
		Y0 = 0;
		xval = xval + X0;
		yval = yval + Y0;

		return new PlaneCoordinate(xval, yval);

	}

	/**
	 * ?*? @Title: GaussToBL ?*? @Description: 由高斯投影坐标反算成经纬度 ?*? @author
	 * jf.gong? DateTime 2014年5月30日 下午3:36:28 ?*? @return Point ?*? @param X ?*? @param
	 * Y ?*? @return ?
	 */
	private Point getLngLat(double X, double Y) {
		int ProjNo;
		double longitude1, latitude1, longitude0, X0, Y0, xval, yval;// latitude0,
		double e1, e2, ee, NN, T, C, M, D, R, u, fai;

		ProjNo = (int) (X / 1000000L); // 查找带号
		longitude0 = (ProjNo - 1) * ZoneWide + ZoneWide / 2;
		longitude0 = longitude0 * iPI; // 中央经线
		X0 = ProjNo * 1000000L + 500000L;
		Y0 = 0;
		xval = X - X0;
		yval = Y - Y0; // 带内大地坐标
		e2 = 2 * f - f * f;
		e1 = (1.0 - Math.sqrt(1 - e2)) / (1.0 + Math.sqrt(1 - e2));
		ee = e2 / (1 - e2);
		M = yval;
		u = M / (a * (1 - e2 / 4 - 3 * e2 * e2 / 64 - 5 * e2 * e2 * e2 / 256));
		fai = u + (3 * e1 / 2 - 27 * e1 * e1 * e1 / 32) * Math.sin(2 * u)
				+ (21 * e1 * e1 / 16 - 55 * e1 * e1 * e1 * e1 / 32) * Math.sin(4 * u)
				+ (151 * e1 * e1 * e1 / 96) * Math.sin(6 * u) + (1097 * e1 * e1 * e1 * e1 / 512)
				* Math.sin(8 * u);
		C = ee * Math.cos(fai) * Math.cos(fai);
		T = Math.tan(fai) * Math.tan(fai);
		NN = a / Math.sqrt(1.0 - e2 * Math.sin(fai) * Math.sin(fai));
		R = a
				* (1 - e2)
				/ Math.sqrt((1 - e2 * Math.sin(fai) * Math.sin(fai))
						* (1 - e2 * Math.sin(fai) * Math.sin(fai))
						* (1 - e2 * Math.sin(fai) * Math.sin(fai)));
		D = xval / NN;
		// 计算经度(Longitude) 纬度(Latitude)
		longitude1 = longitude0
				+ (D - (1 + 2 * T + C) * D * D * D / 6 + (5 - 2 * C + 28 * T - 3 * C * C + 8 * ee + 24
						* T * T)
						* D * D * D * D * D / 120) / Math.cos(fai);
		latitude1 = fai
				- (NN * Math.tan(fai) / R)
				* (D * D / 2 - (5 + 3 * T + 10 * C - 4 * C * C - 9 * ee) * D * D * D * D / 24 + (61
						+ 90 * T + 298 * C + 45 * T * T - 256 * ee - 3 * C * C)
						* D * D * D * D * D * D / 720);

		return new Point(getWantDouble(longitude1 / iPI, 6), getWantDouble(latitude1 / iPI, 6));
	}

	/**
	 * ?*? @Title: getTargetCoordinate ?*? @Description: lbs基站定位 ?*? @author
	 * jf.gong? DateTime 2014年5月30日 下午3:41:59 ?*? @return Point ?*? @param
	 * cellId ?*? @return ?
	 */
	private Point locate(List<CellData> cellList) {
		if (cellList == null || cellList.size() < 3) {
			return null;
		}

//		Map<Long, Double> disMap = new HashMap<Long, Double>();
//		// 获取数据库基站model数据,取前三个信号最强的基站
//		for (CellData baseModel : cellList) {
//			disMap.put(baseModel.getId(), getDistance(baseModel.getRssiVal()));
//		}
		PlaneCoordinate plance1 = getPlanCoordinate(cellList.get(0).getLng(),cellList.get(0).getLat());
		double r1 = getDistance(cellList.get(0).getRssi());
		PlaneCoordinate plance2 = getPlanCoordinate(cellList.get(1).getLng(),cellList.get(1).getLat());
		double r2 = getDistance(cellList.get(1).getRssi());
		PlaneCoordinate plance3 = getPlanCoordinate(cellList.get(2).getLng(),cellList.get(2).getLat());
		double r3 = getDistance(cellList.get(2).getRssi());
		logger.info("************r1=="+r1+"***********r2=="+r2+"**************r3=="+r3);
		// 根据信号强度得到与基站距离
//		double da = disMap.get(cellList.get(0).getId());
//		double db = disMap.get(cellList.get(1).getId());
//		double dc = disMap.get(cellList.get(2).getId());
		// //三角质心原理求坐标
//		 double y1 =
//		 (db*db-dc*dc-plance2.getX()*plance2.getX()+plance3.getX()*plance3.getX()-plance2.getY()*plance2.getY()+plance3.getY()*plance3.getY())*(plance1.getX()-plance2.getX());
//		 double y2
//		 =(da*da-db*db-plance1.getX()*plance1.getX()+plance2.getX()*plance2.getX()-plance1.getY()*plance1.getY()+plance2.getY()*plance2.getY())*(plance2.getX()-plance3.getX());
//		 double y3 =
//		 2*((plance2.getX()-plance3.getX())*(plance1.getY()-plance2.getY())-(plance1.getX()-plance2.getX())*(plance2.getY()-plance3.getY()));
//		 double y = (y1 - y2)/y3;
//		 double x1 =
//		 da*da-db*db-plance1.getX()*plance1.getX()+plance2.getX()*plance2.getX()-plance1.getY()*plance1.getY()+plance2.getY()*plance2.getY()+2*y*plance1.getY()-2*y*plance2.getY();
//		 double x2 = (-2)*(plance1.getX()-plance2.getX());
//		 double x = x1/x2;

		// ?// 外切圆圆心
		double x1 = plance1.getX();
		double x2 = plance2.getX();
		double x3 = plance3.getX();
		double y1 = plance1.getY();
		double y2 = plance2.getY();
		double y3 = plance3.getY();
		//logger.info("**************x1=="+x1+"y1=="+y1+"x2=="+x2+"y2=="+y2+"x3=="+x3+"y3=="+y3);
		Circle c1 = new Circle(x1,y1,r1);
		Circle c2 = new Circle(x2,y2,r2);
		Circle c3 = new Circle(x3,y3,r3);
		CirIntersectService ci1 = new CirIntersectService(c1,c2);
		String dd[] = ci1.intersect();
		//logger.info("*******************dd====="+dd[0]+","+dd[1]+","+dd[2]+","+dd[3]);
		PlaneCoordinate planceOne = new PlaneCoordinate();//最终要求的三点
		PlaneCoordinate planceTwo = new PlaneCoordinate();
		PlaneCoordinate planceThree = new PlaneCoordinate();
		planceOne1 = new PlaneCoordinate();
		planceOne2 = new PlaneCoordinate();
		planceOne1.setX(Double.parseDouble(dd[0]));
		planceOne1.setY(Double.parseDouble(dd[1]));
		double dis1 = distenceOfPoints(planceOne1,plance3);
		planceOne2.setX(Double.parseDouble(dd[2]));
		planceOne2.setY(Double.parseDouble(dd[3]));
		double dis2 = distenceOfPoints(planceOne2,plance3);
		if(dis1>dis2){
			planceOne = planceOne2;
		}else{
			planceOne = planceOne1;
		}
		CirIntersectService ci2 = new CirIntersectService(c2,c3);
		String dd2[] = ci2.intersect();
		//logger.info("*******************dd====="+dd2[0]+","+dd2[1]+","+dd2[2]+","+dd2[3]);
		planceTwo1 = new PlaneCoordinate();
		planceTwo1.setX(Double.parseDouble(dd2[0]));
		planceTwo1.setY(Double.parseDouble(dd2[1]));
		planceTwo2 = new PlaneCoordinate();
		planceTwo2.setX(Double.parseDouble(dd2[2]));
		planceTwo2.setY(Double.parseDouble(dd2[3]));
		double dis3 = distenceOfPoints(planceTwo1,plance1);
		double dis4 = distenceOfPoints(planceTwo2,plance1);
		if(dis3>dis4){
			planceTwo = planceTwo2;
		}else{
			planceTwo = planceTwo1;
		}
		CirIntersectService ci3 = new CirIntersectService(c1,c3);
		String dd3[] = ci3.intersect();
		logger.info("*******************dd====="+dd3[0]+","+dd3[1]+","+dd3[2]+","+dd3[3]);
		planceThree1 = new PlaneCoordinate();
		planceThree1.setX(Double.parseDouble(dd3[0]));
		planceThree1.setY(Double.parseDouble(dd3[1]));
		planceThree2 = new PlaneCoordinate();
		planceThree2.setX(Double.parseDouble(dd3[2]));
		planceThree2.setY(Double.parseDouble(dd3[3]));
		double dis5 = distenceOfPoints(planceThree1,plance2);
		double dis6 = distenceOfPoints(planceThree2,plance2);
		if(dis5>dis6){
			planceThree = planceThree2;
		}else{
			planceThree = planceThree1;
		}
		// ???? double x = ((y2 - y1) * (y3 * y3 - y1 * y1 + x3 * x3 -
		// x1 * x1) - (y3 - y1)
		// ???????????? * (y2 * y2 - y1 * y1 + x2 * x2 - x1 * x1))
		// ???????????? / (2 * (x3 - x1) * (y2 - y1) - 2 * ((x2 - x1) *
		// (y3 - y1)));
		//
		// ???? double y = ((x2 - x1) * (x3 * x3 - x1 * x1 + y3 * y3 -
		// y1 * y1) - (x3 - x1)
		// ???????????? * (x2 * x2 - x1 * x1 + y2 * y2 - y1 * y1))
		// ???????????? / (2 * (y3 - y1) * (x2 - x1) - 2 * ((y2 - y1) *
		// (x3 - x1)));
		
		// 内切圆圆心
		//double d23 = distenceOfPoints(plance2, plance3);
		//double d31 = distenceOfPoints(plance3, plance1);
		//double d12 = distenceOfPoints(plance1, plance2);
		double d23 = distenceOfPoints(planceTwo, planceThree);
		double d31 = distenceOfPoints(planceThree, planceOne);
		double d12 = distenceOfPoints(planceOne, planceTwo);
		//logger.info("************d23=="+d23+"**********d31=="+d31+"************d12=="+d12);
		double sumd = d23 + d31 + d12;
		double x = (planceOne.getX() * d23 +  planceTwo.getX()* d31 + planceThree.getX() * d12) / sumd;

		double y = (planceOne.getY() * d23 + planceTwo.getY() * d31 + planceThree.getY() * d12) / sumd;
		//double d1 = distenceOfPoints(new PlaneCoordinate(x, y), plance2);
		// 计算gps坐标
		Point point = getLngLat(x, y);

		return point;
	}

	// 两点之间的距离

	private double distenceOfPoints(PlaneCoordinate plance1, PlaneCoordinate plance2) {

		return Math.sqrt((plance1.getX() - plance2.getX()) * (plance1.getX() - plance2.getX())
				+ (plance1.getY() - plance2.getY()) * (plance1.getY() - plance2.getY()));

	}

	/**
	 * ?*? @Title: combination ?*? @Description: Java实现排列组合去重复 ?*? @author
	 * jf.gong? DateTime 2014年5月30日 下午6:52:20 ?*? @return void ?
	 */
//	private void combination() {
//		int data[] = { 1, 2, 3, 4, 5, 6, 7 };
//		f_1(data);
//	}

//	private void f_1(int data[]) {
//		for (int i = 0; i <= 2; i++) {
//			for (int j = i + 1; j <= 3; j++) {
//				// k<= length-1
//				for (int k = j + 1; k <= 6; k++) {
//					System.out.println(data[i] + " " + data[j] + " " + data[k]);
//				}
//			}
//		}
//	}

	
	public Point locate(String cellStr){
		String[] cellArray = cellStr.split(",");
		if(cellArray == null){
			return null;
		}
		
		Point point = null;
		if(cellArray.length < 3){
//			CellData cellData = parseCellInfo(cellArray[0]);
//			Cell cell = cellCache.getCellData(cellData.getMnc(), cellData.getLac(), cellData.getCell());
//			if(cell != null){
//				point = new Point(cell.getLng(),cell.getLat());
//			}
			return null;
		}else{
			List<CellData> cellList = new ArrayList<CellData>();
			int index =0;
			for(int i=0;i<cellArray.length && index<3;i++){
				CellData cellData = parseCellInfo(cellArray[i]);
				Cell cell = cellCache.getCellData(cellData.getMnc(), cellData.getLac(), cellData.getCell());
				if(cell == null){
					continue;
				}
				cellData.setLng(cell.getLng());
				cellData.setLat(cell.getLat());

				cellList.add(cellData);
				index++;
			}
			point = this.locate(cellList);
		}
		
		return point;
	}
	
	public CellData parseCellInfo(String cellInfo){
		CellData cellData = new CellData();
		String cellStr = null;
		String rssi = null;

		int index = cellInfo.indexOf("_");
		if(index != -1){
			cellStr = cellInfo.substring(0, index);
			String cellStrs = cellInfo.substring(index+1);
			int indexs = cellStrs.indexOf("_");
			if(indexs != -1){
				rssi = cellInfo.substring(index+1,indexs+index+1);
			}else{
				rssi = cellInfo.substring(index+1);
			}
		}else{
			cellStr = cellInfo;
			rssi = "150";
		}
		if(cellStr.length() == 14){
			//String mcc = cellStr.substring(0,3);
			String mnc = cellStr.substring(3,6);
			String lac = cellStr.substring(6,10);
			String ci = cellStr.substring(10);
			
			cellData.setMnc(Integer.parseInt(mnc, 16));
			cellData.setLac(Integer.parseInt(lac, 16));
			cellData.setCell(Integer.parseInt(ci, 16));
		}else{
			String lac = cellStr.substring(0,4);
			String ci = cellStr.substring(4);
			
			cellData.setLac(Integer.parseInt(lac, 16));
			cellData.setCell(Integer.parseInt(ci, 16));
		}
		cellData.setRssi(Double.valueOf(rssi)-110);
		return cellData;
	}
	
	public int contains(Map<Integer, ?> m,Map<Integer, ?> n){
		Set<Integer> set = m.keySet();    
		Set<Integer> set2 = n.keySet();
		int num = 0;
		for (Integer key :set) {
			Integer valm = (Integer) m.get(key);    
			for(Integer key2 :set2) {
				Integer valn = (Integer) n.get(key2);
				if(valm.equals(valn)){
					num++;
				}
			}
			
		}          
		return num;			
	}
	/**
	 * ?*? @Title: main ?*? @Description: TODO ?*? @author jf.gong? DateTime
	 * 2014年6月6日 下午2:29:11 ?*? @return void ?*? @param args ?
	 */
	public static void main(String[] args) {
		
//		System.out.println(Double.valueOf(Math.pow(10,-0.67)));
//		System.out.println(BaseStationPosition.getDistance(147));
//		System.out.println(BaseStationPosition.getDistance(143));
//		System.out.println(BaseStationPosition.getDistance(141));
//		
		String contextData = "context.xml";
		String[] contexts = new String[] {contextData};
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				contexts);
		
		LocationService location = (LocationService)context.getBean("cellLocation");
		Point point = location.locate("46000028932a73,28932a74");
		System.out.println(point);
	}

}