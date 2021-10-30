package elements;

public class Wallet {
	protected double dollars;

	protected double coins;

	protected double blockedDollars = 0;

	protected double blockedCoins = 0;

	public Wallet(double dollars, double coins) {
		this.dollars = dollars;
		this.coins = coins;
	}

	/**
	 * @return the dollars
	 */
	public double getDollars() {
		return dollars;
	}

	/**
	 * @return the coins
	 */
	public double getCoins() {
		return coins;
	}

	/**
	 * @return the blockedDollars
	 */
	public double getBlockedDollars() {
		return blockedDollars;
	}

	/**
	 * @return the blockedCoins
	 */
	public double getBlockedCoins() {
		return blockedCoins;
	}

	/**
	 * @param dollars the dollars to set
	 */
	public void setDollars(double dollars) {
		this.dollars = dollars;
	}

	/**
	 * @param coins the coins to set
	 */
	public void setCoins(double coins) {
		this.coins = coins;
	}

	/**
	 * @param blockedDollars the blockedDollars to set
	 */
	public void setBlockedDollars(double blockedDollars) {
		this.blockedDollars = blockedDollars;
	}

	/**
	 * @param blockedCoins the blockedCoins to set
	 */
	public void setBlockedCoins(double blockedCoins) {
		this.blockedCoins = blockedCoins;
	}
	
	

	


}
