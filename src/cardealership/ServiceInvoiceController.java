package cardealership;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


public class ServiceInvoiceController implements Initializable {

	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private ComboBox<String> appointmentID;
	
	@FXML
	private TextField customerID,customerName,vinNo,time,packageID,dropTime,employeeID,date,dropDate;
	
	@FXML
	private Label lappointmentID,ldate,lcustomerID,lcustomerName,lvinNo,ltime,lpackageID,ldropDate;
	
	@FXML
	private Label ldropTime,lemployeeID,ltotalCharge,totalCharge;
	
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
	private TableColumn<ServiceProperty, Integer> partPrice;
	
	@FXML
	private TableColumn<ServiceProperty, Double> laborCharge;
	
	private DAO db;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		db = new DAO();
		
		hideNode();
		ObservableList<String> appointmentList = appointmentID.getItems();
		appointmentList.add("Select Appointment");
		appointmentList.addAll(db.getAppointmentID("*"));
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
			partDesc.setCellValueFactory(new PropertyValueFactory<ServiceProperty, String>("partDesc"));
			partReplace.setCellValueFactory(new PropertyValueFactory<ServiceProperty, String>("partReplaced"));
			result.setCellValueFactory(new PropertyValueFactory<ServiceProperty, String>("result"));
			partPrice.setCellValueFactory(new PropertyValueFactory<ServiceProperty, Integer>("partPrice"));
			laborCharge.setCellValueFactory(new PropertyValueFactory<ServiceProperty, Double>("laborPrice"));
			totalCharge.setText(details.get(9));
		}
		else
		{
			hideNode();
			appointmentID.getSelectionModel().select(0);
		}
	}
}
