package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; 

public class Register {
	

	// A common method to connect to the DB
	public Connection connect() {

		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/consumer", "root", "Nethma");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	// Insert Details...............................

	public String insertDetail(String name, String address, String phone, String email, String username,
			String password) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = "insert into consumer_details(`account_no`,`name`,`address`,`phone`,`email`,`username`,`password`)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, address);
			preparedStmt.setString(4, phone);
			preparedStmt.setString(5, email);
			preparedStmt.setString(6, username);
			preparedStmt.setString(7, password);

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newRegisters = readDetails();
			output = "{\"status\":\"success\",\"data\":\""+newRegisters+"\"}"; 
			

		} 
		catch (Exception e) 
		{
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the register.\"}"; 
			System.err.println(e.getMessage());
		}

		return output;

	}
	
	//For Login input
	public String insertLogin (String email) {
		String output="";
		String account_no="", uemail="", username="", password="";
		
		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database";
			}
			
			Statement stmt = con.createStatement();
			String query = "select * from consumer_details where email='" + email + "'";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				account_no = Integer.toString(rs.getInt("account_no"));
				uemail = rs.getString("email");
				username = rs.getString("username");
				password = rs.getString("password");
			}
			
			// create a prepared statement
			String query2 = "insert into login(`account_no`,`username`,`password`,`email`)"
					+ " values (?, ?, ?, ?)";
			PreparedStatement preparedStmt2 = con.prepareStatement(query2);

			// binding values
			preparedStmt2.setString(1, account_no);
			preparedStmt2.setString(2, username);
			preparedStmt2.setString(3, password);
			preparedStmt2.setString(4, uemail);

			// execute the statement
			preparedStmt2.execute();
			con.close();
			output = "Inserted Successfully!";
			
		} catch (Exception e) {
			output = "Error while inserting!";
			System.err.println(e.getMessage());
		}
		
		return output;
		
	}
	
	
	
	//Validate		
			public String validate(String username, String password) {
				
				String output = "";
				String email="";
				
				try {
					Connection con = connect();

					if (con == null) {
						return "Error while connecting to the database";
					}

					Statement stmt = con.createStatement();
					String query = "select * from login where username='" + username + "' and password='"+password+"'";
					ResultSet rs = stmt.executeQuery(query);

					while (rs.next()) {
						username = rs.getString("username");
						password = rs.getString("password");
						email = rs.getString("email");
					}

					
					con.close();
					
					String getUser = getAccount(email);
					
					

					output = getUser;
					
				} catch (Exception e) {
					output = "Error while inserting!";
					System.err.println(e.getMessage());
				}

				return output;
				
			}
			
	

	// Read Details...............................

	public String readDetails() {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for reading";
			}

			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Account Number</th>" + "<th>Name</th>"
					+ "<th>Address</th><th>Phone</th>" + "<th>Email</th><th>UserName</th>" + "<th>Password</th>"
					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from consumer_details";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String account_no = Integer.toString(rs.getInt("account_no"));
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String username = rs.getString("username");
				String password = rs.getString("password");

				// Add new row to the html table
				output += "<tr><td>" + account_no + "</td>";
				output += "<td>" + name + "</td>";
				output += "<td>" + address + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + username + "</td>";
				output += "<td>" + password + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btn btn-secondary'data-account_no='" + account_no + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-account_no='" + account_no + "'></td></tr>"; 
			}

			con.close();

			// Complete the html table
			output += "</table>";

		} catch (Exception e) {
			output = "Error while reading!";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// Update Details...............................

	public String updateDetail(String account_no, String name, String address, String phone, String uemail,
			String username, String password) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE consumer_details SET name=?,address=?,phone=?,email=?,username=?,password=? WHERE account_no=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, address);
			preparedStmt.setString(3, phone);
			preparedStmt.setString(4, uemail);
			preparedStmt.setString(5, username);
			preparedStmt.setString(6, password);
			preparedStmt.setInt(7, Integer.parseInt(account_no));

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newRegisters = readDetails();
			output = "{\"status\":\"success\",\"data\":\""+newRegisters+"\"}"; 

		} 
		catch (Exception e) {
			output = "{\"status\":\"error\",\"data\":\"Error while updating the register.\"}"; 
			System.err.println(e.getMessage());
		}
		return output;
	}

	// Delete Details...............................

	public String deleteDetail(String account_no) {

		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from consumer_details where account_no=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(account_no));

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newRegisters = readDetails();
			output = "{\"status\":\"success\",\"data\":\""+newRegisters+"\"}"; 

		} 
		catch (Exception e) {
			output = "{\"status\":\"error\",\"data\":\"Error while deleting the register.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	

	// to show one particular consumer method
	public String getAccount(String email) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for reading";
			}


			Statement stmt = con.createStatement();
			String query = "select * from consumer_details where email='" + email + "'";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String account_no = Integer.toString(rs.getInt("account_no"));
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String uemail = rs.getString("email");
				String username = rs.getString("username");
				String password = rs.getString("password");

				output = "<form method='post' action='updateUser.jsp'>"
						+"<label>User Account : </label><span>"
						+ "<input name='account_no' type='text' value='" + account_no + "' class='form-control' readonly><br>"
						+"<label>User Name : </label><span>"
						+ "<input name='name' type='text' value='" + name + "' class='form-control' ><br>"
						+"<label>User Address : </label><span>"
						+ "<input name='address' type='text' value='" + address + "' class='form-control' ><br>"
						+"<label>Phone No : </label><span>"
						+ "<input name='phone' type='text' value='" + phone + "' class='form-control' ><br>"
						+"<label>Email Address : </label><span>"
						+ "<input name='uemail' type='email' value='" + uemail + "' class='form-control' ><br>"
						+"<label>Username : </label><span>"
						+ "<input name='username' type='text' value='" + username + "' class='form-control' readonly><br>"
						+"<label>Password : </label><span>"
						+ "<input name='password' type='password' value='" + password + "' class='form-control'readonly><br>"
						+"<input name='btnUpdate' type='submit' value='Update Account' class='btn btn-success'>"
						+"</form>"
						+"<form method='post' action='deleteUser.jsp'>"
						+ "<input name='btnSubmit' type='submit' value='Delete Account' class='btn btn-danger'>"
						+"<input name='account_no' type='hidden' value='" + account_no + "'>"
						+"</form>";
			}

			con.close();

	

		} catch (Exception e) {
			output = "Error while searching!";
			System.err.println(e.getMessage());
		}

		return output;

	}
	
}
