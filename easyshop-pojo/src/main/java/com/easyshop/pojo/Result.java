package com.easyshop.pojo;

import java.io.Serializable;

/*
 * 异步状态对象
 */
public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7274024387262243393L;
	
	
	private boolean success;
	private String message;

	public Result(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
