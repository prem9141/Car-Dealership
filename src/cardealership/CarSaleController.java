package cardealership;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CarSaleController implements Initializable{
	
	@FXML
	private TextField soldPrice;
	
	@FXML
	private ComboBox<String> customerID,vinNo,employeeID;
	
	@FXML
	private DatePicker soldDate;
	
	@FXML
	private Button create,clear;
	
	@FXML
	private AnchorPane anchorPane;
	
	private DAO db;
	
	public void initialize(URL arg0,ResourceBundle arg1)
	{
		db = new DAO();
		
		ObservableList<String> customerList = customerID.getItems();
		customerList.add("Select Customer ID");
		customerList.addAll(db.getCustomerID("Customer"));
		
		ObservableList<String> employeeList = employeeID.getItems();
		employeeList.add("Select Employee ID");
		employeeList.addAll(db.getSalesEmployeeID("Sales Executive"));
		
		ObservableList<String> vinList = vinNo.getItems();
		vinList.add("Select VIN NO");
		vinList.addAll(db.getVinNO("Car","for sale"));
		
		soldDate.setEditable(false);
		customerID.getSelectionModel().select(0);
		employeeID.getSelectionModel().select(0);
		vinNo.getSelectionModel().select(0);
		
		clear.setOnAction(this::handleClear);
		create.setOnAction(this::handleCreate);
			
		
		create.disableProperty().bind(Bindings.createBooleanBinding(
		() -> validate(),soldPrice.textProperty(),
		soldDate.valueProperty(),customerID.valueProperty(),
		vinNo.valueProperty(),employeeID.valueProperty())
		);
	}
	
	private boolean validate()
	{
		if(soldPrice.getText().isBlank())
			return true;
		if(customerID.getSelectionModel().getSelectedIndex()<=0)
			return true;
		if(employeeID.getSelectionModel().getSelectedIndex()<=0)
			return true;
		if(vinNo.getSelectionModel().getSelectedIndex()<=0)
			return true;
		if(soldDate.getValue()==null)
			return true;
		return false;
	}

	private void handleClear(ActionEvent event)
	{
			
		for(Node node : anchorPane.getChildren())
		{
			String component = node.getTypeSelector();
			
			if(component.equalsIgnoreCase("TextField"))
				((TextField)node).clear();
			else if(component.equalsIgnoreCase("ComboBox"))
				((ComboBox<String>)node).getSelectionModel().select(0);
			else if(component.equalsIgnoreCase("DatePicker"))
				((DatePicker)node).getEditor().clear();
		}
	}
	
	private void handleCreate(ActionEvent event)
	{
		ArrayList<String> formValues = new ArrayList<String>();
		Alert alert = new Alert(AlertType.NONE);
		
		try
		{
			for(Node node : anchorPane.getChildren())
			{
				String component = node.getTypeSelector();
				if(component.equalsIgnoreCase("TextField"))
					formValues.add(((TextField)node).getText());
				else if(component.equalsIgnoreCase("ComboBox"))
					formValues.add(((ComboBox<String>)node).getValue());
				else if(component.equalsIgnoreCase("DatePicker"))
					formValues.add(((DatePicker)node).getValue().toString());
			}	
			
			
			DAO db = new DAO();
			db.updateCarCategory(formValues.get(1),"sold");
			
			int profit = Integer.parseInt(formValues.get(4)) - db.getCarPrice(formValues.get(1),"Min_Selling_Price");
			db.createCustomerCar(formValues.get(0),formValues.get(1),formValues.get(3),profit);
			
			
			int discount = db.getCarPrice(formValues.get(1),"Selling_Price") - Integer.parseInt(formValues.get(4));
			int invoiceID = db.createInvoice(formValues.get(0),formValues.get(1),formValues.get(2),formValues.get(3),formValues.get(4),discount);
			
			if(invoiceID>0)
			{
				alert.setAlertType(AlertType.INFORMATION);
				alert.setHeaderText("Invoice Successfully Generated");
				alert.setContentText("Invoice No: " + invoiceID);
				alert.setTitle("Success");
				
			}
			else
			{
				alert.setAlertType(AlertType.ERROR);
				alert.setHeaderText("Invoice not created.");
				alert.setContentText("Please check the input values...");
				alert.setTitle("Failure");
			}
			
		}
		catch(Exception e)
		{
			alert.setAlertType(AlertType.ERROR);
			alert.setHeaderText("Invoice not created.");
			alert.setContentText("Please check the input values...");
			alert.setTitle("Failure");
		}
		alert.show();
	}
}
