package com.ops.api.data.model;



public class RestResponse  {

	private int statusCode;
	
	private String message;
	
	private Object object;

	private String loggedInUserMail;
	
	public RestResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public RestResponse(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}



	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}



	


	public String getLoggedInUserMail() {
		return loggedInUserMail;
	}



	public void setLoggedInUserMail(String loggedInUserMail) {
		this.loggedInUserMail = loggedInUserMail;
	}

	
	
	
}
