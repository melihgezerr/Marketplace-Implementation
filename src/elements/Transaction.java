package elements;

public class Transaction {
	private SellingOrder sellingOrder;

	private BuyingOrder buyingOrder;
	
	public static int numOfTransactions = 0;
	
	private double amount;
	private double price;
	/**
	 * @param sellingOrder
	 * @param buyingOrder
	 * @param amount
	 * @param price
	 */
	public Transaction(SellingOrder sellingOrder, BuyingOrder buyingOrder, double amount, double price) {
		this.sellingOrder = sellingOrder;
		this.buyingOrder = buyingOrder;
		this.amount = amount;
		this.price = price;
		numOfTransactions+=1;
	}
	@Override
	public String toString() {
		String mystr = "sell: " +"(ID:"+sellingOrder.traderID+", price: "+sellingOrder.price+", amount: "+ sellingOrder.amount+")  "+
	                    "buy: "+"("+buyingOrder.traderID +", "+buyingOrder.price+", "+ buyingOrder.amount+") "+
	                    "sold: "+ amount +" PQs for each: "+price+" dollars.";
		return mystr;
	}
	
	
	

	


}
