package executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

import elements.BuyingOrder;
import elements.Market;
import elements.SellingOrder;
import elements.Trader;
import elements.Transaction;

public class Main {

	public static void main(String args[])  throws FileNotFoundException{
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		int seed = in.nextInt();
		Random myRandom = new Random(seed);
		
		int marketFee = in.nextInt();
		Market market = new Market(marketFee);
		
		int numOfUsers = in.nextInt();
		int numOfQueries = in.nextInt();
		
		for (int i=0 ; i<numOfUsers ; i++) {  // TRADER LÝSTESÝNÝ OLUÞTURDU.
			double dollars = in.nextDouble();
			double coins = in.nextDouble();
			market.getTraders().add(new Trader(dollars, coins));
		}
		
		
		
		for (int a=0 ; a < numOfQueries ; a++) {  // KOMUTLARI ALACAK.
			int operation = in.nextInt();
			
			if (operation == 10) { //give buying order of specific price
				int traderID = in.nextInt();
				double buyPrice = in.nextDouble();
				double PQamount = in.nextDouble();
				
				market.giveBuyOrder(new BuyingOrder(traderID, PQamount, buyPrice));
			}
			
			if (operation == 11) { //give buying order of market price
				int traderID = in.nextInt();
				double PQamount = in.nextDouble();
				
				if (market.getBuyingOrders().size() == 0)
					market.setInvalidQueries(market.getInvalidQueries()+1);
				else
					market.giveBuyOrder(new BuyingOrder(traderID, PQamount, market.getSellingOrders().peek().getPrice()));
			}
			
			
			if (operation == 20) { //give selling order of specific price
				int traderID = in.nextInt();
				double buyPrice = in.nextDouble();
				double PQamount = in.nextDouble();
				
				market.giveSellOrder(new SellingOrder(traderID, PQamount, buyPrice));
			}
			
			if (operation == 21) { //give selling order of market price
				int traderID = in.nextInt();
				double PQamount = in.nextDouble();
				if (market.getSellingOrders().size() == 0)
					market.setInvalidQueries(market.getInvalidQueries()+1);
				else
					market.giveSellOrder(new SellingOrder(traderID, PQamount, market.getBuyingOrders().peek().getPrice()));
			}
			
			if (operation == 3) {//deposit a certain amount of dollars to wallet
				int traderID = in.nextInt();
				double dollarAmount = in.nextDouble();
				
				market.getTraders().get(traderID).getWallet().setDollars(market.getTraders().get(traderID).getWallet().getDollars()+dollarAmount);
			}
			
			if (operation == 4) {//withdraw a certain amount of dollars from wallet
				int traderID = in.nextInt();
				double dollarAmount = in.nextDouble();
				if (dollarAmount<=market.getTraders().get(traderID).getWallet().getDollars())
					market.getTraders().get(traderID).getWallet().setDollars(market.getTraders().get(traderID).getWallet().getDollars()-dollarAmount);
				else
					market.setInvalidQueries(market.getInvalidQueries()+1);
			}
			
			if (operation == 5) {//print wallet status
				int traderID = in.nextInt();
				double allDollars = market.getTraders().get(traderID).getWallet().getBlockedDollars() + market.getTraders().get(traderID).getWallet().getDollars();
				double allPQoins = market.getTraders().get(traderID).getWallet().getBlockedCoins() + market.getTraders().get(traderID).getWallet().getCoins();
				out.print("Trader "+ traderID+": "+String.format("%.5f", allDollars)+"$ "+String.format("%.5f", allPQoins)+"PQ"+"\n");
				
			}
			
			if (operation == 777) {//give rewards to all traders
				for (int trad=0; trad< market.getTraders().size(); trad++) {
					double PQamount = myRandom.nextDouble()*10;
					market.getTraders().get(trad).getWallet().setCoins(market.getTraders().get(trad).getWallet().getCoins()+PQamount);
				}
			}
			
			if (operation == 666) {//make open market operation
				double priceWanted = in.nextDouble();
				market.makeOpenMarketOperation(priceWanted);
			}
			
			if (operation == 500) {//print the current market size
				double dollarSize = 0;
				double PQsize = 0;
				for (BuyingOrder buys : market.getBuyingOrders()) {
					dollarSize += buys.getAmount()*buys.getPrice();
				}
				for (SellingOrder sells : market.getSellingOrders()) {
					PQsize += sells.getAmount();
				}
				out.print("Current market size: "+String.format("%.5f", dollarSize)+" "+String.format("%.5f", PQsize)+"\n");
			}
		
			if (operation == 501) {//print number of successful transactions
				out.print("Number of successful transactions: "+Transaction.numOfTransactions+"\n");
			}
			
			if (operation == 502) {//print the number of invalid queries
				out.print("Number of invalid queries: "+market.getInvalidQueries()+"\n");
			}
			
			if (operation == 505) {//print the current prices
				double myBuy;
				double mySell;
				double average = 0.0;
				if (market.getBuyingOrders().size()==0)
					myBuy = 0.0;
				else 
					myBuy = market.getBuyingOrders().peek().getPrice();
				if (market.getSellingOrders().size()==0)
					mySell = 0.0;
				else 
					mySell = market.getSellingOrders().peek().getPrice();
				if (myBuy != 0.0 && mySell != 0.0)
					average = (myBuy+mySell)/2;
				else if (myBuy == 0.0 && mySell != 0.0)
					average = mySell;
				else if (myBuy != 0.0 && mySell == 0.0)
					average = myBuy;
					
				out.print("Current prices: "+ String.format("%.5f", myBuy)+" "+String.format("%.5f", mySell)+" "+String.format("%.5f", average)+"\n");
			}
			
			if (operation == 555) {//print all traders’ wallet status
				for (int trad=1; trad<market.getTraders().size(); trad++) {
					double allDollars = market.getTraders().get(trad).getWallet().getBlockedDollars() + market.getTraders().get(trad).getWallet().getDollars();
					double allPQoins = market.getTraders().get(trad).getWallet().getBlockedCoins() + market.getTraders().get(trad).getWallet().getCoins();
					out.print("Trader "+ trad+": "+String.format("%.5f", allDollars)+"$ "+String.format("%.5f", allPQoins)+"PQ\n");
				}
			}
		}
		
		for (Transaction tran : market.getTransactions()) {
			System.out.println(tran.toString());
		}
		
		
		
	}
}
