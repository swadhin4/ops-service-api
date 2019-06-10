package com.ops.api.rest.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ops.api.data.model.AccessTokenVO;
import com.ops.api.data.model.RestResponse;
import com.ops.api.data.model.User;
import com.ops.api.service.AppAuthService;

@Controller
public class AppAuthenticationController {

	public static final String OAUTH_TOKEN_URL = "http://localhost:8080/ops/api/oauth/token?grant_type=password";
	
	@Autowired
	private AppAuthService authService;

	@RequestMapping(value="/v1/authenticate/user/client", method = RequestMethod.GET)
	public ResponseEntity<RestResponse> getUserClientCredentials(
			@RequestParam(name = "userdetails") String encodedUserDetails,
			@RequestParam(name = "loginType") String loginType, @RequestParam(name = "loginDevice") String deviceId) {
		RestResponse responseObj = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			User userClientDetails = authService.decodeUserDetails(encodedUserDetails,loginType,deviceId);
			if(userClientDetails.getClientId()!=null){
				getAuthenticationToken(userClientDetails);
			}
		} catch (Exception eek) {
			eek.printStackTrace();
			responseObj.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	private RestResponse getAuthenticationToken(User userClientDetails) {
		RestResponse responseObj = new RestResponse();
		try {
			HttpHeaders headers = createHttpHeaders(userClientDetails.getClientId(), userClientDetails.getClientSecret());
			// responseObj.setObject(headers.getFirst("Authorization"));

			HttpClient client = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost(OAUTH_TOKEN_URL + "&username=" + userClientDetails.getUsername() + "&password=" + userClientDetails.getPassword());
		
			request.addHeader("Authorization", headers.getFirst("Authorization"));
			request.addHeader("Accept", "application/json");
			request.addHeader("Content-Type", "application/json");
			HttpResponse responseData = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(responseData.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			ObjectMapper mapper = new ObjectMapper();
			AccessTokenVO accessTokenVO = mapper.readValue(result.toString(), AccessTokenVO.class);
			if (accessTokenVO.getAccessToken() != null) {
				responseObj.setLoggedInUserMail(userClientDetails.getUsername());
				responseObj.setStatusCode(200);
				responseObj.setObject(accessTokenVO);
			} else {
				responseObj.setStatusCode(401);
			}

		} catch (Exception eek) {
			eek.printStackTrace();
			responseObj.setStatusCode(500);
		}
		return responseObj;
	}

	private HttpHeaders createHttpHeaders(String clientId, String secret) {
		String notEncoded = clientId + ":" + secret;
		String encodedAuth = Base64.encodeBase64String(notEncoded.getBytes());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Basic " + encodedAuth);
		headers.add("Accept", "application/json");
		return headers;
	}
}
