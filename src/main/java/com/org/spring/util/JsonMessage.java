package com.org.spring.util;


public class JsonMessage {
	boolean success;
	boolean error;
	String message;
	String param;
	
	public JsonMessage() {
		
	}
	
	public JsonMessage(boolean success, String message) {
		this.success = success;
		this.error = !success;
		this.message = message;
	}
	public JsonMessage(boolean success, String message, String param) {
		this.success = success;
		this.error = !success;
		this.message = message;
		this.param = param;
	}
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
		this.error = !success;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
		this.success = !error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "JsonMessage [success=" + success + ", error=" + error + ", message=" + message + ", param=" + param
				+ "]";
	}
	
	
}
