package Zaif;

import jp.nyatla.jzaif.api.PublicApi;
import jp.nyatla.jzaif.types.CurrencyPair;


/**
 * Zaifで、BTCからの重みを格納し、スタートをBTCとしての変換結果を算出する
 */
public class ZaifBTC implements ZaifEx{

	private ZaifData data;
	
	public ZaifBTC(ZaifData data){
		this.data = data;
	}

	@Override
	/*
	 * 各ノード間の重みを計算、格納
	 */
	public void exchange(){
		try{
		PublicApi lp = new PublicApi(CurrencyPair.BTCJPY);
		Double r1 = lp.ticker().bid;
//		System.out.println("BTC/JPY："+r1);
		data.setBTCPrice("JPY", r1);
		
		lp = new PublicApi(CurrencyPair.MONABTC);
		r1 = lp.ticker().ask;
//		System.out.println("BTC/MONA："+(1/r1));
		r1 *= 1.001;
		data.setBTCPrice("MONA", (1/r1));

		lp = new PublicApi(CurrencyPair.XEMBTC);
		r1 = lp.ticker().ask;
		r1 *= 1.001;
//		System.out.println("BTC/XEM："+(1/r1));
		data.setBTCPrice("XEM", (1/r1));
		
		//エラー発生時は0を格納
		}catch(Exception e){
			e.printStackTrace();
			data.setBTCPrice("JPY", 0.0);
			data.setBTCPrice("MONA", 0.0);
			data.setBTCPrice("XEM", 0.0);
		}
		
		return;
	}
	
	
	@Override
	//第2ノードを決定する
	public void transition(){
		String nextNode = null;//決定経路
		Double nextWeight = 1.0;
		Double totalWeight = 1.0;
		String nextNode2;//仮経路
		Double totalWeight2 = 1.0;
		Double nextWeight2 = 1.0;
		
//////////////////////////////////////////////////////////////////////////	
		//第一ノードがJPY
		for (int i = 1; i <= data.getNodenumbers()-2;i++){;
			totalWeight2 = 1.0;
			nextWeight2 = 1.0;
			nextNode2 = "JPY";
			nextWeight2*=data.getBTCPrice().get("JPY");
			totalWeight2*=data.getBTCPrice().get("JPY");
			
			if(i == 1){//第2ノードがMONA
				totalWeight2*=data.getJPYPrice().get("MONA");
				totalWeight2*=data.getMONAPrice().get("BTC");
				System.out.println(totalWeight2 + "BTC;JPY;MONA;BTC");
/*				for (int j = 0; j < expectation.size();j++){
					System.out.println(expectation.get(j));
				}
*/				
//				System.out.println(temporaryWeight);
			}
			//第2ノードがXEM
			else if(i == 2){
				totalWeight2*=data.getJPYPrice().get("XEM");
				totalWeight2*=data.getXEMPrice().get("BTC");
				System.out.println(totalWeight2 + "BTC;JPY;XEM;BTC");
				//				System.out.println(temporaryWeight);
			}
			//最大経路上書き
			if(i == 1){
				nextNode = nextNode2;
				totalWeight = totalWeight2;
				nextWeight = nextWeight2;
			}
			//最大経路上書き
			if(i >= 2 && totalWeight < totalWeight2){
				nextNode = nextNode2;
				totalWeight = totalWeight2;
				nextWeight = nextWeight2;
			}
		}
//////////////////////////////////////////////////////////////////////////		
		//第一ノードがMONA
		for (int i = 1; i <= data.getNodenumbers()-2;i++){
			totalWeight2 = 1.0;
			nextWeight2 = 1.0;
			nextNode2 = "MONA";
			nextWeight2*=data.getBTCPrice().get("MONA");
			totalWeight2*=data.getBTCPrice().get("MONA");
			
			//第2ノードはJPY
			totalWeight2*=data.getMONAPrice().get("JPY");
			
			//終了
			if(i == 1){
				totalWeight2*=data.getJPYPrice().get("BTC");
				System.out.println(totalWeight2 + "BTC;MONA;JPY;BTC");
//				System.out.println(temporaryWeight);
			}
			//第3ノードがXEM
			else if(i == 2){
				totalWeight2*=data.getJPYPrice().get("XEM");
				totalWeight2*=data.getXEMPrice().get("BTC");
				System.out.println(totalWeight2 + "BTC;MONA;JPY;XEM;BTC");
//				System.out.println(temporaryWeight);
			}
			//最大経路上書き
			if(totalWeight < totalWeight2){
				nextNode = nextNode2;
				totalWeight = totalWeight2;
				nextWeight = nextWeight2;
			}
		}
//////////////////////////////////////////////////////////////////////////
		//第一ノードがXEM
		for (int i = 1; i <= data.getNodenumbers()-2;i++){
			totalWeight2 = 1.0;
			nextWeight2 = 1.0;
			nextNode2 = "XEM";
			nextWeight2*=data.getBTCPrice().get("XEM");
			totalWeight2*=data.getBTCPrice().get("XEM");
			
			//第2ノードはJPY
			totalWeight2*=data.getXEMPrice().get("JPY");
			
			//終了
			if(i == 1){
				totalWeight2*=data.getJPYPrice().get("BTC");
				System.out.println(totalWeight2 + "BTC;XEM;JPY;BTC");
			}
			//第3ノードがMONA
			else if(i == 2){
				totalWeight2*=data.getJPYPrice().get("MONA");
				totalWeight2*=data.getMONAPrice().get("BTC");
				System.out.println(totalWeight2 + "BTC;XEM;JPY;MONA;BTC");
			}
			//最大経路上書き
			if(totalWeight < totalWeight2){
				nextNode = nextNode2;
				totalWeight = totalWeight2;
				nextWeight = nextWeight2;
			}
		}
		data.setGainWeight(nextWeight);
		data.setRoute(nextNode);
	}
	

