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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class SalesInvoiceController implements Initializable {

	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private ComboBox<String> invoiceID;
	
	@FXML
	private TextField customerID,customerName,phoneNo,vinNo,model,maker,year;
	
	@FXML
	private TextField date,employeeID,price,discount;
	
	@FXML
	private Label linvoiceID,lcustomerID,lcustomerName,lphoneNo,lvinNo,lmodel,lmaker,lyear;
	
	@FXML
	private Label ldate,lemployeeID,lprice,ldiscount;
	
	
	private DAO db;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		db = new DAO();
		
		hideNode();
		ObservableList<String> invoiceList = invoiceID.getItems();
		invoiceList.add("Select Invoice");
		invoiceList.addAll(db.getInvoice());
		invoiceID.getSelectionModel().select(0);	
		
		invoiceID.setOnAction(this::handleInvoiceID);
		
		customerID.setEditable(false);
		customerName.setEditable(false);
		phoneNo.setEditable(false);
		vinNo.setEditable(false);
		model.setEditable(false);
		maker.setEditable(false);
		year.setEditable(false);
		date.setEditable(false);
		employeeID.setEditable(false);
		price.setEditable(false);
		discount.setEditable(false);
	}

	private void hideNode()
	{
		for(Node node : anchorPane.getChildren())
		{
			String nodeID = node.getId();
			
			if(nodeID!=null)
			{
				if(!(nodeID.equalsIgnoreCase("invoiceID")||nodeID.equalsIgnoreCase("linvoiceID")))
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
				if(!(nodeID.equalsIgnoreCase("invoiceID")||nodeID.equalsIgnoreCase("linvoiceID")))
					node.setVisible(true);
			}			
		}
	}
	
	private void handleInvoiceID(ActionEvent event)
	{
		if(invoiceID.getSelectionModel().getSelectedIndex()>0)
		{
			showNode();
			ArrayList<String> details;
			details = db.getInvoiceDetails(Integer.parseInt(invoiceID.getValue()));
			customerID.setText(details.get(0));
			customerName.setText(details.get(6));
			phoneNo.setText(details.get(7));
			vinNo.setText(details.get(1));
			model.setText(details.get(8));			
			maker.setText(details.get(9));
			year.setText(details.get(10));
			date.setText(details.get(2));
			employeeID.setText(details.get(3));
			price.setText(details.get(4));
			discount.setText(details.get(5));
			
		}
		else
		{
			hideNode();
			invoiceID.getSelectionModel().select(0);
		}
	}
}
