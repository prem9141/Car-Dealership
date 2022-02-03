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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


public class SaleStatisticsController implements Initializable {

	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private TableView<SalesProperty> tableView;
	
	@FXML
	private TableColumn<SalesProperty, String> maker;
	
	@FXML
	private TableColumn<SalesProperty, String> model;
	
	@FXML
	private TableColumn<SalesProperty, String> year;
	
	@FXML
	private TableColumn<SalesProperty, Integer> soldCount;
	
	@FXML
	private TableColumn<SalesProperty, Integer> profit;
	
	@FXML
	private Button calculate,clear;
	
	@FXML
	private DatePicker fromDate,toDate;
	
	private DAO db;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		db = new DAO();
		
		calculate.setOnAction(this::handleCalculate);
		clear.setOnAction(this::handleClear);
		
		fromDate.setEditable(false);
		toDate.setEditable(false);
		tableView.setVisible(false);
		
		calculate.disableProperty().bind(Bindings.createBooleanBinding(
				() -> validate(),fromDate.valueProperty(),toDate.valueProperty()
				));
	}

	private void hideNode()
	{
		tableView.setVisible(false);
	}
	
	private boolean validate()
	{
		if(fromDate.getValue()==null || toDate.getValue()==null)
			return true;
		return false;
	}
	
	private void handleCalculate(ActionEvent event)
	{
		tableView.setVisible(true);
		tableView.setItems(db.getSalesProperty(fromDate.getValue().toString(), toDate.getValue().toString()));
		maker.setCellValueFactory(new PropertyValueFactory<SalesProperty, String>("maker"));
		model.setCellValueFactory(new PropertyValueFactory<SalesProperty, String>("model"));
		year.setCellValueFactory(new PropertyValueFactory<SalesProperty, String>("year"));
		soldCount.setCellValueFactory(new PropertyValueFactory<SalesProperty, Integer>("soldCount"));
		profit.setCellValueFactory(new PropertyValueFactory<SalesProperty, Integer>("profit"));
	}
	
	private void handleClear(ActionEvent event)
	{
		hideNode();
		fromDate.setValue(null);
		toDate.setValue(null);
	}
}
