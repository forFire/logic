package com.capcare.harbor.util;

import java.lang.annotation.Annotation;

import javax.persistence.Table;

public class TableUtils {

	public static String getName(Class<?> clas){
		Annotation[] annotations = clas.getAnnotations();
		for (Annotation ann : annotations) {
			if(ann instanceof Table){
				String name = ((Table) ann).name();
				return name;
			}
		}
		return null;
	}
}
