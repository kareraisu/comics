package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.*;
import system.UI;

public abstract class DAO<T extends Data> {
	
	public abstract boolean insert(T t, boolean update);
	
	public abstract boolean delete(int id);
	
	public static ArrayList<Data> selectAll(String table) {
		ArrayList<Data> res = new ArrayList<>();
		try (Connection conn = Mydb.connect()) {
			Data el = null;
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM "+ table);
			while(rs.next()) {
				int id = rs.getInt(1);
				ArrayList<Object> values;
				switch (table) {
				case "comics":
					values = getValues(rs, Comic.getFields());
					el = new Comic(id, values);
					break;
				case "people":
					values = getValues(rs, Person.getFields());
					el = new Person(id, values);
					break;
				case "loans":
					values = getValues(rs, Loan.getFields());
					el = new Loan(id, values);
					break;
				default:
					break;
				}
				res.add(el);
			}
		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
		} catch(Exception e) {
			System.out.println("App Error: " + e.getMessage());
		}
		return res;
	}
	
	static ArrayList<Object> getValues(ResultSet rs, String[] fields) throws Exception {
		ArrayList<Object> values = new ArrayList<>();
		for (int i = 0; i < fields.length; i++) {
			String f = fields[i];
			Object v = new Object();
			//apply +1 offset due to rs being 1-indexed
			//and another +1 to skip id column
			int j = i+2;
			if (f.startsWith("s")) {
				v = rs.getString(j);
			} else if (f.startsWith("i")) {
				v = rs.getInt(j);
			} else {
				String msg = UI.locale.get("fieldTypeErr1") +
						f + UI.locale.get("fieldTypeErr2");
				throw(new Exception(msg));
			}
			values.add(v);
		}
		return values;
	}
	
}
