package com.hid.pdf.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static boolean isEmpty(String valor) {
		return valor == null || valor.trim().equals("") || valor.trim().equals("null");
	}

	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static Object escapeNull(Object value, Class<?> clazz) {
		if (clazz.equals(String.class)) {
			return escapeNull((String) value);
		} else if (clazz.equals(Integer.class)) {
			return escapeNull((Integer) value);
		} else if (clazz.equals(Long.class)) {
			return escapeNull((Long) value);
		} else if (clazz.equals(Float.class)) {
			return escapeNull((Float) value);
		} else if (clazz.equals(Double.class)) {
			return escapeNull((Double) value);
		} else if (clazz.equals(Boolean.class)) {
			return escapeNull((Boolean) value);
		} else if (clazz.equals(Date.class)) {
			return escapeNull((Date) value);
		} else {
			return null;
		}
	}

	public static Double escapeNull(Double value) {
		if (isNull(value)) {
			return 0D;
		} else {
			return value;
		}
	}

	public static Float escapeNull(Float value) {
		if (isNull(value)) {
			return 0F;
		} else {
			return value;
		}
	}

	public static String escapeNull(String value) {
		if (isNull(value)) {
			return "";
		} else {
			return value;
		}
	}

	public static Long escapeNull(Long value) {
		if (isNull(value)) {
			return 0L;
		} else {
			return value;
		}
	}

	public static Integer escapeNull(Integer value) {
		if (isNull(value)) {
			return 0;
		} else {
			return value;
		}
	}

	public static String escapeNull(Date value) {
		if (isNull(value)) {
			return "NULL";
		} else {
			return sdf.format(value);
		}
	}

	public static String escapeNull(Boolean value) {
		if (isNull(value)) {
			return Boolean.FALSE.toString();
		} else {
			return value ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
		}
	}

	public static String replaceVariables(String htmlMail, String nome, String musica, String email, String comentarios) {
		String firstName = "#nome#";
		String music = "#musica#";
		String emailAddress = "#email#";
		String comments = "#comentarios#";

		return htmlMail.replaceAll(firstName, nome).replaceAll(music, musica).replaceAll(emailAddress, email)
				.replaceAll(comments, comentarios);
	}

}
