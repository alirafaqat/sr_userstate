package com.test.validator;

import static org.apache.commons.lang3.StringUtils.isAlphanumeric;


public class TextValidator {
	public static boolean validateSingleAlphaNumeric(String text) {
		return isAlphanumeric(text) && text.length() == 1;
	}
}
