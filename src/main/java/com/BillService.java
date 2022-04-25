package com;
import model.Bill;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Bill")


public class BillService {

	Bill billObj = new Bill();
	@GET

	@Produces(MediaType.TEXT_HTML)
	public String readBill()
	 {
		return billObj.readBill(); 
	 }

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertbill(@FormParam("bill_number") String bill_number,
	 @FormParam("bill_type") String bill_type,
	 @FormParam("bill_description") String bill_description,
	 @FormParam("total_amount") String total_amount,
	 @FormParam("bill_receipt") String bill_receipt)
	{
	 String output = billObj.insertBill(bill_number, bill_type, bill_description, total_amount, bill_receipt);
	return output;
	}


	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBill(String billData)
	{
		
	//Convert the input string to a JSON object
	 JsonObject billObject = new JsonParser().parse(billData).getAsJsonObject();
	
	 //Read the values from the JSON object
	 String bill_ID_number = billObject.get("bill_ID_number").getAsString();
	 String bill_number = billObject.get("bill_number").getAsString();
	 String bill_type = billObject.get("bill_type").getAsString();
	 String bill_description = billObject.get("bill_description").getAsString();
	 String total_amount = billObject.get("total_amount").getAsString();
	 String bill_receipt = billObject.get("bill_receipt").getAsString();
	 String output = billObj.updateBill(bill_ID_number, bill_number, bill_type, bill_description, total_amount, bill_receipt);
	return output;
	}


	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteBill(String billData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(billData, "", Parser.xmlParser());

	//Read the value from the element <bill_ID_number>
	 String bill_ID_number = doc.select("bill_ID_number").text();
	 String output = billObj.deleteBill(bill_ID_number);
	return output;
	}


	
}
