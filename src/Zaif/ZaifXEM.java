package Zaif;


import jp.nyatla.jzaif.api.PublicApi;
import jp.nyatla.jzaif.types.CurrencyPair;


/**
 * Zaifで、XEMからの重みを格納し、スタートをXEMとしての変換結果を算出する
 */
public class ZaifXEM implements ZaifEx{
	
	private ZaifData data;
	
	public ZaifXEM(ZaifData data){
		this.data = data;
	}
	
	@Override
	/*
	 * 各ノード間の重みを計算、格納
	 */
	public void exchange(){
		try{
		PublicApi lp = new PublicApi(CurrencyPair.XEMJPY);
		double r1 = lp.ticker().bid;
		data.setXEMPrice("JPY", r1);
		
		lp = new PublicApi(CurrencyPair.XEMBTC);
		r1 = lp.ticker().bid;
		data.setXEMPrice("BTC", r1);}
		
		//エラー発生時は0を格納
		catch(Exception e){
			e.printStackTrace();
			data.setXEMPrice("BTC", 0.0);
			data.setXEMPrice("JPY", 0.0);
		}
		return;
	}

	@Override
	//第2ノードを決定する
	public void transition(){
		String nextNode = null;//決定経路
		String nextNode2;//仮経路
		Double totalWeight = 1.0;
		Double totalWeight2 = 1.0;
		Double nextWeight = 1.0;
		Double nextWeight2 = 1.0;
		
//////////////////////////////////////////////////////////////////////////	
		//第一ノードがBTC
		for (int i = 1; i <= data.getNodenumbers()-2;i++){
			totalWeight2 = 1.0;
			nextWeight2 = 1.0;
			nextNode2 = "BTC";
			nextWeight2*=data.getXEMPrice().get("BTC");
			totalWeight2*=data.getXEMPrice().get("BTC");
			
			if(i == 1){//第2ノードがJPY
				totalWeight2*=data.getBTCPrice().get("JPY");
				totalWeight2*=data.getJPYPrice().get("XEM");
				System.out.println(totalWeight2 + "XEM;BTC;JPY;XEM");
			}
			//第2ノードがMONA
			else if(i == 2){
				totalWeight2*=data.getBTCPrice().get("MONA");
				totalWeight2*=data.getMONAPrice().get("JPY");
				totalWeight2*=data.getJPYPrice().get("XEM");
				System.out.println(totalWeight2 + "XEM;BTC;MONA;JPY;XEM");
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
		//第一ノードがJPY
		for (int i = 1; i <= data.getNodenumbers()-2;i++){
			totalWeight2 = 1.0;
			nextWeight2 = 1.0;
			nextNode2 = "JPY";
			nextWeight2*=data.getXEMPrice().get("JPY");
			totalWeight2*=data.getXEMPrice().get("JPY");
			
			if(i == 1){//第2ノードがBTC
				totalWeight2*=data.getJPYPrice().get("BTC");
				totalWeight2*=data.getBTCPrice().get("XEM");
				System.out.println(totalWeight2 + "XEM;JPY;BTC;XEM");
			}
			//第2ノードがMONA
			else if(i == 2){
				totalWeight2*=data.getJPYPrice().get("MONA");
				totalWeight2*=data.getMONAPrice().get("BTC");
				totalWeight2*=data.getBTCPrice().get("XEM");
				System.out.println(totalWeight2 + "XEM;JPY;MONA;BTC;XEM");
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
		double totalWeight = 1.0;
		double nextWeight = 1.0;
		
		//XEM→BTC→???
		if(data.getRoute().get(1).equals("BTC")){
			for (int i = 1; i <= data.getNodenumbers()-2;i++){
				String nextNode2 = null;
				double totalWeight2 = 1.0;
				double nextWeight2 = 1.0;
				if(i == 1){////XEM→BTC→JPY→XEM
					nextNode2 = "JPY";
					nextWeight2*=data.getBTCPrice().get("JPY");
					totalWeight2*=data.getBTCPrice().get("JPY");
					totalWeight2*=data.getJPYPrice().get("XEM");
				}
				////XEM→BTC→MONA→JPY→XEM
				else if(i == 2){
					nextNode2 = "MONA";
					nextWeight2*=data.getBTCPrice().get("MONA");
					totalWeight2*=data.getBTCPrice().get("MONA");
					totalWeight2*=data.getMONAPrice().get("JPY");
					totalWeight2*=data.getJPYPrice().get("XEM");
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
		//XEM→JPY→???
		else if(data.getRoute().get(1).equals("JPY")){
			for (int i = 1; i <= data.getNodenumbers()-2;i++){
				String nextNode2 = null;
				double totalWeight2 = 1.0;
				double nextWeight2 = 1.0;
				if(i == 1){////XEM→BTC→JPY→XEM
					nextNode2 = "BTC";
					nextWeight2*=data.getJPYPrice().get("BTC");
					totalWeight2*=data.getJPYPrice().get("BTC");
					totalWeight2*=data.getBTCPrice().get("XEM");
				}
				////XEM→BTC→MONA→JPY→XEM
				else if(i == 2){
					nextNode2 = "MONA";
					nextWeight2*=data.getJPYPrice().get("MONA");
					totalWeight2*=data.getJPYPrice().get("MONA");
					totalWeight2*=data.getMONAPrice().get("BTC");
					totalWeight2*=data.getBTCPrice().get("XEM");
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
	//第4ノードを決定する
	public void transition3(){
		String nextNode = null;
		double weight = 1.0;
		
		//XEM→BTC→JPY→XEM
		if(data.getRoute().get(2).equals("JPY")){
			nextNode = "XEM";
			weight*=data.getJPYPrice().get("XEM");
		}
		//XEM→JPY→BTC→XEM
		else if(data.getRoute().get(2).equals("BTC")){
			nextNode = "XEM";
			weight*=data.getBTCPrice().get("XEM");
		}
		//XEM→BTC→MONA→JPY→XEM
		else if(data.getRoute().get(2).equals("MONA") && data.getRoute().get(1).equals("BTC")){
			nextNode = "JPY";
			weight*=data.getMONAPrice().get("JPY");
		}
		//XEM→JPY→MONA→BTC→XEM
		else if(data.getRoute().get(2).equals("MONA") && data.getRoute().get(1).equals("JPY")){
			nextNode = "BTC";
			weight*=data.getMONAPrice().get("BTC");
		}
		data.setGainWeight(weight);
		data.setRoute(nextNode);
	}
	
	@Override
	//第5ノードを決定する
	public void transition4(){
		String nextNode = null;
		double weight = 1.0;
		
		//XEM→BTC→MONA→JPY→XEM
		if(data.getRoute().get(3).equals("JPY")){
			nextNode = "XEM";
			weight*=data.getJPYPrice().get("XEM");
		}
		//XEM→JPY→MONA→BTC→XEM
		else if(data.getRoute().get(3).equals("BTC")){
			nextNode = "XEM";
			weight*=data.getBTCPrice().get("XEM");
		}
		data.setGainWeight(weight);
		data.setRoute(nextNode);
	}

}
