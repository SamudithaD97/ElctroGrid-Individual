package com;

import java.sql.*;

public class bill {
	
	//A common method to connect to the DB
	private Connection connect()
	{
		Connection con = null;
		try
		{
		Class.forName("com.mysql.cj.jdbc.Driver");

		//Provide the correct details: DBServer/DBName, username, password
		con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/list", "root", "12345");
		}
		 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
	
	//insert into database
	public String insertBill(String locationCode, String locationName, String date, String time)
	{
		String output = "";
		
		try
		{
			
			Connection con = connect();
			 
				if (con == null)
				{
					output = "{\"status\":\"error\", \"data\": \"Error while inserting the bill.\"}";
				}
				
				// create a prepared statement
				String query = " insert into items (`locationId`,`locationCode`,`locationName`,`date`,`time`)"
						+ " values (?, ?, ?, ?, ?)";
			 
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, locationCode);
				preparedStmt.setString(3, locationName);
				preparedStmt.setString(4, date);
				preparedStmt.setString(5, time);

			 

				// execute the statement
				preparedStmt.execute();
				con.close();
				
				String newItems = readItems();
				 output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}"; 
			 
			}
		 
		 
		 	catch (Exception e)
		 	{
		 
		 		output = "{\"status\":\"error\", \"data\": \"Error while inserting the bill.\"}";
		 		System.err.println(e.getMessage());
		 	}
		 	return output;
	}
	
	//read data from database.
	public String readItems()
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading."; 
			}
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr>"
						+"<th>Location Code</th>"
						+ "<th>Location Name</th>"
						+"<th>Date</th>"
						+"<th>Time</th>"
						+"<th>Update</th><th>Remove</th></tr>";


			// create a prepared statement
			String query = "select * from items";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next())
			{
				//get data from database and assign to local variables.
				String locationId = Integer.toString(rs.getInt("locationId"));
				String locationCode = rs.getString("locationCode");
				String locationName = rs.getString("locationName");
				String date = rs.getString("date");
				String time = rs.getString("time");
		 
				// Add into the html table
				output += "<tr><td><input locationId=\'hidbillIDUpdate\' locationCode=\'hidbillIDUpdate\' type=\'hidden\' value=\'" + locationId + "'>'" + locationCode + "</td>";
				output += "<td>" + locationName + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + time + "</td>";
		 
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-billid='"
						+ locationId + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	//Update
	public String updateBill(String locationId, String locationCode, String locationName, String date, String time)
	{
		String output = "";
		try
		{
				 
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database for updating."; 
				}
			 
				// create a prepared statement
				String query = "UPDATE items SET bname=?,bdate=?,accno=?,prereading=?,curreading=?,units=?,total=?  WHERE billID=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setString(1, locationCode);
				preparedStmt.setString(2, locationName);
				preparedStmt.setString(3, date);
				preparedStmt.setString(4, time);
				preparedStmt.setInt(5, Integer.parseInt(locationId));
			 
			
				// execute the statement
				preparedStmt.execute();
				con.close();

				String newItems = readItems();
				output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\": \"Error while updating the Bill.\"}";
				System.err.println(e.getMessage());
			}
			return output;
		}
			
		//Delete
		public String deleteBill(String locationId)
			 {
			 	String output = "";
			 	try
			 	{
			 		Connection con = connect();
			 		if (con == null)
			 		{
			 			return "Error while connecting to the database for deleting."; 
			 		}
			 
			 		// create a prepared statement
			 		String query = "delete from items where locationId=?";
			 		PreparedStatement preparedStmt = con.prepareStatement(query);
			 		
			 		// binding values
			 		preparedStmt.setInt(1, Integer.parseInt(locationId));
			 		
			 		// execute the statement
			 		preparedStmt.execute();
			 		con.close();

			 		String newItems = readItems();
					output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
			 		
			 	}
			 	catch (Exception e)
			 	{
			 		output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
			 		System.err.println(e.getMessage());
			 	}
			 	return output;
			 }

}