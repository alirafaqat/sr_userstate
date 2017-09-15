package com.test.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class CustomErrorController implements ErrorController {
	private static final String PATH = "/error";
	
	@RequestMapping(value = PATH)
	@ResponseBody
    public ResponseEntity<String> error() {
		return new ResponseEntity("NOT_FOUND", HttpStatus.NOT_FOUND);
    }
	
	@Override
    public String getErrorPath() {
        return PATH;
    }
}
