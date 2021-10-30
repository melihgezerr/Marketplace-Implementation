package elements;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Market {
	private int fee;
	private final double sellerFeeMultiplier;
	public Market(int fee) {
		this.fee = fee;
		this.sellerFeeMultiplier = 1- (double)fee/1000.0;
	}
	/**
	 * 
	 */
	int invalidQueries = 0;
	

	private PriorityQueue<SellingOrder> sellingOrders = new PriorityQueue<SellingOrder>();
	private PriorityQueue<BuyingOrder> buyingOrders  = new PriorityQueue<BuyingOrder>();
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	private ArrayList<Trader> traders = new ArrayList<Trader>();


	/**
	 *Gives the sell order with arranging trader's wallet. (Also checks for invalid query.)
	 * @param order
	 */
	public void giveSellOrder(SellingOrder order) {
		if (traders.get(order.traderID).sell(order.amount, order.price, this)){
			sellingOrders.add(order);
			traders.get(order.getTraderID()).getWallet().blockedCoins += order.amount;
			traders.get(order.getTraderID()).getWallet().coins -= order.amount;
			checkTransactions(traders);
		}
	}
/**
 * Gives the buy order with arranging trader's wallet. (Also checks for invalid query.)
 * @param order
 */
	public void giveBuyOrder(BuyingOrder order) {
		if (traders.get(order.traderID).buy(order.amount, order.price, this)) {
			buyingOrders.add(order);
			traders.get(order.getTraderID()).getWallet().blockedDollars += order.amount*order.price;
			traders.get(order.getTraderID()).getWallet().dollars -= order.amount*order.price;
			checkTransactions(traders);
		}
	}
/**
 * Arrange the current price to a specific price.
 * @param price
 */
	public void makeOpenMarketOperation(double price) {
		
		if (price > getBuyingOrders().peek().getPrice() || price < getSellingOrders().peek().getPrice()) {
			while (getSellingOrders().peek().getPrice() <= price) {
				SellingOrder myServerSell = getSellingOrders().poll();
				Trader mySeller = getTraders().get(myServerSell.getTraderID());
				mySeller.getWallet().setBlockedCoins(mySeller.getWallet().getBlockedCoins()-myServerSell.getAmount());
				mySeller.getWallet().setDollars(mySeller.getWallet().getDollars()+myServerSell.getPrice()*myServerSell.getAmount()*getSellerFeeMultiplier());
				Transaction.numOfTransactions +=1;
			}
		}
		
		else if (price < getBuyingOrders().peek().getPrice() || price > getSellingOrders().peek().getPrice()) {
			while (getBuyingOrders().peek().getPrice() >= price) {
				BuyingOrder myServerSell = getBuyingOrders().poll();
				Trader mySeller = getTraders().get(myServerSell.getTraderID());
				mySeller.getWallet().setBlockedDollars(mySeller.getWallet().getBlockedDollars()-myServerSell.getPrice()*myServerSell.getAmount());
				mySeller.getWallet().setCoins(mySeller.getWallet().getCoins()+myServerSell.getAmount());
				Transaction.numOfTransactions +=1;
			}
		}
	}
/**
 * Does the necessary actions like a normal exchange marketplace.
 * It arranges the trading with given priority.
 * @param traders
 */
	public void checkTransactions(ArrayList<Trader> traders) {
		while (buyingOrders.size() != 0 && sellingOrders.size()!= 0) {
			if (buyingOrders.peek().price >= sellingOrders.peek().price) {
				BuyingOrder myBuy = buyingOrders.poll();
				SellingOrder mySell = sellingOrders.poll();
				Trader myBuyer = traders.get(myBuy.getTraderID());
				Trader mySeller = traders.get(mySell.getTraderID());

				if (myBuy.amount == mySell.amount) {

					myBuyer.getWallet().blockedDollars -= myBuy.amount*myBuy.price;
					myBuyer.getWallet().dollars += myBuy.amount*myBuy.price - mySell.amount*mySell.price;
					myBuyer.getWallet().coins += mySell.amount;

					mySeller.getWallet().blockedCoins -= mySell.amount;
					mySeller.getWallet().dollars += mySell.amount*mySell.price*sellerFeeMultiplier;

					transactions.add(new Transaction(mySell, myBuy, mySell.amount, mySell.price));
				}

				else if (myBuy.amount > mySell.amount) {
					myBuyer.getWallet().blockedDollars -= myBuy.amount*myBuy.price;
					myBuyer.getWallet().dollars += myBuy.amount*myBuy.price - mySell.amount*mySell.price;
					myBuyer.getWallet().coins += mySell.amount;

					mySeller.getWallet().blockedCoins -= mySell.amount;
					mySeller.getWallet().dollars += mySell.amount*mySell.price*sellerFeeMultiplier;

					transactions.add(new Transaction(mySell, myBuy, mySell.amount, mySell.price));

					giveBuyOrder(new BuyingOrder(myBuy.traderID, myBuy.amount - mySell.amount, myBuy.price));
				}

				else if (mySell.amount > myBuy.amount) {
					myBuyer.getWallet().blockedDollars -= myBuy.amount*myBuy.price;
					myBuyer.getWallet().dollars += myBuy.amount*myBuy.price - myBuy.amount*mySell.price;
					myBuyer.getWallet().coins += myBuy.amount;

					mySeller.getWallet().blockedCoins -= mySell.amount;
					mySeller.getWallet().coins += mySell.amount - myBuy.amount;
					mySeller.getWallet().dollars += myBuy.amount*mySell.price*sellerFeeMultiplier;

					transactions.add(new Transaction(mySell, myBuy, myBuy.amount, mySell.price));

					giveSellOrder(new SellingOrder(mySell.traderID, mySell.amount- myBuy.amount, mySell.price));
				}
			}
			else
				break;
		}
	}






	/**
	 * @return the sellingOrders
	 */
	public PriorityQueue<SellingOrder> getSellingOrders() {
		return sellingOrders;
	}

	/**
	 * @return the buyingOrders
	 */
	public PriorityQueue<BuyingOrder> getBuyingOrders() {
		return buyingOrders;
	}

	/**
	 * @return the transactions
	 */
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * @return the traders
	 */
	public ArrayList<Trader> getTraders() {
		return traders;
	}

	/**
	 * @return the invalidQueries
	 */
	public int getInvalidQueries() {
		return invalidQueries;
	}

	/**
	 * @param invalidQueries the invalidQueries to set
	 */
	public void setInvalidQueries(int invalidQueries) {
		this.invalidQueries = invalidQueries;
	}

	/**
	 * @return the sellerFeeMultiplier
	 */
	public double getSellerFeeMultiplier() {
		return sellerFeeMultiplier;
	}

	/**
	 * @return the fee
	 */
	public int getFee() {
		return fee;
	}




}
