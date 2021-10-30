package elements;

public class SellingOrder extends Order implements Comparable<SellingOrder>{

	public SellingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(SellingOrder e) {
		// TODO Auto-generated method stub
		double myInt = this.price - e.price;
		if (myInt == 0)
			myInt = e.amount - this.amount;
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
