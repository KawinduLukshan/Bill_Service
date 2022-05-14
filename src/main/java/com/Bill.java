package com;

import java.sql.*;



public class Bill {
	public Connection connect()
	{
		Connection con = null;

	 try
	 {
		 Class.forName("com.mysql.jdbc.Driver");
		 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid",
		 "root", "");
		 
		 //For testing
		 System.out.print("Successfully connected");
	 }
	 
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }

	 return con;
	}
	
	public String insertBill(String bill_number, String bill_type, String bill_description, String total_amount, String bill_receipt)
	
	{
	 String output = "";
	try
	 {
		 Connection con = connect();
		 if (con == null)
	 {
			 return "Error while connecting to the database";
	 }
		 
		 
	 // create a prepared statement
		 
		 String query = " insert into bill_details(`bill_ID_number`,`bill_number`,`bill_type`,`bill_description`,`total_amount`,`bill_receipt`)"
		 + " values (?, ?, ?, ?, ?, ?)";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
	 
	 
	 // binding values
	 
		 preparedStmt.setInt(1, 0);
		 preparedStmt.setString(2, bill_number);
		 preparedStmt.setString(3, bill_type);
		 preparedStmt.setString(4, bill_description);
		 preparedStmt.setDouble(5, Double.parseDouble(total_amount));
		 preparedStmt.setString(6, bill_receipt);
	 
	 
	 //execute the statement
	 
		 preparedStmt.execute();
		 con.close();
		 String newBill = readBill();
		 output = "{\"status\":\"success\", \"data\": \"" +
		 newBill + "\"}"; 

	 }
	
	catch (Exception e)
	{
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the Bill.\"}";
			 System.err.println(e.getMessage());
	}
	
		return output;
	}
	
	
	
	
	public String readBill()
	
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
		 
		 output = "<table border='1'>"
		 		 + "<tr><th>bill_number</th>"
				 + "<th>bill_type</th>"
				 + "<th>bill_description</th>"
				 + "<th>total_amount</th>"
				 + "<th>bill_receipt</th>"
				 + "<th>Update</th><th>Remove</th></tr>";
	 
		 String query = "select * from bill_details";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
	 
	 
	 // iterate through the rows in the result set
		 
		 while (rs.next())
		 {
			 String bill_ID_number = Integer.toString(rs.getInt("bill_ID_number"));
			 String bill_number = rs.getString("bill_number");
			 String bill_type = rs.getString("bill_type");
			 String bill_description = rs.getString("bill_description");
			 String total_amount = Double.toString(rs.getDouble("total_amount"));
			 String bill_receipt = rs.getString("bill_receipt");
	 
	// Add into the html table
			 output += "<tr><td><input id='hidbill_ID_numberUpdate'name='hidbill_ID_numberUpdate'type='hidden' value='" + bill_ID_number
			  + "'>" + bill_number + "</td>";
			  output += "<td>" + bill_type + "</td>";
			  output += "<td>" + bill_description + "</td>";
			  output += "<td>" + total_amount + "</td>";
			  output += "<td>" + bill_receipt + "</td>";
			  
	// buttons
			  output += "<td><input name='btnUpdate' type='button' value='Update' "
					  + "class='btnUpdate btn btn-secondary' data-billnumber='" + bill_ID_number+ "'></td>"
					  + "<td><input name='btnRemove' type='button' value='Remove' "
					  + "class='btnRemove btn btn-danger' data-billnumber='" + bill_ID_number + "'></td></tr>";
					  }
		 
	 con.close();
	 // Complete the html table
	 output += "</table>";
	 }
		
	catch (Exception e)
	 {
		 output = "Error while reading the Bill.";
		 System.err.println(e.getMessage());
	 }
		
	return output;
	}
	
	
	
	public String updateBill(String bill_ID_number, String bill_number, String bill_type, String bill_description, String total_amount, String bill_receipt)
	 
	 {
		 String output = "";
		 try
	 {
			 
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for updating."; }
		 
		 
			 // create a prepared statement
			 String query = "UPDATE bill_details SET bill_number=?,bill_type=?,bill_description=?,total_amount=?,bill_receipt=? WHERE bill_ID_number=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setString(1, bill_number);
			 preparedStmt.setString(2, bill_type);
			 preparedStmt.setString(3, bill_description);
			 preparedStmt.setDouble(4, Double.parseDouble(total_amount));
			 preparedStmt.setString(5, bill_receipt);
			 preparedStmt.setInt(6, Integer.parseInt(bill_ID_number));
			 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 String newBill = readBill();
			 output = "{\"status\":\"success\", \"data\": \"" + newBill + "\"}";
	 }
	 catch (Exception e)
	 {
			 output = "{\"status\":\"error\", \"data\":\"Error while updating the Bill.\"}";
			 System.err.println(e.getMessage());
		 }
		 
		 return output;
	 } 
	
	
	
	public String deleteBill(String bill_ID_number)
	
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
	 String query = "delete from bill_details where bill_ID_number=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(bill_ID_number));

	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 String newBill = readBill();
	 output = "{\"status\":\"success\", \"data\": \"" +
	 newBill + "\"}";
	 }
		
	catch (Exception e)
	{
		 output = "{\"status\":\"error\", \"data\":\"Error while deleting the Bill.\"}";
		 System.err.println(e.getMessage());
		 }
	return output;
	}
}