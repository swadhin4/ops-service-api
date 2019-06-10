package com.ops.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ops.api.data.model.RestResponse;

@Controller
@RequestMapping(value="/test")
public class HomeController {
	@RequestMapping(value="/api", method = RequestMethod.GET)
	 public  ResponseEntity<RestResponse> home() {
		RestResponse responseObj = new RestResponse();
		responseObj.setStatusCode(500);
		return new ResponseEntity<RestResponse>(responseObj, HttpStatus.OK);
	 }
}