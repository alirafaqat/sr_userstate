package com.test.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ResourceUtils {
	public Integer addDigitsInString(String text) {
		if(StringUtils.isNotEmpty(text)) {
			String[] arrNumbers = text.replaceAll("\\D+", " ").trim().split(" ");
			int sum = 0;
			for (String strNumber : arrNumbers) {
				sum += Integer.parseInt(strNumber);
			}
			
			return sum;
		}
		return null;
	}
	
	public String removeLastOccuranceOfChar(String text, char ch) {
		if(StringUtils.isNotEmpty(text)) {
			int lastIndex = text.lastIndexOf(ch);
			if(lastIndex > 0) {
				return new StringBuilder(text).replace(lastIndex, lastIndex+1,"").toString();
			}
		}
		
		return text;
	}

	public static void main(String argt[]) {
		//  12 34 56 78 90 
//		System.out.println("asdf.12_-==`!@34$#56^%78*&90)(".replaceAll("\\D+", " "));
//		System.out.println(removeLastOccuranceOfChar("asdfbbfdlknnkb fdjlkasdff", 'f'));		
		
	}

}
