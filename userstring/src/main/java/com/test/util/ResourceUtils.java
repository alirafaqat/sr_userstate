package com.test.util;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ResourceUtils {
	public Integer addDigitsInString(String text) {
		if(StringUtils.isNotEmpty(text)) {
			String[] arrNumbers = text.replaceAll("\\D+", " ").trim().split(" ");
			int sum = 0;
			for (String strNumber : arrNumbers) {
				if(strNumber.length() > 0) {
					sum += Integer.parseInt(strNumber);
				}
			}
			
			return sum;
		}
		return 0;
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
	
	public boolean isValidJSon(String jSonText) {
		final ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.readTree(jSonText);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
