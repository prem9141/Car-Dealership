package cardealership;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;



public class HomeScreenController implements Initializable {

	@FXML
	private Button createCustomer,modifyAppointment,sellCar,bookAppointment,serviceInvoice,saleStatistics,saleInvoice;
	
	@FXML
	private Button updateResult,addService;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		createCustomer.setOnAction(this::handleCreateCustomer);
		sellCar.setOnAction(this::handleSellCar);
		bookAppointment.setOnAction(this::handleBookAppointment);
		serviceInvoice.setOnAction(this::handleServiceInvoice);
		saleStatistics.setOnAction(this::handleSaleStatistics);
		saleInvoice.setOnAction(this::handleSaleInvoice);
		modifyAppointment.setOnAction(this::handleModifyAppointment);
		updateResult.setOnAction(this::handleupdateResult);
		addService.setOnAction(this::handleaddService);
		
		
	}
	
		
	private void handleCreateCustomer(ActionEvent e)
	{
		CustomerCreation cc = new CustomerCreation();
		cc.start(new Stage());		
	}	
	
	private void handleSellCar(ActionEvent e)
	{
		CarSale cs = new CarSale();
		cs.start(new Stage());		
	}	
	
	private void handleBookAppointment(ActionEvent e)
	{
		BookAppointment ba = new BookAppointment();
		ba.start(new Stage());		
	}	
	
	private void handleModifyAppointment(ActionEvent e)
	{
		ModifyAppointment ma = new ModifyAppointment();
		ma.start(new Stage());		
	}
	
	private void handleServiceInvoice(ActionEvent e)
	{
		ServiceInvoice si = new ServiceInvoice();
		si.start(new Stage());		
	}	
	
	private void handleSaleInvoice(ActionEvent e)
	{
		SalesInvoice si = new SalesInvoice();
		si.start(new Stage());	
	}
	
	private void handleSaleStatistics(ActionEvent e)
	{
		SaleStatistics ss = new SaleStatistics();
		ss.start(new Stage());		
	}	
	
	private void handleupdateResult(ActionEvent e)
	{
		UpdateServiceResult usr = new UpdateServiceResult();
		usr.start(new Stage());		
	}
	
	private void handleaddService(ActionEvent e)
	{
		InsertAdditionalService as = new InsertAdditionalService();
		as.start(new Stage());		
	}	
}
