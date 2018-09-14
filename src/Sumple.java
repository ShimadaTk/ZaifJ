import java.util.Scanner;

import jp.nyatla.jzaif.api.*;
import jp.nyatla.jzaif.api.result.DepthResult;
import jp.nyatla.jzaif.api.result.TickerResult;
import jp.nyatla.jzaif.types.CurrencyPair;

public class Sumple {
	
	static double difference = 0;
	static double btc = 0;
	static double dif1 = 0;
	static double dif2 = 0;
	
	//BTC JPYへ変換の終値
	private static void BTCJPYLastPrice(){
		PublicApi lp = new PublicApi(CurrencyPair.BTCJPY);
		double r1 = lp.ticker().ask;
		System.out.println("BTC/JPY："+r1);
		return;
	}
	//日本円からモナコインへ終値
	private static void MONAJPYLastPrice(){
		PublicApi lp = new PublicApi(CurrencyPair.MONAJPY);
		double r1 = lp.ticker().bid;
		System.out.println("MONA/JPY："+r1);
		dif1 = r1;
		return;
	}	
	//日本円からネムコインへ変換の終値
	private static void XEMJPYLastPrice(){
		PublicApi lp = new PublicApi(CurrencyPair.XEMJPY);
		double r1 = lp.lastPrice();
		System.out.println("XEM/JPY："+r1);
		dif1 = r1;
		return;
	}
	//日本円からイーサリアムへ変換の終値
	private static void ETHJPYLastPrice(){
		PublicApi lp = new PublicApi(CurrencyPair.ETHJPY);
		double r1 = lp.lastPrice();
		System.out.println("ETH/JPY："+r1);
		dif1 = r1;
		return;
	}
	//日本円からビットコインキャッシュへ変換の終値
	private static void BCHJPYLastPrice(){
		PublicApi lp = new PublicApi(CurrencyPair.BCHJPY);
		double r1 = lp.lastPrice();
		System.out.println("BCH/JPY："+r1);
		dif1 = r1;
		return;
	}
	/*
	 * -----------------------------------------------------
	 */
	
	//ビットコインからモナコインへの終値
	private static void MONABTCLastPrice(){
		PublicApi lp = new PublicApi(CurrencyPair.MONABTC);
		double r1 = lp.ticker().bid;
		System.out.println("MONA/BTC："+r1);
		dif2 = r1;
		return;
	}
	//ビットコインからネムコインへ変換の終値
	private static void XEMBTCLastPrice(){
		PublicApi lp = new PublicApi(CurrencyPair.XEMBTC);
		Double r1 = lp.ticker().ask;
		System.out.println("XEM/BTC："+(1/r1));		
		dif2 = r1;
		return;
	}
	//ビットコインからネムコインへ変換の終値
		private static void XEMBTC2LastPrice(){
			PublicApi lp = new PublicApi(CurrencyPair.XEMBTC);
			Double r1 = lp.ticker().ask;
			r1 *= 1.001;
			System.out.println("XEM/BTC："+(1/r1));
			dif2 = r1;
			return;
		}
	//ビットコインからイーサリアムへ変換の終値
	private static void ETHBTCLastPrice(){
		PublicApi lp = new PublicApi(CurrencyPair.ETHBTC);
		double r1 = lp.lastPrice();
		System.out.println("ETH/BTC："+r1);
		dif2 = r1;
		return;
	}
	//ビットコインからBitcoinキャッシュへ変換の終値
	private static void BCHBTCLastPrice(){
		PublicApi lp = new PublicApi(CurrencyPair.BCHBTC);
		double r1 = lp.lastPrice();
		System.out.println("BCH/BTC："+r1);
		dif2 = r1;
		return;
	}
	
	//差を計算
	private static void Difference(){
		System.out.println("\n"+"仮想通貨をアルファベットで指定してください:");
		Scanner scan = new Scanner(System.in);
	    String str = scan.next();
	    if(str.equals("BTC")){
	    	BTCJPYLastPrice();
	    }
	    else if(str.equals("MONA")){
	    	BTCJPYLastPrice();
	    	MONAJPYLastPrice();
	    	MONABTCLastPrice();
	    	difference = (dif2*btc)-dif1;
	    	if(difference > 0){
	    		System.out.println("ビットコイン経由で日本円に変換したほうが"+difference+"円お得です");
	    	}
	    	else{
	    		System.out.println("ビットコイン経由で日本円に変換すると"+difference+"円の損失です");
	    	}
	    }
	    else if(str.equals("XEM")){
	    	BTCJPYLastPrice();
	    	XEMJPYLastPrice();
	    	XEMBTCLastPrice();
	    	difference = (dif2*btc)-dif1;
	    	if(difference > 0){
	    		System.out.println("ビットコイン経由で日本円に変換したほうが"+difference+"円お得です");
	    	}
	    	else{
	    		System.out.println("ビットコイン経由で日本円に変換すると"+difference+"円の損失です");
	    	}
	    }
	    else if(str.equals("ETH")){
	    	BTCJPYLastPrice();
	    	ETHJPYLastPrice();
	    	ETHBTCLastPrice();
	    	difference = (dif2*btc)-dif1;
	    	if(difference > 0){
	    		System.out.println("ビットコイン経由で日本円に変換したほうが"+difference+"円お得です");
	    	}
	    	else{
	    		System.out.println("ビットコイン経由で日本円に変換すると"+difference+"円の損失です");
	    	}
	    }
	    else if(str.equals("BCH")){
	    	BTCJPYLastPrice();
	    	BCHJPYLastPrice();
	    	BCHBTCLastPrice();
	    	difference = dif1-(dif2*btc);
	    	if(difference > 0){
	    		System.out.println("そのまま日本円に変換したほうが"+difference+"円お得です");
	    	}
	    	else{
	    		System.out.println("そのまま日本円に変換すると"+difference+"円の損失です");
	    	}
	    }
	    else{
	    	System.out.println("入力した仮想通貨は存在しません");
	    }
	}
	



	public static void main(String[] args) {
//日本円での終値
		System.out.println("円での終値");
		//	BTCJPYLastPrice();
		//	MONAJPYLastPrice();
		//	XEMJPYLastPrice();
		//	ETHJPYLastPrice();
		//	BCHJPYLastPrice();
	
//ビットコインでの終値
		System.out.println("\n"+"Bitcoinでの終値");
	//	MONABTCLastPrice();
		XEMBTCLastPrice();
		XEMBTC2LastPrice();
		//	ETHBTCLastPrice();
		//	BCHBTCLastPrice();
		
//差を計算
		Difference();		
		for(int i = 1;i<=10;i++){
			for(int j = 1;j<=10;j++){
				if(j <= 5){
					System.out.println(j);
				}
				else{
					System.out.println("error");
					break;
				}
			}
			System.out.println("ループ"+i+"回目");
		}
	}
}
