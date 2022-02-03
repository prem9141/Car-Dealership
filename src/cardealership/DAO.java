package cardealership;

import java.sql.DriverManager;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;

public class DAO {

	private String url,uname,pwd,db;
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	//	DAO dao = new DAO();
		
	}
	
	public DAO()
	{
		url = "http://localhost/";
		db = "car";
		uname = "root";
		pwd = "root";
		
	}
	
	private void connect()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url+db,uname,pwd);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void close()
	{
		try
		{
			rs.close();
			psmt.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public int createCustomer(ArrayList<String> values)
	{
		int customerID = 0;
		try
		{
			connect();
			psmt = con.prepareStatement("Insert into Customer(First_Name,Last_Name,Middle_Name,Building_Apt,Address,"
					+ "Country,State,City,Zip_Code,Phone_No,DOB,Age,Type) Values(?,?,?,?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			for(int i=1;i<=10;i++)
			{
				psmt.setString(i, values.get(i-1));
			}
			
			psmt.setDate(11, Date.valueOf(values.get(10)));
			psmt.setInt(12,Calendar.getInstance().get(Calendar.YEAR)-Integer.parseInt(values.get(10).substring(0, 4)));
			psmt.setString(13, values.get(11));
			
			psmt.executeUpdate();
			rs = psmt.getGeneratedKeys();
			
			while(rs.next())
			{
				customerID = rs.getInt(1);
			}
			
			close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			customerID = 0;					
		}
		return customerID;
	}
		
	public ArrayList<String> getCustomerID(String table)
	{
		ArrayList<String> customerID = new ArrayList<String>();
		try
		{
			connect();
			psmt = con.prepareStatement("select DISTINCT(Customer_ID) from "+table);
				
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				customerID.add(rs.getString("Customer_ID"));
			}
			close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			customerID.clear();
			
		}
		return customerID;
	}
	
	public String getCustomerName(int customerID)
	{
		String customerName = "";
		try
		{
			connect();
			psmt = con.prepareStatement("select First_Name,Last_Name from Customer where Customer_ID="+customerID);
				
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				customerName = rs.getString("First_Name") + " " + rs.getString("Last_Name");
			}
			close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return customerName;
	}
		
		
		
	public ArrayList<String> getSalesEmployeeID(String jobType)
	{
		ArrayList<String> employeeID = new ArrayList<String>();
		try
		{
			connect();
			psmt = con.prepareStatement("select Employee_ID from Employee where Job_Type='" +jobType+ "'");
				
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				employeeID.add(rs.getString("Employee_ID"));
			}
			close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			employeeID.clear();
			
		}
		return employeeID;
	}
	
	public ArrayList<String> getVinNO(String table, String category)
	{
		ArrayList<String> vinNo = new ArrayList<String>();
		try
		{
			connect();
			psmt = con.prepareStatement("select DISTINCT(VIN_NO) from " + table + " where Category='" + category + "'");
				
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				vinNo.add(rs.getString("VIN_NO"));
			}
			close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			vinNo.clear();
			
		}
		return vinNo;
	}
	
	public ArrayList<String> getVinNO(String table, int customerID)
	{
		ArrayList<String> vinNo = new ArrayList<String>();
		try
		{
			connect();
			psmt = con.prepareStatement("select VIN_NO from " + table + " where Customer_ID=" +customerID);
				
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				vinNo.add(rs.getString("VIN_NO"));
			}
			close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			vinNo.clear();
			
		}
		return vinNo;
	}
	
	public String getMaker(String vinNo)
	{
		String maker = "";
		try
		{
			connect();
			psmt = con.prepareStatement("select DISTINCT(Maker) from Car where VIN_NO='"+vinNo+"'");
			
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				maker = rs.getString("Maker");
			}
			close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return maker;
	}
	
	public String getModel(String vinNo)
	{
		String model = "";
		try
		{
			connect();
			psmt = con.prepareStatement("select DISTINCT(Model) from Car where VIN_NO='"+vinNo+"'");
			
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				model = rs.getString("Model");
			}
			close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return model;
	}
	
	public String getYear(String vinNo)
	{
		String year = "";
		try
		{
			connect();
			psmt = con.prepareStatement("select DISTINCT(Year) from Car where VIN_NO='"+vinNo+"'");
			
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				year = rs.getString("Year").substring(0, 4);
				
			}
			close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return year;
	}
	
	public String getPurchasedDate(String vinNo)
	{
		String date = "";
		try
		{
			connect();
			psmt = con.prepareStatement("select Purchased_Date from Customer_Car where VIN_NO='"+vinNo+"'");
			
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				date = rs.getString("Purchased_Date");
			}
			close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return date;
	}
	
	public ArrayList<String> getBookingDate()
	{
		ArrayList<String> date = new ArrayList<String>();
		try
		{
			connect();
			psmt = con.prepareStatement("select DISTINCT(Date) from Booking where Slot_free='YES' and Date >= Current_Date()");
				
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				date.add(rs.getString("Date"));
			}
			close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			date.clear();
			
		}
		return date;
	}

	public ArrayList<String> getBookingTimeSlot(String date)
	{
		ArrayList<String> timeslot = new ArrayList<String>();
		try
		{
			connect();
			psmt = con.prepareStatement("select DISTINCT(Time_Slot) from Booking where Date='" + date + "' and Slot_Free='YES'");
				
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				timeslot.add(rs.getString("Time_Slot"));
			}
			close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			timeslot.clear();
			
		}
		return timeslot;
	}
	
	public void updateCarCategory(String vinNo, String category)
	{
		try
		{
			connect();
			psmt = con.prepareStatement("Update Car set Category='"+category+"' where VIN_NO='"+vinNo+"'");
			psmt.executeUpdate();
			psmt.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void updateServiceResult(int appointmentID,String partID, String result)
	{
		try
		{
			connect();
			psmt = con.prepareStatement("Update Service_Charges set Diagnosis_Result='"+result+"' where Appointment_ID="+appointmentID+
					" and Part_ID='"+partID+"'");
			psmt.executeUpdate();
			psmt.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void updateSlotFree(String date, String time)
	{
		try
		{
			connect();
			psmt = con.prepareStatement("Update Booking set Slot_free='No'"
					+ " where Date='"+date+"' and Time_slot='"+time+"'");
			psmt.executeUpdate();
			psmt.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public int updateCarDropDate(int appointmentID, String date)
	{
		try
		{
			connect();
			psmt = con.prepareStatement("Update Appointment set Car_Drop_Date='"+Date.valueOf(date)
					+ "', Car_Drop_Time='"+new Time(System.currentTimeMillis()).toString()
					+"', Status='In Progress' where Appointment_ID="+appointmentID);
			psmt.executeUpdate();
			psmt.close();
			con.close();
			return 1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	public int updateCarPickupDate(int appointmentID, String date)
	{
		try
		{
			connect();
			psmt = con.prepareStatement("Update Appointment set Car_Pickup_Date='"+Date.valueOf(date)
					+ "', Car_Pickup_Time='"+new Time(System.currentTimeMillis()).toString()
					+"', Status='Delivered' where Appointment_ID="+appointmentID);
			psmt.executeUpdate();
			psmt.close();
			con.close();
			return 1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	public int updateCarServiceStatus(int appointmentID)
	{
		try
		{
			connect();
			psmt = con.prepareStatement("Update Appointment set Status='Serviced' where Appointment_ID="+appointmentID);
			psmt.executeUpdate();
			psmt.close();
			con.close();
			return 1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	
	
	public void createCustomerCar(String customerID, String vinNo, String date, int profit)
	{
		try
		{
			connect();
			psmt = con.prepareStatement("Insert into Customer_Car(Customer_ID,VIN_NO,Purchased_Date,Profit)"
					+ "values(?,?,?,?)");
			
			psmt.setInt(1,Integer.parseInt(customerID));
			psmt.setString(2,vinNo);
			psmt.setDate(3,Date.valueOf(date));
			psmt.setInt(4,profit);
			
			psmt.executeUpdate();
			psmt.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public int createInvoice(String customerID, String vinNo, String employeeID, String date, String price, int discount)
	{
		int invoiceID = 0;
		try
		{
			connect();
			psmt = con.prepareStatement("Insert into Invoice(Customer_ID,VIN_NO,Employee_ID,Date,Price,Discount)"
					+ "values(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			psmt.setInt(1,Integer.parseInt(customerID));
			psmt.setString(2,vinNo);
			psmt.setInt(3,Integer.parseInt(employeeID));
			psmt.setDate(4,Date.valueOf(date));
			psmt.setInt(5,Integer.parseInt(price));
			psmt.setInt(6,discount);
			
			psmt.executeUpdate();
			
			rs = psmt.getGeneratedKeys();
			
			while(rs.next())
			{
				invoiceID = rs.getInt(1);
			}
			close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return invoiceID;
	}
	
	public int getCarPrice(String vinNo,String colName)
	{
		int price = 0;
		try
		{
			connect();
			psmt = con.prepareStatement("select "+colName+" from Car where VIN_NO='"+vinNo+"'");
			
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
				price = rs.getInt(1);
			}
			close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return price;
	}
	
	public String getServiceTime(int packageID)
	{
		int serviceTime = 0;
		int hours =0,minutes=0,seconds=0;
		String[] time;
		String checkTime = "";
		String replaceTime = "";
		
		try
		{
			connect();
			psmt = con.prepareStatement("select Labor_Check_Time from Parts where Part_ID in"
					+ " (select Part_ID from Package where Package_ID="+packageID+")");
			
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
				checkTime = rs.getString("Labor_Check_Time");
				time = checkTime.split(":");
				serviceTime +=  (Integer.parseInt(time[0])*3600 + Integer.parseInt(time[1])*60 +
								Integer.parseInt(time[2]));
			}
			
			psmt = con.prepareStatement("select Labor_Replace_Time from Parts where Part_ID in "
					+ "(select Part_ID from Package where Replace_Part='YES' and Package_ID="+packageID+")");
			
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
				replaceTime = rs.getString("Labor_Replace_Time");
				time = replaceTime.split(":");
				serviceTime +=  (Integer.parseInt(time[0])*3600 + Integer.parseInt(time[1])*60 +
								Integer.parseInt(time[2]));
			}
			
			seconds = serviceTime%60;
			minutes = (serviceTime/60)%60;
			hours = serviceTime/3600;
			
			close();
			
			return String.format("%02d:%02d:%02d",hours,minutes,seconds);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "00:00:00";
		}
	}
	
	public int createAppointment(String date, String time, String customerID, String vinNo, String employeeID)
	{
		int appointmentID = 0;
		try
		{
			updateSlotFree(date, time);
			connect();
			psmt = con.prepareStatement("Insert into Appointment(Booked_date,Booked_time,Customer_ID,VIN_NO,Employee_ID,Status)"					
					+ "values(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			psmt.setDate(1,Date.valueOf(date));
			psmt.setTime(2,Time.valueOf(time));
			psmt.setInt(3,Integer.parseInt(customerID));
			psmt.setString(4,vinNo);
			psmt.setInt(5,Integer.parseInt(employeeID));
			psmt.setString(6,"Scheduled");
								
			psmt.executeUpdate();
			
			rs = psmt.getGeneratedKeys();
			
			while(rs.next())
			{
				appointmentID = rs.getInt(1);
			}
			close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return appointmentID;
	}
	
	public void createServiceCharge(int appointmentID, int  packageID, int employeeID)
	{
		
		PreparedStatement psmt1;
		ResultSet rs1;
		double wagePerSecond=0.00000;
		
		try
		{
			connect();
			
			
			psmt1 = con.prepareStatement("select Hourly_Wage from Employee where Employee_ID="+employeeID);
			rs1 = psmt1.executeQuery();
			
			while(rs1.next())
			{
				wagePerSecond = rs1.getInt("Hourly_Wage")/3600.00;
				//System.out.println("Hourly Wage:" + rs1.getInt("Hourly_Wage") + " " + "Wage Per Second:" + wagePerSecond);
			}
			
			
			//System.out.println("Appointment_ID\tPackage_ID\tPart_ID\tPart_Replaced\tPart_Price\tLaborPrice");
			
			psmt = con.prepareStatement("select * from Package where Package_ID="+packageID);
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
				String partID = rs.getString("Part_ID");
				String replacePart = rs.getString("Replace_Part");
				String[] replaceTime; 
				String[] checkTime;
				int partPrice = 0; float laborPrice = 0;
				
				if(replacePart.equalsIgnoreCase("YES"))
				{
					psmt1 = con.prepareStatement("select Part_Price from Warehouse where Part_ID='"+partID+"'");
					rs1 = psmt1.executeQuery();
					
					while(rs1.next())
					{
						partPrice = rs1.getInt("Part_Price");
					}
					
					psmt1 = con.prepareStatement("select Labor_Replace_Time from Parts where Part_ID='"+partID+"'");
					rs1 = psmt1.executeQuery();
					
					while(rs1.next())
					{
						replaceTime = rs1.getString("Labor_Replace_Time").split(":");
						laborPrice += (Integer.parseInt(replaceTime[0])*3600 + Integer.parseInt(replaceTime[1])*60 +
								Integer.parseInt(replaceTime[2]))*wagePerSecond;
					}
					
				}
				
				psmt1 = con.prepareStatement("select Labor_Check_Time from Parts where Part_ID='"+partID+"'");
				rs1 = psmt1.executeQuery();
				
				while(rs1.next())
				{
					checkTime = rs1.getString("Labor_Check_Time").split(":");
					//System.out.println((Integer.parseInt(checkTime[0])*3600 + Integer.parseInt(checkTime[1])*60 +
					//		Integer.parseInt(checkTime[2])));
					laborPrice += (Integer.parseInt(checkTime[0])*3600 + Integer.parseInt(checkTime[1])*60 +
							Integer.parseInt(checkTime[2]))*wagePerSecond;
				}
				
				psmt1 = con.prepareStatement("Insert into Service_Charges(Appointment_ID,Package_ID,"
						+ "Part_ID,Part_Replaced,Part_Price,Labor_Price) values(?,?,?,?,?,?)");
				
				psmt1.setInt(1,appointmentID);
				psmt1.setInt(2,packageID);
				psmt1.setString(3,partID);
				psmt1.setString(4,replacePart);
				psmt1.setInt(5,partPrice);
				psmt1.setBigDecimal(6,BigDecimal.valueOf(laborPrice));
				
				psmt1.executeUpdate();
			//	System.out.println(appointmentID+"\t"+packageID+"\t"+partID+
					//	"\t"+replacePart+"\t"+partPrice+"\t"+laborPrice);
				 
			}
			close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	public int insertAdditionalService(int appointmentID, String partID, String partReplace, String result, int employeeID)
	{
		
		PreparedStatement psmt1;
		ResultSet rs1;
		double wagePerSecond=0.00000;
		
		try
		{
			connect();
				
			psmt = con.prepareStatement("select Hourly_Wage from Employee where Employee_ID="+employeeID);
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
				wagePerSecond = rs.getInt("Hourly_Wage")/3600.00;
			}
			
			psmt = con.prepareStatement("select * from Parts where Part_ID='"+partID+"'");
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
				String[] replaceTime; 
				String[] checkTime;
				int partPrice = 0; float laborPrice = 0;
				
				if(partReplace.equalsIgnoreCase("YES"))
				{
					psmt1 = con.prepareStatement("select Part_Price from Warehouse where Part_ID='"+partID+"'");
					rs1 = psmt1.executeQuery();
					
					while(rs1.next())
					{
						partPrice = rs1.getInt("Part_Price");
					}
					
					psmt1 = con.prepareStatement("select Labor_Replace_Time from Parts where Part_ID='"+partID+"'");
					rs1 = psmt1.executeQuery();
					
					while(rs1.next())
					{
						replaceTime = rs1.getString("Labor_Replace_Time").split(":");
						laborPrice += (Integer.parseInt(replaceTime[0])*3600 + Integer.parseInt(replaceTime[1])*60 +
								Integer.parseInt(replaceTime[2]))*wagePerSecond;
					}
					
				}
				
				psmt1 = con.prepareStatement("select Labor_Check_Time from Parts where Part_ID='"+partID+"'");
				rs1 = psmt1.executeQuery();
				
				while(rs1.next())
				{
					checkTime = rs1.getString("Labor_Check_Time").split(":");
					//System.out.println((Integer.parseInt(checkTime[0])*3600 + Integer.parseInt(checkTime[1])*60 +
					//		Integer.parseInt(checkTime[2])));
					laborPrice += (Integer.parseInt(checkTime[0])*3600 + Integer.parseInt(checkTime[1])*60 +
							Integer.parseInt(checkTime[2]))*wagePerSecond;
				}
				
				psmt1 = con.prepareStatement("Insert into Service_Charges(Appointment_ID,Package_ID,"
						+ "Part_ID,Part_Replaced,Part_Price,Labor_Price,Diagnosis_Result) values(?,?,?,?,?,?,?)");
				
				psmt1.setInt(1,appointmentID);
				psmt1.setInt(2,0);
				psmt1.setString(3,partID);
				psmt1.setString(4,partReplace);
				psmt1.setInt(5,partPrice);
				psmt1.setBigDecimal(6,BigDecimal.valueOf(laborPrice));
				psmt1.setString(7,result);
				
				psmt1.executeUpdate();
			//	System.out.println(appointmentID+"\t"+packageID+"\t"+partID+
					//	"\t"+replacePart+"\t"+partPrice+"\t"+laborPrice);
				 
			}
			close();
			return 1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}		
		
	}
	
	public ArrayList<String> getAppointmentID(String status)
	{
		ArrayList<String> appointmentID = new ArrayList<String>();
		String statement;
		if(status.equalsIgnoreCase("*"))
			statement = "select Appointment_ID from Appointment";
		else
			statement = "select Appointment_ID from Appointment where Status='"+status+"'";
		
		try
		{
			connect();
			psmt = con.prepareStatement(statement);
				
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				appointmentID.add(rs.getString("Appointment_ID"));
			}
			close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			appointmentID.clear();
			
		}
		return appointmentID;
	}
	
	public ArrayList<String> getAppointmentDetails(int appointmentID)
	{
		ArrayList<String> appointmentDetails = new ArrayList<String>();
		ResultSet rs1;
				
		try
		{
			connect();
			psmt = con.prepareStatement("select * from Appointment where Appointment_ID="+appointmentID);
				
			rs1 = psmt.executeQuery();
				
			while(rs1.next())
			{
				appointmentDetails.add(rs1.getString("Customer_ID"));
				appointmentDetails.add(getCustomerName(Integer.parseInt(rs1.getString("Customer_ID"))));
				connect();
				appointmentDetails.add(rs1.getString("VIN_NO"));
				appointmentDetails.add(rs1.getString("Booked_date"));
				appointmentDetails.add(rs1.getString("Booked_time"));
				if(rs1.getString("Car_Drop_Date")==null)
					appointmentDetails.add("");
				else
					appointmentDetails.add(rs1.getString("Car_Drop_Date"));
				if(rs1.getString("Car_Drop_Time")==null)
					appointmentDetails.add("");
				else
					appointmentDetails.add(rs1.getString("Car_Drop_Time"));
				appointmentDetails.add(rs1.getString("Employee_ID"));
			}
			
			psmt = con.prepareStatement("select DISTINCT(Package_ID) from Service_Charges "
					+ "where Appointment_ID="+appointmentID + " and Package_ID>0");
			
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				appointmentDetails.add(rs.getString("Package_ID"));
			}
					
			psmt = con.prepareStatement("select SUM(Labor_price),SUM(Part_Price) from Service_Charges "
					+ "where Appointment_ID="+appointmentID);
			
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				double value= rs.getBigDecimal(1).doubleValue() + rs.getInt(2);
				appointmentDetails.add(String.valueOf(value));
				
			}
			
			rs1.close();
			close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			appointmentDetails.clear();
			
		}
		return appointmentDetails;
	}
	
	public ObservableList<ServiceProperty> getServiceProperty(int appointmentID)
	{
		ObservableList<ServiceProperty> serviceProperty = FXCollections.observableArrayList();
		PreparedStatement psmt1 = null;
		ResultSet rs1 = null;
		String partDesc = null;
		
		try
		{
			connect();
			psmt = con.prepareStatement("select * from Service_Charges "
				+ "where Appointment_ID="+appointmentID);
		
			rs = psmt.executeQuery();
			
					
			
			while(rs.next())
			{
				psmt1 = con.prepareStatement("select Part_Desc from Parts where Part_ID='"+rs.getString("Part_ID")+"'");
				rs1 = psmt1.executeQuery();
				
				while(rs1.next())
				{
					partDesc = rs1.getString("Part_Desc");
				}
				
				serviceProperty.add(new ServiceProperty(rs.getInt("Part_Price"),
						rs.getBigDecimal("Labor_Price").doubleValue(),rs.getString("Diagnosis_Result"),
						rs.getString("Part_ID"),rs.getString("Part_Replaced"),partDesc));
			}
			psmt1.close();
			rs1.close();
			close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			serviceProperty.clear();
		}
		return serviceProperty;
	}
	
	public ObservableList<SalesProperty> getSalesProperty(String from, String to)
	{
		ObservableList<SalesProperty> salesProperty = FXCollections.observableArrayList();

		try
		{
			connect();
			psmt = con.prepareStatement("select C.Maker,C.Model,C.Year,Count(*) as Sold_Cars,SUM(I.Price-C.Min_Selling_Price) as "
					+ "Profit from Car C,Invoice I where I.Date between '" + Date.valueOf(from) + "' and '"
					+ Date.valueOf(to) + "' and C.VIN_NO=I.VIN_NO group by C.Maker,C.Model,C.Year");
		
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
					salesProperty.add(new SalesProperty(rs.getString("Maker"),
					rs.getString("Model"),rs.getString("Year").substring(0,4),
					rs.getInt("Sold_Cars"),rs.getInt("Profit")));
			}
			close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			salesProperty.clear();
		}
		return salesProperty;
	}
	
	public ArrayList<String> getPartDesc(int appointmentID)
	{
		ArrayList<String> descList = new ArrayList<String>();

		try
		{
			connect();
			psmt = con.prepareStatement("select DISTINCT(Part_Desc) from Parts where Part_ID not in "
					+ "(select Part_ID from Service_Charges S where Appointment_ID="+appointmentID+")");
		
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
					descList.add(rs.getString("Part_Desc"));
			}
			close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			descList.clear();
		}
		return descList;
	}
	
	public ArrayList<String> getInvoice()
	{
		ArrayList<String> invoiceList = new ArrayList<String>();

		try
		{
			connect();
			psmt = con.prepareStatement("select Invoice_ID from Invoice");
		
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
				invoiceList.add(rs.getString("Invoice_ID"));
			}
			close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			invoiceList.clear();
		}
		return invoiceList;
	}
	
	public ArrayList<String> getInvoiceDetails(int invoiceID)
	{
		ArrayList<String> invoiceDetails = new ArrayList<String>(11);
		String vinNo = null;
		int customerID = 0;
		try
		{
			connect();
			psmt = con.prepareStatement("select * from Invoice where Invoice_ID="+invoiceID);
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
				customerID = rs.getInt("Customer_ID");
				invoiceDetails.add(0,String.valueOf(customerID));
				vinNo = rs.getString("VIN_NO");
				invoiceDetails.add(1,vinNo);
				invoiceDetails.add(2,String.valueOf(rs.getDate("Date")));
				invoiceDetails.add(3,String.valueOf(rs.getInt("Employee_ID")));
				invoiceDetails.add(4,String.valueOf(rs.getInt("Price")));
				invoiceDetails.add(5,String.valueOf(rs.getInt("Discount")));
				
			}
			
			psmt = con.prepareStatement("select First_Name, Last_Name, Phone_No from Customer where Customer_ID="+customerID);
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
				invoiceDetails.add(6,rs.getString("First_Name")+ " " + rs.getString("Last_Name"));
				invoiceDetails.add(7,rs.getString("Phone_No"));
			}
			
			psmt = con.prepareStatement("select Model, Maker, Year from Car where VIN_NO='"+vinNo+"'");
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
				invoiceDetails.add(8,rs.getString("Model"));
				invoiceDetails.add(9,rs.getString("Maker"));
				invoiceDetails.add(10,String.valueOf(rs.getDate("Year")).substring(0,4));
			}
			
			close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			invoiceDetails.clear();
		}
		return invoiceDetails;
	}
	
	public String getPartID(String desc)
	{
		String partID = null;
		try
		{
			connect();
			psmt = con.prepareStatement("select DISTINCT(Part_ID) from Parts where Part_Desc='"+desc+"'");
		
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
					partID =  rs.getString("Part_ID");
			}
			close();
			return partID;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isResultEmpty(int appointmentID)
	{
		try
		{
			connect();
			psmt = con.prepareStatement("select Count(*) as Total from Service_Charges where Appointment_ID=950026"
					+ " and Diagnosis_Result is null");
			
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				if(rs.getInt("Total")>0)
					return true;
			}
			close();
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return true;
		}
	}
}

