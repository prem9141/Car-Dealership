package cardealership;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;


public class InsertAdditionalServiceController implements Initializable {

	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private ComboBox<String> appointmentID,partDesc,partReplace,result;
	
	@FXML
	private TextField customerID,customerName,vinNo,time,packageID,dropTime,employeeID,date,dropDate,partID;
	
	@FXML
	private Label lappointmentID,ldate,lcustomerID,lcustomerName,lvinNo,ltime,lpackageID,ldropDate;
	
	@FXML
	private Label ldropTime,lemployeeID,lpartID,lpartDesc,lpartReplace,lresult;
	
	@FXML
	private Button insert;
	
	private DAO db;
	
	private ObservableList<String> appointmentList, descList, replaceList, resultList;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		db = new DAO();
		
		hideNode();
		appointmentList = appointmentID.getItems();
		appointmentList.add("Select Appointment");
		appointmentList.addAll(db.getAppointmentID("In Progress"));
		appointmentID.getSelectionModel().select(0);	
		
		descList = partDesc.getItems();
		
		
		replaceList = partReplace.getItems();
		replaceList.add("Select an Option");
		replaceList.add("YES");
		replaceList.add("NO");
		partReplace.getSelectionModel().select(0);	
		
		resultList = result.getItems();
		resultList.add("Select Result");
		resultList.add("Ok");
		resultList.add("Not Ok");
		result.getSelectionModel().select(0);
		
		appointmentID.setOnAction(this::handleAppointmentID);
		partDesc.setOnAction(this::handleDesc);
		
		customerID.setEditable(false);
		customerName.setEditable(false);
		vinNo.setEditable(false);
		date.setEditable(false);
		time.setEditable(false);
		packageID.setEditable(false);
		dropDate.setEditable(false);
		dropTime.setEditable(false);
		employeeID.setEditable(false);
				
		
		insert.setOnAction(this::handleInsert);
		
		insert.disableProperty().bind(Bindings.createBooleanBinding(
				() -> validate(), partDesc.valueProperty(),
				partReplace.valueProperty(),result.valueProperty()
				));
	}

	private boolean validate()
	{
		if(partDesc.getSelectionModel().getSelectedIndex()==0)
			return true;
		if(partReplace.getSelectionModel().getSelectedIndex()==0)
			return true;
		if(result.getSelectionModel().getSelectedIndex()==0)
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
				if(!(nodeID.equalsIgnoreCase("appointmentID")||nodeID.equalsIgnoreCase("lappointmentID")))
					node.setVisible(false);
			}			
		}
	}
	
	private void showNode()
	{
		for(Node node : anchorPane.getChildren())
		{
			String nodeID = node.getId();
			
			if(nodeID!=null)
			{
				if(!(nodeID.equalsIgnoreCase("appointmentID")||nodeID.equalsIgnoreCase("lappointmentID")))
					node.setVisible(true);
			}			
		}
	}
	
	private void handleAppointmentID(ActionEvent event)
	{
		if(appointmentID.getSelectionModel().getSelectedIndex()>0)
		{
			ArrayList<String> details;
			details = db.getAppointmentDetails(Integer.parseInt(appointmentID.getValue()));
			customerID.setText(details.get(0));
			customerName.setText(details.get(1));
			vinNo.setText(details.get(2));
			date.setText(details.get(3));
			time.setText(details.get(4));
			packageID.setText(details.get(8)+" - Year Service");
			dropDate.setText(details.get(5));
			dropTime.setText(details.get(6));
			employeeID.setText(details.get(7));
			showNode();
			lpartID.setVisible(false);
			lpartReplace.setVisible(false);
			lresult.setVisible(false);
			partReplace.setVisible(false);
			partID.setVisible(false);
			result.setVisible(false);
			insert.setVisible(false);
			descList.clear();
			descList.add("Select an Option");
			descList.addAll(db.getPartDesc(Integer.parseInt(appointmentID.getValue())));
			partDesc.getSelectionModel().select(0);
					
		}
		else
			hideNode();
	}
	
	private void handleDesc(ActionEvent event)
	{
		if(partDesc.getSelectionModel().getSelectedIndex()>0)
		{
			lpartID.setVisible(true);
			lpartReplace.setVisible(true);
			lresult.setVisible(true);
			partReplace.setVisible(true);
			partID.setVisible(true);
			result.setVisible(true);
			insert.setVisible(true);
			partID.setText(db.getPartID(partDesc.getValue()));
			partReplace.getSelectionModel().select(0);
			result.getSelectionModel().select(0);
					
		}
		else
		{
			lpartID.setVisible(false);
			lpartReplace.setVisible(false);
			lresult.setVisible(false);
			partReplace.setVisible(false);
			partID.setVisible(false);
			result.setVisible(false);
			insert.setVisible(false);
		}
	}		
	
	
	private void handleInsert(ActionEvent event)
	{
		int status=0;
		Alert alert = new Alert(AlertType.NONE);
		status = db.insertAdditionalService(Integer.parseInt(appointmentID.getValue()),
				partID.getText(),partReplace.getValue(),result.getValue(),Integer.parseInt(employeeID.getText()));
		if(status>0)
		{
			alert.setAlertType(AlertType.INFORMATION);
			alert.setHeaderText("Additional Service Inserted Successfully");
			alert.setTitle("Success");
			hideNode();
			appointmentList.clear();
			appointmentList.add("Select Appointment");
			appointmentList.addAll(db.getAppointmentID("In Progress"));
			appointmentID.getSelectionModel().select(0);	
		
		}
		else
		{
			alert.setAlertType(AlertType.ERROR);
			alert.setHeaderText("Additional Service Not Inserted");
			alert.setContentText("Please check the input values...");
			alert.setTitle("Failure");
		}
	
		alert.show();
	}
}
