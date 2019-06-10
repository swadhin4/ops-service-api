package com.ops.api.exception;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@XmlRootElement
@JsonPropertyOrder({"status", "errorCode", "errorMessage"})
public class OpsDataException implements Serializable {
	private String status ;
	private String errorCode;
	private String errorMessage;
	private String type ;
	
	
	public OpsDataException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public OpsDataException(String status, String errorMessage) {
		super();
		this.status = status;
		this.errorMessage = errorMessage;
	}



	public OpsDataException(String status, String errorCode, String errorMessage) {
		super();
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}



	public OpsDataException(String status, String errorCode, String errorMessage, String type) {
		super();
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.type = type;
	}


	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	
}
