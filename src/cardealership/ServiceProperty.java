package cardealership;

public class ServiceProperty {
	
	int partPrice;
	double laborPrice;
	String result,partID,partReplaced,partDesc;
	
	public ServiceProperty(int partPrice, double laborPrice,String result, String partID, String partReplaced,String partDesc)
	{
		this.partPrice = partPrice;
		this.laborPrice = laborPrice;
		this.result = result; 
		this.partID = partID; 
		this.partReplaced = partReplaced;
		this.partDesc = partDesc;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public int getPartPrice() {
		return partPrice;
	}

	public void setPartPrice(int partPrice) {
		this.partPrice = partPrice;
	}

	public double getLaborPrice() {
		return laborPrice;
	}

	public void setLaborPrice(double laborPrice) {
		this.laborPrice = laborPrice;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getPartID() {
		return partID;
	}

	public void setPartID(String partID) {
		this.partID = partID;
	}

	public String getPartReplaced() {
		return partReplaced;
	}

	public void setPartReplaced(String partReplaced) {
		this.partReplaced = partReplaced;
	}

}
