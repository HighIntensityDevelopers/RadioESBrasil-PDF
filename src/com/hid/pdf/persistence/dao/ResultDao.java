package com.hid.pdf.persistence.dao;

import java.sql.ResultSet;
import java.sql.Statement;

public class ResultDao {
	private Statement stmt;
	private ResultSet resultSet;

	public ResultDao(Statement stmt, ResultSet resultSet) {
		super();
		this.stmt = stmt;
		this.resultSet = resultSet;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
}
