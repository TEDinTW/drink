package com.example.drinkorder.utils;

public class StringUtil {
	public static boolean isEmpty(String s){
		return (s==null || s.trim().length()==0);
	}
	
	public static int getStringArrayPosition(String[] strArray, String targetStr){
		if(targetStr==null || strArray==null)
			return -1;
		for(int i=0;i<strArray.length;i++){
			if(targetStr.equals(strArray[i]))
				return i;
		}

		return -1;
	}
}