	@Override
	//第3ノードを決定する
	public void transition2(){
		String nextNode = null;
		double nextWeight = 1.0;
		double totalWeight = 1.0;
		
		//BTC→JPY→???
		if(data.getRoute().get(1).equals("JPY")){
			for (int i = 1; i <= data.getNodenumbers()-2;i++){
				String nextNode2 = null;
				double totalWeight2 = 1.0;
				double nextWeight2 = 1.0;
				if(i == 1){////BTC→JPY→MONA
					nextNode2 = "MONA";
					nextWeight2*=data.getJPYPrice().get("MONA");
					totalWeight2*=data.getJPYPrice().get("MONA");
					totalWeight2*=data.getMONAPrice().get("BTC");
				}
				////BTC→JPY→XEM
				else if(i == 2){
					nextNode2 = "XEM";
					nextWeight2*=data.getJPYPrice().get("XEM");
					totalWeight2*=data.getJPYPrice().get("XEM");
					totalWeight2*=data.getXEMPrice().get("BTC");
				}
				//最大経路上書き
				if(i == 1){
					nextNode = nextNode2;
					totalWeight = totalWeight2;
					nextWeight = nextWeight2;
				}
				else if(i >= 2 && totalWeight2 > totalWeight){
					nextNode = nextNode2;
					totalWeight = totalWeight2;
					nextWeight = nextWeight2;
				}
			}
		}
		//BTC→MONA→???
		else if(data.getRoute().get(1).equals("MONA")){
			nextNode = "JPY";
			nextWeight*=data.getMONAPrice().get("JPY");
		}
		//BTC→XEM→???
		else if(data.getRoute().get(1).equals("XEM")){
			nextNode = "JPY";
			nextWeight*=data.getXEMPrice().get("JPY");
		}
		data.setGainWeight(nextWeight);
		data.setRoute(nextNode);
	}
	
