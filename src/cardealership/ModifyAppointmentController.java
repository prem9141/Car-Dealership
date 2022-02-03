package cardealership;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class ModifyAppointmentController implements Initializable{

	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private ComboBox<String> change,appointmentID;
	
	@FXML
	private Button update,clear;
	
	@FXML
	private Label lchange,ldate,lappointmentID;
	
	@FXML
	private DatePicker date;
		
	private DAO db;
	
	private ObservableList<String> appointmentlist;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		db = new DAO();
		
		hideNode();
		
		ObservableList<String> changelist = change.getItems();
		changelist.add("Choose an Option");
		changelist.add("Update PickUp Date");
		changelist.add("Update Drop Date");
		
		change.getSelectionModel().select(0);
		
		appointmentlist = appointmentID.getItems();
		
		
		date.setEditable(false);
		date.setDisable(true);
		date.setValue(LocalDate.now());
		
		change.setOnAction(this::handleChange);
		appointmentID.setOnAction(this::handleAppointment);
		update.setOnAction(this::handleUpdate);
		clear.setOnAction(this::handleClear);
		
		update.disableProperty().bind(Bindings.createBooleanBinding(
				()-> validate(),date.valueProperty()				
				));
	}
	
	private boolean validate()
	{
		if(date.getValue()==null)
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
				if(!(nodeID.equalsIgnoreCase("lchange")||nodeID.equalsIgnoreCase("change")))
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
	
	private void handleChange(ActionEvent event)
	{
		if(change.getSelectionModel().getSelectedIndex()>0)
		{
			appointmentID.setVisible(true);
			lappointmentID.setVisible(true);
			appointmentlist.clear();
			appointmentlist.add("Select Appointment");
			if(change.getSelectionModel().getSelectedIndex()==1)
				appointmentlist.addAll(db.getAppointmentID("Serviced"));
			else
				appointmentlist.addAll(db.getAppointmentID("Scheduled"));	
			appointmentID.getSelectionModel().select(0);
			
		}
		else
		{
			hideNode();
			change.getSelectionModel().select(0);
		}
	}
	
	private void handleAppointment(ActionEvent event)
	{
		if(appointmentID.getSelectionModel().getSelectedIndex()>0)
		{
			showNode();
		}
		else
		{
			hideNode();
			appointmentID.setVisible(true);
			lappointmentID.setVisible(true);
			appointmentID.getSelectionModel().select(0);
		}
	}
	
	private void handleUpdate(ActionEvent event)
	{
		int status=0;
		Alert alert = new Alert(AlertType.NONE);
		if(change.getSelectionModel().getSelectedIndex()==1)
			status = db.updateCarPickupDate(Integer.parseInt(appointmentID.getValue()),date.getValue().toString());
		else if(change.getSelectionModel().getSelectedIndex()==2)
			status = db.updateCarDropDate(Integer.parseInt(appointmentID.getValue()),date.getValue().toString());
		
		if(status>0)
		{
			alert.setAlertType(AlertType.INFORMATION);
			alert.setHeaderText("Date Updated Successfully");
			alert.setTitle("Success");
			hideNode();
			change.getSelectionModel().select(0);
		
		}
		else
		{
			alert.setAlertType(AlertType.ERROR);
			alert.setHeaderText("Date Not Updated");
			alert.setContentText("Please check the input values...");
			alert.setTitle("Failure");
		}
	
		alert.show();
	}
	
	private void handleClear(ActionEvent event)
	{
		hideNode();
		change.getSelectionModel().select(0);
	}
}
