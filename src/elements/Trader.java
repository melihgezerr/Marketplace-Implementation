package elements;

public class Trader {
	final private int id;
	public static int numberOfUsers = 0;
	final private double dollars;
	final private double coins;

	private Wallet wallet;
	
	public Trader(double dollars, double coins) {
		this.dollars = dollars;
		this.coins = coins;
		this.wallet = new Wallet(dollars, coins);
		this.id = numberOfUsers;
		Trader.numberOfUsers += 1;
	}
	/**
	 * Checks whether a seller has enough coins to sell.
	 * @param amount
	 * @param price
	 * @param market
	 * @return boolean
	 */
	public boolean sell(double amount, double price, Market market) {
		if (this.wallet.coins > amount && amount > 0)
			return true;
		else {
			market.invalidQueries += 1;
			return false;
		}
	}
	/**
	 * Checks whether a buyer has enough money to buy.
	 * @param amount
	 * @param price
	 * @param market
	 * @return boolean
	 */
	public boolean buy(double amount, double price, Market market) {
		if (this.wallet.dollars > amount*price && amount > 0)
			return true;
		else {
			market.invalidQueries += 1;
			return false;
		}
	}

	/**
	 * @return the wallet
	 */
	public Wallet getWallet() {
		return wallet;
	}

	


}
