package cardealership;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class BookAppointmentController implements Initializable{

	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private ComboBox<String> date,time,customerID,vinNo,employeeID;
	
	@FXML
	private TextField customerName,maker,model,year,packageID,serviceTime,purDate;
	
	@FXML
	private Button schedule,clear;
	
	@FXML
	private Label ldate,ltime,lcustomerID,lvinNo,lemployeeID;
	
	@FXML
	private Label lcustomerName,lmaker,lmodel,lyear,lpacakgeID,lserviceTime,lpurDate;
	
	private DAO db;
	private int yearDiff;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		db = new DAO();
		
		hideNode();
		
		ObservableList<String> dateList = date.getItems();
		dateList.add("Choose Date");
		dateList.addAll(db.getBookingDate());
		date.getSelectionModel().select(0);
		
		customerName.setEditable(false);
		maker.setEditable(false);
		model.setEditable(false);
		year.setEditable(false);
		purDate.setEditable(false);
		packageID.setEditable(false);
		serviceTime.setEditable(false);
		
		date.setOnAction(this::handleDate);
		time.setOnAction(this::handleTime);
		customerID.setOnAction(this::handleCustomerID);
		vinNo.setOnAction(this::handleVinNo);
		clear.setOnAction(this::handleClear);
		schedule.setOnAction(this::handleSchedule);
		
		schedule.disableProperty().bind(Bindings.createBooleanBinding(
				() -> validate(), employeeID.valueProperty()
				));
		
	}
	
	private boolean validate()
	{
		if(employeeID.getSelectionModel().getSelectedIndex()<=0)
			return true;
		return false;
	}
	
	private void hideNode()
	{
		for(Node node : anchorPane.getChildren())
		{
			String nodeID = node.getId();
			
			if(nodeID!=null)
			{
				if(!(nodeID.equalsIgnoreCase("date")||nodeID.equalsIgnoreCase("ldate")))
					node.setVisible(false);
			}			
		}
	}
	
	private void showNode()
	{
		for(Node node : anchorPane.getChildren())
		{
			if(!node.isVisible())
				node.setVisible(true);		
		}
	}
	
	
	private void handleDate(ActionEvent event)
	{
		if(date.getSelectionModel().getSelectedIndex()>0)
		{
			ltime.setVisible(true);
			time.setVisible(true);
			
			ObservableList<String> timeList = time.getItems();
			timeList.clear();
			timeList.add(0,"Choose Time Slot");
			timeList.addAll(db.getBookingTimeSlot(date.getValue()));
			time.getSelectionModel().select(0);			
		}
		else
			hideNode();
	}
	
	private void handleTime(ActionEvent event)
	{
		if(time.getSelectionModel().getSelectedIndex()>0)
		{
			lcustomerID.setVisible(true);
			customerID.setVisible(true);
			
			ObservableList<String> customerList = customerID.getItems();
			customerList.add("Select Customer ID");
			customerList.addAll(db.getCustomerID("Customer_Car"));
			
			customerID.getSelectionModel().select(0);						
		}
		else
		{
			hideNode();
			ltime.setVisible(true);
			time.setVisible(true);
		}
	}
	
	private void handleCustomerID(ActionEvent event)
	{
		if(customerID.getSelectionModel().getSelectedIndex()>0)
		{
			lvinNo.setVisible(true);
			vinNo.setVisible(true);
			
			ObservableList<String> vinList = vinNo.getItems();
			vinList.clear();
			vinList.add("Select VIN NO");
			vinList.addAll(db.getVinNO("Customer_Car",Integer.parseInt(customerID.getValue())));
			
			vinNo.getSelectionModel().select(0);				
		}
	}
	
	private void handleVinNo(ActionEvent event)
	{
		if(vinNo.getSelectionModel().getSelectedIndex()>0)
		{
			showNode();
			ObservableList<String> employeeList = employeeID.getItems();
			employeeList.add("Select Employee ID");
			employeeList.addAll(db.getSalesEmployeeID("Mechanic"));
			employeeID.getSelectionModel().select(0);
			
			customerName.setText(db.getCustomerName(Integer.parseInt(customerID.getValue())));
			maker.setText(db.getMaker(vinNo.getValue()));
			model.setText(db.getModel(vinNo.getValue()));
			year.setText(db.getYear(vinNo.getValue()));
			
			String purchasedDate = db.getPurchasedDate(vinNo.getValue());
			purDate.setText(purchasedDate);
			
			yearDiff = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(purchasedDate.substring(0, 4));
			
			if(yearDiff>6)
				yearDiff=10;
			else if(yearDiff<1)
				yearDiff=1;
			
			packageID.setText(yearDiff + " Year Service");
			serviceTime.setText(db.getServiceTime(yearDiff));
							
		}
		else
		{
			hideNode();
			ltime.setVisible(true);
			time.setVisible(true);
			lcustomerID.setVisible(true);
			customerID.setVisible(true);
			lvinNo.setVisible(true);
			vinNo.setVisible(true);			
		}
	}

	private void handleClear(ActionEvent event)
	{
		hideNode();
		date.getSelectionModel().select(0);
	}
	
	private void handleSchedule(ActionEvent event)
	{
		Alert alert = new Alert(AlertType.NONE);
		try
		{
			int appointmentID = db.createAppointment(date.getValue(), time.getValue(), customerID.getValue(), vinNo.getValue(), employeeID.getValue());
			if(appointmentID>0)
			{
				db.createServiceCharge(appointmentID,yearDiff,Integer.parseInt(employeeID.getValue()));
				alert.setAlertType(AlertType.INFORMATION);
				alert.setHeaderText("Appointment Scheduled Successfully");
				alert.setContentText("Appointment ID: " + appointmentID);
				alert.setTitle("Success");
				
			}
			else
			{
				alert.setAlertType(AlertType.ERROR);
				alert.setHeaderText("Appointment not Schduled.");
				alert.setContentText("Please check the input values...");
				alert.setTitle("Failure");
			}
			
		}
		catch(Exception e)
		{
			alert.setAlertType(AlertType.ERROR);
			alert.setHeaderText("Appointment not Schduled.");
			alert.setContentText("Please check the input values...");
			alert.setTitle("Failure");
		}
		alert.show();
	}
}
