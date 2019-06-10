package com.ops.api.exception;

import java.io.Serializable;


public class OpsDatabaseException extends RuntimeException implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3624750827687795232L;
	
	private OpsDataException opsDataException;

	public OpsDatabaseException() {
		super();
	}

	public OpsDatabaseException(String message, Throwable cause) {
		super(message, cause);
		this.opsDataException = new OpsDataException(message, cause.getMessage());
	}

	public OpsDatabaseException(String errorCode, String message) {
		super(message);
		this.opsDataException = new OpsDataException(errorCode , message);
	}

	public OpsDatabaseException(String message) {
		super(message);
		
	}

	public OpsDatabaseException(Throwable cause) {
		super(cause);
	}

	public OpsDataException getOpsDataException() {
		return opsDataException;
	}

	public void setOpsDataException(OpsDataException opsDataException) {
		this.opsDataException = opsDataException;
	}



}
