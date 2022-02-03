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


public class CustomerCreationController implements Initializable {

	@FXML
	private TextField firstName,middleName,lastName,buildingNo,address,city,zipcode,phoneNo;
	@FXML
	private Button create,clear;
	@FXML
	private ComboBox<String> country,state,custType;
	@FXML
	private DatePicker dob;
	@FXML
	private AnchorPane anchorPane;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<String> countryList = country.getItems();
		ObservableList<String> stateList = state.getItems();
		ObservableList<String> custTypeList = custType.getItems();
		
		countryList.add("Select Country");
		countryList.add("United States");
		stateList.add("Select State");
		stateList.add("California");
		stateList.add("Alabama");
		stateList.add("Alaska");
		stateList.add("Arizona");
		stateList.add("Arkansas");
		stateList.add("California");
		stateList.add("Colorado");
		stateList.add("Connecticut");
		stateList.add("Delaware");
		stateList.add("Florida");
		stateList.add("Georgia");
		stateList.add("Hawaii");
		stateList.add("Idaho");
		stateList.add("Illinois");
		stateList.add("Indiana");
		stateList.add("Iowa");
		stateList.add("Kansas");
		stateList.add("Kentucky");
		stateList.add("Louisiana");
		stateList.add("Maine");
		stateList.add("Maryland");
		stateList.add("Massachusetts");
		stateList.add("Michigan");
		stateList.add("Minnesota");
		stateList.add("Mississippi");
		stateList.add("Missouri");
		stateList.add("Montana");
		stateList.add("Nebraska");
		stateList.add("Nevada");
		stateList.add("New Hampshire");
		stateList.add("New Jersey");
		stateList.add("New Mexico");
		stateList.add("New York");
		stateList.add("North Carolina");
		stateList.add("North Dakota");
		stateList.add("Ohio");
		stateList.add("Oklahoma");
		stateList.add("Oregon");
		stateList.add("Pennsylvania");
		stateList.add("Rhode Island");
		stateList.add("South Carolina");
		stateList.add("South Dakota");
		stateList.add("Tennessee");
		stateList.add("Texas");
		stateList.add("Utah");
		stateList.add("Vermont");
		stateList.add("Virginia");
		stateList.add("Washington");
		stateList.add("West Virginia");
		stateList.add("Wisconsin");
		stateList.add("Wyoming");
		custTypeList.add("Select Customer Type");
		custTypeList.add("Service");
		custTypeList.add("Prospective");
		custTypeList.add("Sales");
		
		country.getSelectionModel().select(0);
		state.getSelectionModel().select(0);
		custType.getSelectionModel().select(0);
		
		clear.setOnAction(this::handleClear);
		create.setOnAction(this::handleCreate);
		
		dob.setEditable(false);
		
		create.disableProperty().bind(Bindings.createBooleanBinding(
				() -> validate(),firstName.textProperty(),
				lastName.textProperty(),address.textProperty(),
				city.textProperty(),zipcode.textProperty(),
				phoneNo.textProperty(),country.valueProperty(),
				state.valueProperty(),custType.valueProperty(),
				dob.valueProperty())
				);
	}
	
	private boolean validate()
	{
		
		
		if(firstName.getText().isBlank()||lastName.getText().isBlank())
			return true;
		if(address.getText().isBlank()||city.getText().isBlank()||zipcode.getText().isBlank())
			return true;
		if(phoneNo.getText().isBlank())
			return true;
		if(country.getSelectionModel().getSelectedIndex()<=0)
			return true;
		if(state.getSelectionModel().getSelectedIndex()<=0)
			return true;
		if(custType.getSelectionModel().getSelectedIndex()<=0)
			return true;
		if(dob.getValue()==null)
			return true;
		return false;
	}
	
	private void handleClear(ActionEvent e)
	{
		
		for(Node node : anchorPane.getChildren())
		{
			String component = node.getTypeSelector();
			if(component.equalsIgnoreCase("TextField"))
				((TextField)node).clear();
			else if(component.equalsIgnoreCase("ComboBox"))
			{
				((ComboBox<String>)node).getSelectionModel().select(0);
			}
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
					formValues.add(((ComboBox<String>)node).getSelectionModel().getSelectedItem());
				else if(component.equalsIgnoreCase("DatePicker"))
					formValues.add(((DatePicker)node).getValue().toString());
			}	
			
			
			
			DAO db = new DAO();
			int customerID = db.createCustomer(formValues);
			if(customerID>0)
			{
				alert.setAlertType(AlertType.INFORMATION);
				alert.setHeaderText("Customer created Successfully");
				alert.setContentText("Customer ID: " + customerID);
				alert.setTitle("Success");
				
			}
			else
			{
				alert.setAlertType(AlertType.ERROR);
				alert.setHeaderText("Customer not created.");
				alert.setContentText("Please check the input values...");
				alert.setTitle("Failure");
			}
			
		}
		catch(Exception e)
		{
			alert.setAlertType(AlertType.ERROR);
			alert.setHeaderText("Customer not created.");
			alert.setContentText("Please check the input values...");
			alert.setTitle("Failure");
		}
		alert.show();
	}		
		
	
}
