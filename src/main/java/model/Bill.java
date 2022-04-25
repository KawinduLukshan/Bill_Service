package model;
import java.sql.*;

public class Bill {

	//A common method to connect to the DB
	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", "");
	 }
	 
	 catch (Exception e)
	 {e.printStackTrace();}
	 return con;
	 }
	public String insertBill(String number, String type, String description, String amount, String receipt)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for inserting."; }
	 
	 // create a prepared statement
	 String query = " insert into bill_details(`bill_ID_number`,`bill_number`,`bill_type`,`bill_description`,`total_amount`,`bill_receipt`)"
	 + " values (?, ?, ?, ?, ?, ?)";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 
	 // binding values
	 preparedStmt.setInt(1, 0);
	 preparedStmt.setString(2, number);
	 preparedStmt.setString(3, type);
	 preparedStmt.setString(4, description);
	 preparedStmt.setDouble(5, Double.parseDouble(amount));
	 preparedStmt.setString(6, receipt);
	 
	 // execute the statement
	 
	 preparedStmt.execute();
	 con.close();
	 output = "Inserted successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while inserting the bill.";
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
	 {return "Error while connecting to the database for reading."; }
	 
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Bill_number</th><th>Bill_type</th>" +
	 "<th>Bill_description</th>" +
	 "<th>Total_amount</th>" +
	 "<th>Bill_receipt</th>" +
	 "<th>Update</th><th>Remove</th></tr>";

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
	 output += "<tr><td>" + bill_number + "</td>";
	 output += "<td>" + bill_type + "</td>";
	 output += "<td>" + bill_description + "</td>";
	 output += "<td>" + total_amount + "</td>";
	 output += "<td>" + bill_receipt + "</td>";
	
	 // buttons
	 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
	 + "<td><form method='post' action='bill_details.jsp'>"
	 + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
	 + "<input name='bill_ID_number' type='hidden' value='" + bill_ID_number
	 + "'>" + "</form></td></tr>";
	 }
	 con.close();
	 // Complete the html table
	 
	 output += "</table>";
	 }
	 catch (Exception e)
	 {
	 output = "Error while reading the bill.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	
	
	
	public String updateBill(String ID_number, String number, String type, String description, String amount, String receipt)
	{
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
			 
		 {return "Error while connecting to the database for updating."; }
		 // create a prepared statement
		 
		 String query = "UPDATE bill_details SET bill_number=?,bill_type=?,bill_description=?,total_amount=?,bill_receipt=?WHERE bill_ID_number=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 
		// preparedStmt.setString(1, ID_number);
		 preparedStmt.setString(1, number); 
		 preparedStmt.setString(2, type);
		 preparedStmt.setString(3, description);
		 preparedStmt.setDouble(4, Double.parseDouble(amount));
		 preparedStmt.setString(5, receipt);
		 preparedStmt.setInt(6, Integer.parseInt(ID_number));
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 output = "Updated successfully";
		 }
		 catch (Exception e)
		 {
		 output = "Error while updating the bill.";
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
		 {return "Error while connecting to the database for deleting."; }
		 // create a prepared statement
		 
		 String query = "delete from bill_details where bill_ID_number=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 
		 preparedStmt.setInt(1, Integer.parseInt(bill_ID_number));
		 // execute the statement
		 
		 preparedStmt.execute();
		 con.close();
		 output = "Deleted successfully";
		 }
		 catch (Exception e)
		 {
		 output = "Error while deleting the bill.";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 }
}
