package Zaif;



import jp.nyatla.jzaif.api.PublicApi;
import jp.nyatla.jzaif.types.CurrencyPair;


/**
 * Zaifで、MONAからの重みを格納し、スタートをMOANとしての変換結果を算出する
 */
public class ZaifMONA implements ZaifEx{
	
	private ZaifData data;
	
	public ZaifMONA(ZaifData data){
		this.data = data;
	}

	@Override
	/*
	 * 各ノード間の重みを計算、格納
	 */
	public void exchange(){
		try{
		PublicApi lp = new PublicApi(CurrencyPair.MONAJPY);
		double r1 = lp.ticker().bid;
		data.setMONAPrice("JPY", r1);
		
		lp = new PublicApi(CurrencyPair.MONABTC);
		r1 = lp.ticker().bid;
		data.setMONAPrice("BTC", r1);
		
		//エラー発生時は0を格納
		}catch(Exception e){
			e.printStackTrace();
			data.setMONAPrice("BTC", 0.0);
			data.setMONAPrice("JPY", 0.0);
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
			nextWeight2*=data.getMONAPrice().get("BTC");
			totalWeight2*=data.getMONAPrice().get("BTC");
			
			if(i == 1){//第2ノードがJPY
				totalWeight2*=data.getBTCPrice().get("JPY");
				totalWeight2*=data.getJPYPrice().get("MONA");			
				System.out.println(totalWeight2 + "MONA;BTC;JPY;MONA");
			}
			//第2ノードがXEM
			else if(i == 2){
				totalWeight2*=data.getBTCPrice().get("XEM");
				totalWeight2*=data.getXEMPrice().get("JPY");
				totalWeight2*=data.getJPYPrice().get("MONA");
				System.out.println(totalWeight2 + "MONA;BTC;XEM;JPY;MONA");
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
			nextWeight2*=data.getMONAPrice().get("JPY");
			
			totalWeight2*=data.getMONAPrice().get("JPY");
			
			if(i == 1){//第2ノードがBTC
				totalWeight2*=data.getJPYPrice().get("BTC");
				totalWeight2*=data.getBTCPrice().get("MONA");
				System.out.println(totalWeight2 + "MONA;JPY;BTC;MONA");
			}
			//第2ノードがXEM
			else if(i == 2){
				totalWeight2*=data.getJPYPrice().get("XEM");
				totalWeight2*=data.getXEMPrice().get("BTC");
				totalWeight2*=data.getBTCPrice().get("MONA");
				System.out.println(totalWeight2 + "MONA;JPY;XEM;BTC;MONA");
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
		
		//MONA→BTC→???
		if(data.getRoute().get(1).equals("BTC")){
			for (int i = 1; i <= data.getNodenumbers()-2;i++){
				String nextNode2 = null;
				double totalWeight2 = 1.0;
				double nextWeight2 = 1.0;
				if(i == 1){////MONA→BTC→JPY→MONA
					nextNode2 = "JPY";
					nextWeight2*=data.getBTCPrice().get("JPY");
					totalWeight2*=data.getBTCPrice().get("JPY");
					totalWeight2*=data.getJPYPrice().get("MONA");
				}
				////MONA→BTC→XEM→JPY→MONA
				else if(i == 2){
					nextNode2 = "XEM";
					nextWeight2*=data.getBTCPrice().get("XEM");
					totalWeight2*=data.getBTCPrice().get("XEM");
					totalWeight2*=data.getXEMPrice().get("JPY");
					totalWeight2*=data.getJPYPrice().get("MONA");
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
		//MONA→JPY→???
		else if(data.getRoute().get(1).equals("JPY")){
			for (int i = 1; i <= data.getNodenumbers()-2;i++){
				String nextNode2 = null;
				double totalWeight2 = 1.0;
				double nextWeight2 = 1.0;
				if(i == 1){////MONA→BTC→JPY→MONA
					nextNode2 = "BTC";
					nextWeight2*=data.getJPYPrice().get("BTC");
					totalWeight2*=data.getJPYPrice().get("BTC");
					totalWeight2*=data.getBTCPrice().get("MONA");
				}
				////MONA→BTC→XEM→JPY→MONA
				else if(i == 2){
					nextNode2 = "XEM";
					nextWeight2*=data.getJPYPrice().get("XEM");
					totalWeight2*=data.getJPYPrice().get("XEM");
					totalWeight2*=data.getXEMPrice().get("BTC");
					totalWeight2*=data.getBTCPrice().get("MONA");
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
		
		//MONA→BTC→JPY→MONA
		if(data.getRoute().get(2).equals("JPY")){
			nextNode = "MONA";
			weight*=data.getJPYPrice().get("MONA");
		}
		//MONA→JPY→BTC→MONA
		else if(data.getRoute().get(2).equals("BTC")){
			nextNode = "MONA";
			weight*=data.getBTCPrice().get("MONA");
		}
		//MONA→BTC→XEM→JPY→MONA
		else if(data.getRoute().get(2).equals("XEM") && data.getRoute().get(1).equals("BTC")){
			nextNode = "JPY";
			weight*=data.getXEMPrice().get("JPY");
		}
		//MONA→JPY→XEM→BTC→MONA
		else if(data.getRoute().get(2).equals("XEM") && data.getRoute().get(1).equals("JPY")){
			nextNode = "BTC";
			weight*=data.getXEMPrice().get("BTC");
		}
		data.setGainWeight(weight);
		data.setRoute(nextNode);
	}
	
	@Override
	//第5ノードを決定する
	public void transition4(){
		String nextNode = null;
		double weight = 1.0;
		
		//MONA→BTC→XEM→JPY→MONA
		if(data.getRoute().get(3).equals("JPY")){
			nextNode = "MONA";
			weight*=data.getJPYPrice().get("MONA");
		}
		//MONA→JPY→XEM→BTC→MONA
		else if(data.getRoute().get(3).equals("BTC")){
			nextNode = "MONA";
			weight*=data.getBTCPrice().get("MONA");
		}
		data.setGainWeight(weight);
		data.setRoute(nextNode);
	}

}
