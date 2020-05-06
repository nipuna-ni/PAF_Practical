package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Medicine {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/helthcare?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
			System.out.println("Database Connected.");
		} catch (Exception e) {
			System.out.println("DB Connecting Faild.");
			e.printStackTrace();
		}
		return con;
	}

	public String insertMedicines(String code, String name, String price, String desc) {
		{
			String output = "";

			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database for inserting.";
				}

				// create a prepared statement
				String query = " insert into medicine(`mediID`,`mediCode`,`mediName`,`mediPrice`,`mediDesc`)"
						+ " values (?, ?, ?, ?, ?)";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, code);
				preparedStmt.setString(3, name);
				preparedStmt.setDouble(4, Double.parseDouble(price));
				preparedStmt.setString(5, desc);

				// execute the statement
				preparedStmt.execute();
				con.close();

				String newMedicines = readMedicines();
				output = "{\"status\":\"success\", \"data\": \"" + newMedicines + "\"}";
			} catch (Exception e) {
				output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
				System.err.println(e.getMessage());
			}

			return output;
		}
	}

	public String readMedicines() {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\'1\'><tr><th>Medi code</th><th>Medi name</th><th>Medi price</th><th>Medi desc</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from medicine";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String mediID = Integer.toString(rs.getInt("mediID"));
				String mediCode = rs.getString("mediCode");
				String mediName = rs.getString("mediName");
				String mediPrice = Double.toString(rs.getDouble("mediPrice"));
				String mediDesc = rs.getString("mediDesc");

				// Add into the html table
				output += "<tr><td><input id=\'hidMediIDUpdate\' name=\'hidMediIDUpdate\' type=\'hidden\' value=\'" + mediID  
						+ "'>" + mediCode + "</td>";
				output += "<td>" + mediName + "</td>";
				output += "<td>" + mediPrice + "</td>";
				output += "<td>" + mediDesc + "</td>";

				// buttons
//				output += "<td><input name=\'btnUpdate\' type=\'button\' value=\'Update\' class=\' btnUpdate btn btn-secondary\'></td>"
//						//+ "<td><form method=\"post\" action=\"items.jsp\">      "
//						+ "<input name=\'btnRemove\' type=\'submit\' value=\'Remove\' class=\'btn btn-danger\'> "
//						+ "<input name=\"hidItemIDDelete\" type=\"hidden\" value=\"" + itemID + "\">" + "</form></td></tr>"; 
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-mediid='"
						+ mediID + "'>" + "</td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updateMedicines(String ID, String code, String name, String price, String desc) {
		{
			String output = "";

			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database for updating.";
				}

				// create a prepared statement
				String query = "UPDATE medicine SET mediCode=?,mediName=?,mediPrice=?,mediDesc=? WHERE mediID=?";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setString(1, code);
				preparedStmt.setString(2, name);
				preparedStmt.setDouble(3, Double.parseDouble(price));
				preparedStmt.setString(4, desc);
				preparedStmt.setInt(5, Integer.parseInt(ID));

				// execute the statement
				preparedStmt.execute();
				con.close();

				String newMedicines = readMedicines();
				output = "{\"status\":\"success\", \"data\": \"" + newMedicines + "\"}";
			} catch (Exception e) {
				output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
				System.err.println(e.getMessage());
			}

			return output;
		}
	}

	public String deleteMedicines(String mediID) {
		{
			String output = "";

			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database for deleting.";
				}

				// create a prepared statement
				String query = "delete from medicine where mediID=?";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setInt(1, Integer.parseInt(mediID));

				// execute the statement
				preparedStmt.execute();
				con.close();

				String newMedicines = readMedicines();
				output = "{\"status\":\"success\", \"data\": \"" + newMedicines + "\"}";
			} catch (Exception e) {
				output = "Error while deleting the item.";
				System.err.println(e.getMessage());
			}

			return output;
		}

	}
}
