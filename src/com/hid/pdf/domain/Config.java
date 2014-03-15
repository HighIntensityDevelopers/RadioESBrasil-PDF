package com.hid.pdf.domain;

import java.io.Serializable;

public class Config implements Serializable {
	private static final long serialVersionUID = 3821634119128939553L;

	private Long id;
	private String key;
	private String value;
	private String valueBlob;

	public static final String _LINK_PDF = "linkPdf";

	public static final String _U = "u";
	public static final String _P = "p";

	public static final String _QUESTION_1 = "question1";
	public static final String _QUESTION_2 = "question2";
	public static final String _QUESTION_3 = "question3";
	public static final String _QUESTION_4 = "question4";
	public static final String _QUESTION_5 = "question5";
	public static final String _MSGDONWLOAD = "msgdownload";
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public Config setKey(String key) {
		this.key = key;
		return this;
	}

	public String getValue() {
		return value;
	}

	public Config setValue(String value) {
		this.value = value;
		return this;
	}

	public String getValueBlob() {
		return valueBlob;
	}

	public Config setValueBlob(String valueBlob) {
		this.valueBlob = valueBlob;
		return this;
	}
}
