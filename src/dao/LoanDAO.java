package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import data.Loan;

public class LoanDAO extends DAO<Loan> {
	
	private static String table = "loans";
	
	@Override
	public boolean insert(Loan loan, boolean update) {
		try (Connection conn = Mydb.connect()) {
			String query = "INSERT INTO "+ table +"(id, person, comic, date) VALUES(?, ?, ?, ?)";
			Integer[] po = {1, 2, 3, 4}; //query parameter order
			if (update) {
				query = "UPDATE "+ table +" SET person=?, comic=?, date=? WHERE id=?";
				ArrayList<Integer> poList = new ArrayList<Integer>(Arrays.asList(po));
				Collections.rotate(poList, 1);
				poList.toArray(po);
			}
			// insert loan
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(po[0], loan.getId());
			ps.setInt(po[1], loan.getPerson());
			ps.setInt(po[2], loan.getComic());
			ps.setString(po[3], loan.getDate());
			ps.executeUpdate();
			// then update comic quantity
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE comics SET quantity=quantity-1 WHERE id="+ loan.getComic());
		} catch (SQLException e) {
			System.out.println("DB Error: " + e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(int id) {
		try (Connection conn = Mydb.connect()) {
			Statement stmt = conn.createStatement();
			// get comic id from loan
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ table +" WHERE id="+ id);
			rs.next();
			int comicId = rs.getInt(3);
			// delete loan
			stmt.executeUpdate("DELETE FROM "+ table +" WHERE id="+ id);
			// then update comic quantity
			stmt.executeUpdate("UPDATE comics SET quantity=quantity+1 WHERE id="+ comicId);
		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			return false;
		}
		return true;
	};
	
}
