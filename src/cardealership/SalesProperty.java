package cardealership;

public class SalesProperty {
	
	String maker,model,year;
	int soldCount, profit;
	
	public SalesProperty(String maker, String model, String year, int soldCount, int profit)
	{
		this.maker = maker;
		this.model = model;
		this.year = year;
		this.soldCount = soldCount;
		this.profit = profit;
	}
	
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public int getSoldCount() {
		return soldCount;
	}
	public void setSoldCount(int soldCount) {
		this.soldCount = soldCount;
	}
	public int getProfit() {
		return profit;
	}
	public void setProfit(int profit) {
		this.profit = profit;
	}
	
	

}