	@Override
	//第4ノードを決定する
	public void transition3(){
		String nextNode = null;
		double nextWeight = 1.0;
		double totalWeight = 1.0;
		
		//BTC→JPY→MONA→BTC
		if(data.getRoute().get(2).equals("MONA")){
			nextNode = "BTC";
			nextWeight*=data.getMONAPrice().get("BTC");
		}
		//BTC→JPY→XEM→BTC
		else if(data.getRoute().get(2).equals("XEM")){
			nextNode = "BTC";
			nextWeight*=data.getXEMPrice().get("BTC");
		}
		//BTC→MONA→JPY→???
		else if(data.getRoute().get(2).equals("JPY") && data.getRoute().get(1).equals("MONA")){
			for (int i = 1; i <= data.getNodenumbers()-2;i++){
				String nextNode2 = null;
				double totalWeight2 = 1.0;
				double nextWeight2 = 1.0;
				if(i == 1){////BTC→MONA→JPY→XEM
					nextNode2 = "XEM";
					nextWeight2*=data.getJPYPrice().get("XEM");
					totalWeight2*=data.getJPYPrice().get("XEM");
					totalWeight2*=data.getXEMPrice().get("BTC");
	//				System.out.println(weight2);
				}
				////BTC→MONA→JPY→BTC
				else if(i == 2){
					nextNode2 = "BTC";
					nextWeight2*=data.getJPYPrice().get("BTC");
					totalWeight2*=data.getJPYPrice().get("BTC");
	//				System.out.println(weight2);
				}
				//最大経路上書き
				if(i == 1){
					nextNode = nextNode2;
					totalWeight = totalWeight2;
					nextWeight = nextWeight2;
				}
				else if(i >= 2 && totalWeight2 > totalWeight){
					nextNode = nextNode2;
					totalWeight = totalWeight2;
					nextWeight = nextWeight2;
				}
			}
		}
		//BTC→XEM→JPY→???
		else if(data.getRoute().get(2).equals("JPY") && data.getRoute().get(1).equals("XEM")){
			for (int i = 1; i <= data.getNodenumbers()-2;i++){
				String nextNode2 = null;
				double totalWeight2 = 1.0;
				double nextWeight2 = 1.0;
				if(i == 1){////BTC→XEM→JPY→MONA
					nextNode2 = "MONA";
					nextWeight2*=data.getJPYPrice().get("MONA");
					totalWeight2*=data.getJPYPrice().get("MONA");
					totalWeight2*=data.getMONAPrice().get("BTC");
		//			System.out.println(weight2);
				}
				////BTC→XEM→JPY→BTC
				else if(i == 2){
					nextNode2 = "BTC";
					nextWeight2*=data.getJPYPrice().get("BTC");
					totalWeight2*=data.getJPYPrice().get("BTC");
		//			System.out.println(weight2);
				}
				//最大経路上書き
				if(i == 1){
					nextNode = nextNode2;
					totalWeight = totalWeight2;
					nextWeight = nextWeight2;
				}
				else if(i >= 2 && totalWeight2 > totalWeight){
					nextNode = nextNode2;
					totalWeight = totalWeight2;
					nextWeight = nextWeight2;
				}
			}
		}
		data.setGainWeight(nextWeight);
		data.setRoute(nextNode);
	}
	
	@Override
	//帰ってくる
	public void transition4(){
		String nextNode = null;
		double weight = 1.0;
		
		//BTC→XEM→JPY→MONA→BTC
		if(data.getRoute().get(3).equals("MONA")){
			nextNode = "BTC";
			weight*=data.getMONAPrice().get("BTC");
		}
		//BTC→MONA→JPY→XEM→BTC
		else if(data.getRoute().get(3).equals("XEM")){
			nextNode = "BTC";
			weight*=data.getXEMPrice().get("BTC");
		}
		data.setGainWeight(weight);
		data.setRoute(nextNode);
	}
	
}
	
	


