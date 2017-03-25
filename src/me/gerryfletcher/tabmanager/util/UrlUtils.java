package me.gerryfletcher.tabmanager.util;

public class UrlUtils {
	public static String formatUrl(String str){
		
		if(!str.contains("http")){
			str = "http://" + str;
		}
		
		return str;
	}
}
