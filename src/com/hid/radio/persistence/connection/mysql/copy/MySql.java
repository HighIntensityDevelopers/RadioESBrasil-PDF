package com.hid.radio.persistence.connection.mysql.copy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

import com.hid.pdf.persistence.dao.ResultDao;

public class MySql {
	private Connection conn;
	private static MySql obj;
	private static ResourceBundle bundle;
	private static Date lastTime = new Date();

	static {
		bundle = ResourceBundle.getBundle("cfg");
	}

	public static MySql getInstance() {
		try {
			if (obj == null || obj.getConn() == null || obj.getConn().isClosed()) {
				obj = new MySql();
				obj.connect();
			}

			Date now = new Date();
			if (now.getTime() - lastTime.getTime() > 60000) {
				if (obj != null && !obj.getConn().isClosed()) {
					obj.getConn().close();
					obj.connect();
				}
			}

			lastTime = now;

			return obj;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public MySql() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void connect() {
		try {
			conn = DriverManager.getConnection(bundle.getString("tns"));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public ResultDao executeQuery(String query) {
		Statement stmt = null;
		ResultSet rs = null;

		System.out.println(query);

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return new ResultDao(stmt, rs);
	}

	public long executeUpdate(String query) {
		Statement stmt = null;
		ResultSet rs = null;

		System.out.println(query);

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

			long lastId = -1;

			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				lastId = rs.getInt(1);
			}

			return lastId;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}
				stmt = null;
			}
		}
	}

	public Connection getConn() {
		return conn;
	}
}
