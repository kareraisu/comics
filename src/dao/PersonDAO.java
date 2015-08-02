package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import data.Person;

public class PersonDAO extends DAO<Person> {
	
	private static String table = "people";
	
	@Override
	public boolean insert(Person person, boolean update) {
		try (Connection conn = Mydb.connect()) {
			String query = "INSERT INTO "+ table +"(id, name, email) VALUES(?, ?, ?)";
			Integer[] po = {1, 2, 3}; //query parameter order
			if (update) {
				query = "UPDATE "+ table +" SET name=?, email=? WHERE id=?";
				ArrayList<Integer> poList = new ArrayList<Integer>(Arrays.asList(po));
				Collections.rotate(poList, 1);
				poList.toArray(po);
			}
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(po[0], person.getId());
			ps.setString(po[1], person.getName());
			ps.setString(po[2], person.getMail());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DB Error: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	@Override
	public boolean delete(int id) {
		try (Connection conn = Mydb.connect()) {
			conn.createStatement().executeUpdate("DELETE FROM "+ table +" WHERE id="+ id);
		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			return false;
		}
		return true;
	}

}
