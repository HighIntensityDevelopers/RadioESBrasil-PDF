package com.hid.pdf.persistence.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hid.pdf.persistence.connection.mysql.MySql;
import com.hid.pdf.util.Utils;

public abstract class DAO<T> {
	private final String FIND_BY_ID = "select * from %s where id = %s";
	private final String FIND_ALL = "select * from " + databaseTable();
	private final String FIND_ALL_FILTER = "select * from " + databaseTable() + " where %s";
	private final String INSERT_QUERY = "insert into " + databaseTable() + " (%s) values (%s)";
	private final String UPDATE_QUERY = "update " + databaseTable() + " set %s where id = %s";
	private final String DELETE_QUERY = "delete from " + databaseTable() + " where id = %s";

	public T find(long id) {
		return find0(queryById(id));
	}

	public T find(String query) {
		return find0(query);
	}

	public List<T> findAll() {
		return findAll0(FIND_ALL);
	}

	public List<T> findAll(String filter) {
		return findAll0(String.format(FIND_ALL_FILTER, filter));
	}

	public boolean exists(long id) {
		return find0(queryById(id)) == null ? false : true;
	}

	public boolean exists(String query) {
		return find0(query) == null ? false : true;
	}

	public void create(T obj) {
		try {
			List<String> fieldsByObject = fields(obj);

			StringBuilder firstParam = new StringBuilder();
			StringBuilder secondParam = new StringBuilder();

			for (String field : fieldsByObject) {
				if (field.startsWith("_") || field.equals("id") || field.equals("serialVersionUID")) {
					continue;
				}

				Method method = obj.getClass().getMethod(methodGet(field));
				Object field_value = method.invoke(obj);

				firstParam.append(", ").append("`").append(field).append("`");
				secondParam.append(", ");

				if (method.getReturnType().equals(String.class)) {
					secondParam.append("'").append(Utils.escapeNull(field_value, method.getReturnType())).append("'");
				} else if (method.getReturnType().equals(Integer.class)) {
					secondParam.append(Utils.escapeNull(field_value, method.getReturnType()));
				} else if (method.getReturnType().equals(Float.class)) {
					secondParam.append(Utils.escapeNull(field_value, method.getReturnType()));
				} else if (method.getReturnType().equals(Double.class)) {
					secondParam.append(Utils.escapeNull(field_value, method.getReturnType()));
				} else if (method.getReturnType().equals(Boolean.class)) {
					secondParam.append(Utils.escapeNull(field_value, method.getReturnType()));
				} else if (method.getReturnType().equals(Date.class)) {
					if (field_value == null) {
						secondParam.append(Utils.escapeNull(field_value, method.getReturnType()));
					} else {
						secondParam.append("'").append(Utils.escapeNull(field_value, method.getReturnType())).append("'");
					}
				}
			}

			long id = MySql.getInstance().executeUpdate(
					String.format(INSERT_QUERY, firstParam.substring(2), secondParam.substring(2)));

			obj.getClass().getMethod(methodSet("id"), Long.class).invoke(obj, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void create(List<T> list) {
		for (T obj : list) {
			create(obj);
		}
	}

	public void update(T obj) {
		try {
			List<String> fieldsByObject = fields(obj);

			StringBuilder set = new StringBuilder();
			long id = -1;

			for (String field : fieldsByObject) {
				if (field.startsWith("_") || field.equals("serialVersionUID")) {
					continue;
				}

				Method method = obj.getClass().getMethod(methodGet(field));
				Object field_value = method.invoke(obj);

				if (field.equals("id")) {
					id = (Long) field_value;
				}

				set.append(", ").append("`").append(field).append("` = ");

				if (method.getReturnType().equals(String.class)) {
					set.append("'").append(Utils.escapeNull(field_value, method.getReturnType())).append("'");
				} else if (method.getReturnType().equals(Integer.class)) {
					set.append(Utils.escapeNull(field_value, method.getReturnType()));
				} else if (method.getReturnType().equals(Long.class)) {
					set.append(Utils.escapeNull(field_value, method.getReturnType()));
				} else if (method.getReturnType().equals(Float.class)) {
					set.append(Utils.escapeNull(field_value, method.getReturnType()));
				} else if (method.getReturnType().equals(Double.class)) {
					set.append(Utils.escapeNull(field_value, method.getReturnType()));
				} else if (method.getReturnType().equals(Boolean.class)) {
					set.append(Utils.escapeNull(field_value, method.getReturnType()));
				} else if (method.getReturnType().equals(Date.class)) {
					if (field_value == null) {
						set.append(Utils.escapeNull(field_value, method.getReturnType()));
					} else {
						set.append("'").append(Utils.escapeNull(field_value, method.getReturnType())).append("'");
					}
				}
			}

			MySql.getInstance().executeUpdate(String.format(UPDATE_QUERY, set.substring(2), id));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void update(List<T> list) {
		for (T obj : list) {
			update(obj);
		}
	}

	public void destroy(T obj) {
		try {
			MySql.getInstance().executeUpdate(
					String.format(DELETE_QUERY, (Long) obj.getClass().getMethod(methodGet("id")).invoke(obj)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void destroy(List<T> list) {
		for (T obj : list) {
			destroy(obj);
		}
	}

	private void close(ResultDao result) {
		if (result != null) {
			if (result.getResultSet() != null) {
				try {
					result.getResultSet().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (result.getStmt() != null) {
				try {
					result.getStmt().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private T mappedObject(ResultSet rs) {
		try {
			T obj = entityClass().newInstance();

			for (String field : fields(obj)) {
				if (field.startsWith("_")) {
					continue;
				}

				try {
					Method get = obj.getClass().getMethod(methodGet(field));
					Method set = obj.getClass().getMethod(methodSet(field), get.getReturnType());

					if (get.getReturnType().equals(String.class)) {
						set.invoke(obj, rs.getString(field));
					} else if (get.getReturnType().equals(Integer.class)) {
						set.invoke(obj, rs.getInt(field));
					} else if (get.getReturnType().equals(Long.class)) {
						set.invoke(obj, rs.getLong(field));
					} else if (get.getReturnType().equals(Float.class)) {
						set.invoke(obj, rs.getFloat(field));
					} else if (get.getReturnType().equals(Double.class)) {
						set.invoke(obj, rs.getDouble(field));
					} else if (get.getReturnType().equals(Boolean.class)) {
						set.invoke(obj, rs.getBoolean(field));
					} else if (get.getReturnType().equals(Date.class)) {
						set.invoke(obj, rs.getDate(field));
					}
				} catch (Exception e) {
				}
			}

			return obj;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private String databaseTable() {
		return getClass().getAnnotation(DatabaseTable.class).value();
	}

	@SuppressWarnings("unchecked")
	private Class<T> entityClass() {
		return (Class<T>) getClass().getAnnotation(EntityClass.class).value();
	}

	private T find0(String query) {
		ResultDao result = null;

		try {
			result = MySql.getInstance().executeQuery(query);
			ResultSet rs = result.getResultSet();

			T obj = null;

			if (rs.next()) {
				obj = mappedObject(rs);
			}

			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(result);
		}
	}

	private List<T> findAll0(String query) {
		ResultDao result = null;

		try {
			result = MySql.getInstance().executeQuery(query);
			ResultSet rs = result.getResultSet();

			List<T> listaDeObjetos = new ArrayList<T>();

			while (rs.next()) {
				listaDeObjetos.add(mappedObject(rs));
			}

			return listaDeObjetos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(result);
		}
	}

	private List<String> fields(Object obj) {
		List<String> fields = new ArrayList<String>();

		for (Field field : obj.getClass().getDeclaredFields()) {
			fields.add(field.getName());
		}

		return fields;
	}

	private String methodGet(String value) {
		return new StringBuilder("get").append((char) ((int) value.charAt(0) - 32)).append(value.substring(1)).toString();
	}

	private String methodSet(String value) {
		return new StringBuilder("set").append((char) ((int) value.charAt(0) - 32)).append(value.substring(1)).toString();
	}

	private String queryById(long id) {
		return String.format(FIND_BY_ID, databaseTable(), id);
	}
}
