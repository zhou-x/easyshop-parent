package com.easyshop.pojo;

import java.io.Serializable;

/*
 * 类-->Json
 */
public class JsonObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3932197596507492521L;

	private Integer id;
	private String text;

	public JsonObject(String text) {
		super();
		this.text = text;
	}

	public JsonObject() {
		super();
	}

	public JsonObject(Integer id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "JsonObject [id=" + id + ", text=" + text + "]";
	}

}
