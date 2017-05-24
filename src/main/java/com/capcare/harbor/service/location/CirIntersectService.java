package com.capcare.harbor.service.location;

import java.text.DecimalFormat;

import org.springframework.stereotype.Component;


//将两个圆融合在一个对象中进行相交
public class CirIntersectService {
	private Circle c1=null;
	private Circle c2=null;
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private double r1;
	private double r2;
	public CirIntersectService(Circle C1,Circle C2){
		c1= C1;
		c2= C2;
		x1=c1.getX();
		y1=c1.getY();
		x2=c2.getX();
		y2=c2.getY();
		r1=c1.getR();
		r2=c2.getR();
		//初始化时将交点坐标设置为两圆圆心，
	}
	public String[] intersect(){
		double a,b,d,y_1,y_2,x_1,x_2;
		double delta = -1;
		DecimalFormat df = new DecimalFormat("#.##########");  
		y_1=y1;
		y_2=y2;
		x_1=x1;
		x_2=x2;
		//解一元二次方程
		if(x1!=x2){
		//式①减式②带入式①，得到二次方程
			a=Math.pow((2*y1 - 2*y2)/(2*x1 - 2*x2),2) + 1;//((y2-y1)*(y2-y1))/((x2-x1)*(x2-x1))+1;//for y
			b=((4*y1 - 4*y2)*(x1 + (r1*r1 - r2*r2 - x1*x1 + x2*x2 - y1*y1 + y2*y2)/(2*x1 - 2*x2)))/(2*x1 - 2*x2) - 2*y1;//(y2-y1)*(-r2*r2+r1*r1-y1*y1+y2*y2+x2*x2-x1*x2)/((x2-x1)*(x2-x1))-2*y1;
			d=Math.pow((x1 + (r1*r1 - r2*r2 - x1*x1 + x2*x2 - y1*y1 + y2*y2)/(2*x1 - 2*x2)),2) - r1*r1 + y1*y1;//(Math.pow((-r2*r2+r1*r1-y1*y1+y2*y2+x2*x2-x1*x2)/(2*(x2-x1)),2)+y1*y1-r1*r1);
			//下面使用判定式 判断是否有解
			delta=b*b-4*a*d;
			if(delta >0){
				y1=(-b+Math.sqrt(b*b-4*a*d))/(2*a);
				y2=(-b-Math.sqrt(b*b-4*a*d))/(2*a);
				x1=(r2*r2-r1*r1+y_1*y_1-y_2*y_2+x_1*x_1-x_2*x_2+2*(y_2-y_1)*y1)/(2*(x_1-x_2));
				x2=(r2*r2-r1*r1+y_1*y_1-y_2*y_2+x_1*x_1-x_2*x_2+2*(y_2-y_1)*y2)/(2*(x_1-x_2));
			}
			else if(delta ==0){//x needs another delta too
				y1=y2=-b/(2*a);
				x1=x2=(r2*r2-r1*r1+y_1*y_1-y_2*y_2+x_1*x_1-x_2*x_2+2*(y_2-y_1)*y2)/(2*(x_1-x_2));
			}else{
				;
			}
			System.out.println("x_1:"+x_1+" y_1:"+y_1+'\n'+" x_2:"+x_2+" y_2:"+y_2+"\nr1:"+r1+" r2:"+r2+" \nx1:"
				+x1+" y1:"+y1+'\n'+" x2:"+x2+" y2:"+y2+"\n delta"+delta+"\n a"+a+"\n b"+b+"\n d"+d);
		}
		else{
			a=Math.pow((2*x1 - 2*x2)/(2*y1 - 2*y2),2) + 1;//((x2-x1)*(x2-x1))/((y2-y1)*(y2-y1))+1;//for x
			b=((4*x1 - 4*x2)*(y1 + (r1*r1 - r2*r2 - y1*y1 + y2*y2 - x1*x1 + x2*x2)/(2*y1 - 2*y2)))/(2*y1 - 2*y2) - 2*x1;//(x2-x1)*(-r2*r2+r1*r1-x1*x1+x2*x2+y2*y2-y1*y2)/((y2-y1)*(y2-y1))-2*x1;
			d=Math.pow((y1 + (r1*r1 - r2*r2 - y1*y1 + y2*y2 - x1*x1 + x2*x2)/(2*y1 - 2*y2)),2) - r1*r1 + x1*x1;//(Math.pow((-r2*r2+r1*r1-x1*x1+x2*x2+y2*y2-y1*y2)/(2*(y2-y1)),2)+x1*x1-r1*r1)
			delta=b*b-4*a*d;
			if(delta >0){
				x1=(-b+Math.sqrt(b*b-4*a*d))/(2*a);
				x2=(-b-Math.sqrt(b*b-4*a*d))/(2*a);
				y1=(r2*r2-r1*r1+x_1*x_1-x_2*x_2+y_1*y_1-y_2*y_2+2*(x_2-x_1)*x1)/(2*(y_1-y_2));
				y2=(r2*r2-r1*r1+x_1*x_1-x_2*x_2+y_1*y_1-y_2*y_2+2*(x_2-x_1)*x2)/(2*(y_1-y_2));
			}
			else if(delta ==0){//y needs another delta too
				x1=x2=-b/(2*a);
				y1=y2=(r2*r2-r1*r1+x_1*x_1-x_2*x_2+y_1*y_1-y_2*y_2+2*(x_2-x_1)*x2)/(2*(y_1-y_2));
			}else{
				;
			}
			System.out.println("x_1:"+x_1+" y_1:"+y_1+'\n'+" x_2:"+x_2+" y_2:"+y_2+"\nr1:"+r1+" r2:"+r2+" \nx1:"
					+x1+" y1:"+y1+'\n'+" x2:"+x2+" y2:"+y2+"\n delta"+delta+"\n a"+a+"\n b"+b+"\n d"+d);
		}
		String dd[] = new String[4];
		dd[0] = df.format(x1);
 		dd[1] = df.format(y1);
 		dd[2] = df.format(x2);
 		dd[3] = df.format(y2);
		return dd;
	}
}
