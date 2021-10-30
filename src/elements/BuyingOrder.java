package elements;

public class BuyingOrder extends Order implements Comparable<BuyingOrder> {

	public BuyingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(BuyingOrder e) {
		// TODO Auto-generated method stub
		double myInt = e.price - this.price;
		if (myInt == 0)
			myInt =e.amount- this.amount;
		if (myInt == 0)
			myInt = this.traderID - e.traderID;
		if (myInt > 0)
			return 1;
		else if (myInt ==0)
			return 0;
		else
			return -1;
	}





}
