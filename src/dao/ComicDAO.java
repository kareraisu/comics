package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import data.Comic;

public class ComicDAO extends DAO<Comic> {
	
	private static String table = "comics";
	
	@Override
	public boolean insert(Comic comic, boolean update) {
		try (Connection conn = Mydb.connect()) {
			String query = "INSERT INTO "+ table +"(id, title, genre, quantity) VALUES(?, ?, ?, ?)";
			Integer[] po = {1, 2, 3, 4}; //query parameter order
			if (update) {
				query = "UPDATE "+ table +" SET title=?, genre=?, quantity=? WHERE id=?";
				ArrayList<Integer> poList = new ArrayList<Integer>(Arrays.asList(po));
				Collections.rotate(poList, 1);
				poList.toArray(po);
			}
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(po[0], comic.getId());
			ps.setString(po[1], comic.getTitle());
			ps.setString(po[2], comic.getGenre());
			ps.setInt(po[3], comic.getQty());
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
