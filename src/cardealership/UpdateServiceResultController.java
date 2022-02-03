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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


public class UpdateServiceResultController implements Initializable {

	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private ComboBox<String> appointmentID;
	
	@FXML
	private TextField customerID,customerName,vinNo,time,packageID,dropTime,employeeID,date,dropDate;
	
	@FXML
	private Label lappointmentID,ldate,lcustomerID,lcustomerName,lvinNo,ltime,lpackageID,ldropDate;
	
	@FXML
	private Label ldropTime,lemployeeID;
	
	@FXML
	private TableView<ServiceProperty> tableView;
	
	@FXML
	private TableColumn<ServiceProperty, String> partID;
	
	@FXML
	private TableColumn<ServiceProperty, String> partDesc;
	
	@FXML
	private TableColumn<ServiceProperty, String> partReplace;
	
	@FXML
	private TableColumn<ServiceProperty, String> result;
	
	@FXML
	private Button completed;
	
	private DAO db;
	
	private ObservableList<String> appointmentList;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		db = new DAO();
		
		hideNode();
		appointmentList = appointmentID.getItems();
		appointmentList.add("Select Appointment");
		appointmentList.addAll(db.getAppointmentID("In Progress"));
		appointmentID.getSelectionModel().select(0);	
		
		appointmentID.setOnAction(this::handleAppointmentID);
		
		customerID.setEditable(false);
		customerName.setEditable(false);
		vinNo.setEditable(false);
		date.setEditable(false);
		time.setEditable(false);
		packageID.setEditable(false);
		dropDate.setEditable(false);
		dropTime.setEditable(false);
		employeeID.setEditable(false);
		tableView.setEditable(true);
		
		ObservableList<String> value = FXCollections.observableArrayList();
		value.add("Ok");
		value.add("Not Ok");
		
		result.setCellFactory(ComboBoxTableCell.forTableColumn(value));
		result.setOnEditCommit(this::onEditCommit);
		
		completed.setOnAction(this::handleCompleted);
		
		completed.disableProperty().bind(Bindings.createBooleanBinding(
				() -> validate(), tableView.editingCellProperty()			
				));
	}

	private boolean validate()
	{
		System.out.println("Visible:" + completed.isVisible());
		if(completed.isVisible())
			return db.isResultEmpty(Integer.parseInt(appointmentID.getValue()));
		return true;
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
			tableView.setItems(db.getServiceProperty(Integer.parseInt(appointmentID.getValue())));
			partID.setCellValueFactory(new PropertyValueFactory<ServiceProperty, String>("partID"));
			partReplace.setCellValueFactory(new PropertyValueFactory<ServiceProperty, String>("partReplaced"));
			partDesc.setCellValueFactory(new PropertyValueFactory<ServiceProperty, String>("partDesc"));
			result.setCellValueFactory(new PropertyValueFactory<ServiceProperty, String>("result"));			
		}
		else
			hideNode();
	}
	
	private void onEditCommit(TableColumn.CellEditEvent<ServiceProperty, String> e)
	{
		ServiceProperty sp =tableView.getSelectionModel().getSelectedItem();
		sp.result = e.getNewValue();
		db.updateServiceResult(Integer.parseInt(appointmentID.getValue()), sp.partID, e.getNewValue());
	}
	
	private void handleCompleted(ActionEvent event)
	{
		int status=0;
		Alert alert = new Alert(AlertType.NONE);
		status = db.updateCarServiceStatus(Integer.parseInt(appointmentID.getValue()));
		if(status>0)
		{
			alert.setAlertType(AlertType.INFORMATION);
			alert.setHeaderText("Car Diagnosis Results Updated Successfully");
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
			alert.setHeaderText("Diagnosis Results Not Updated");
			alert.setContentText("Please check the input values...");
			alert.setTitle("Failure");
		}
	
		alert.show();
	}
}
