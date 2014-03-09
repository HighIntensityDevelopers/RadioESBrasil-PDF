package com.hid.pdf.persistence.dao;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EntityClass {
	Class<?> value();
}