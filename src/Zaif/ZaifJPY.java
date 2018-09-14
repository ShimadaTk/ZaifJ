package Zaif;


import jp.nyatla.jzaif.api.PublicApi;
import jp.nyatla.jzaif.types.CurrencyPair;


/**
 * Zaifで、JPYからの重みを格納し、スタートをJPYとしての変換結果を算出する
 */
public class ZaifJPY implements ZaifEx{
	
	private ZaifData data;
	
	public ZaifJPY(ZaifData data){
		this.data = data;
	}
	
	@Override
	/*
	 * 各ノード間の重みを計算、格納
	 */
	public void exchange(){
		try{
		PublicApi lp = new PublicApi(CurrencyPair.BTCJPY);
		double r1 = lp.ticker().ask;
		data.setJPYPrice("BTC", (1/r1));
		
		lp = new PublicApi(CurrencyPair.MONAJPY);
		r1 = lp.ticker().ask;
		r1 *= 1.001;
		data.setJPYPrice("MONA", (1/r1));
		
		lp = new PublicApi(CurrencyPair.XEMJPY);
		r1 = lp.ticker().ask;
		r1 *= 1.001;
		data.setJPYPrice("XEM", (1/r1));
		
		//エラー発生時は0を格納
		}catch(Exception e){
			e.printStackTrace();
			data.setJPYPrice("BTC", 0.0);
			data.setJPYPrice("MONA", 0.0);
			data.setJPYPrice("XEM", 0.0);
	}
		return;
	}

	@Override
	//第2ノードを決定する
	public void transition(){
		String nextNode = null;//決定経路
		String nextNode2;//仮経路
		Double totalWeight = 1.0;
		Double totalWeight2 = 0.0;
		Double nextWeight = 1.0;
		Double nextWeight2 = 1.0;
		
//////////////////////////////////////////////////////////////////////////	
		//第一ノードがBTC
		for (int i = 1; i <= data.getNodenumbers()-2;i++){
			totalWeight2 = 1.0;
			nextWeight2 = 1.0;
			nextNode2 = "BTC";
			nextWeight2*=data.getJPYPrice().get("BTC");
			totalWeight2*=data.getJPYPrice().get("BTC");
			
			if(i == 1){//第2ノードがMONA
				totalWeight2*=data.getBTCPrice().get("MONA");
				totalWeight2*=data.getMONAPrice().get("JPY");
				System.out.println(totalWeight2 + "JPY;BTC;MONA;JPY");
/*				for (int j = 0; j < expectation.size();j++){
					System.out.println(expectation.get(j));
				}
*/				
//				System.out.println(temporaryWeight);
			}
			//第2ノードがXEM
			else if(i == 2){
				totalWeight2*=data.getBTCPrice().get("XEM");
				totalWeight2*=data.getXEMPrice().get("JPY");
				System.out.println(totalWeight2 + "JPY;BTC;XEM;JPY");
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
			nextWeight2*=data.getJPYPrice().get("MONA");
			totalWeight2*=data.getJPYPrice().get("MONA");
			
			//第2ノードはBTC
			totalWeight2*=data.getMONAPrice().get("BTC");
			
			//終了
			if(i == 1){
				totalWeight2*=data.getBTCPrice().get("JPY");
				System.out.println(totalWeight2 + "JPY;MONA;BTC;JPY");
			}
			//第3ノードがXEM
			else if(i == 2){
				totalWeight2*=data.getBTCPrice().get("XEM");
				totalWeight2*=data.getXEMPrice().get("JPY");
				System.out.println(totalWeight2 + "JPY;MONA;BTC;XEM;JPY");
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
			nextWeight2*=data.getJPYPrice().get("XEM");
			totalWeight2*=data.getJPYPrice().get("XEM");
			
			//第2ノードはBTC
			totalWeight2*=data.getXEMPrice().get("BTC");
			
			//終了
			if(i == 1){
				totalWeight2*=data.getBTCPrice().get("JPY");
				System.out.println(totalWeight2 + "JPY;XEM;BTC;JPY");
			}
			//第3ノードがMONA
			else if(i == 2){
				totalWeight2*=data.getBTCPrice().get("MONA");
				totalWeight2*=data.getMONAPrice().get("JPY");
				System.out.println(totalWeight2 + "JPY;XEM;BTC;MONA;JPY");
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
		
		//JPY→BTC→???
		if(data.getRoute().get(1).equals("BTC")){
			for (int i = 1; i <= data.getNodenumbers()-2;i++){
				String nextNode2 = null;
				double totalWeight2 = 1.0;
				double nextWeight2 = 1.0;
				if(i == 1){////JPY→BTC→MONA
					nextNode2 = "MONA";
					nextWeight2*=data.getBTCPrice().get("MONA");
					totalWeight2*=data.getBTCPrice().get("MONA");
					totalWeight2*=data.getMONAPrice().get("JPY");
				}
				////JPY→BTC→XEM
				else if(i == 2){
					nextNode2 = "XEM";
					nextWeight2*=data.getBTCPrice().get("XEM");
					totalWeight2*=data.getBTCPrice().get("XEM");
					totalWeight2*=data.getXEMPrice().get("JPY");
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
		//JPY→MONA→???
		else if(data.getRoute().get(1).equals("MONA")){
			nextNode = "BTC";
			nextWeight*=data.getMONAPrice().get("BTC");
		}
		//JPY→XEM→???
		else if(data.getRoute().get(1).equals("XEM")){
			nextNode = "BTC";
			nextWeight*=data.getXEMPrice().get("BTC");
		}
		data.setGainWeight(nextWeight);
		data.setRoute(nextNode);
	}
	
	@Override
	//第4ノードを決定する
	public void transition3(){
		String nextNode = null;
		double totalWeight = 1.0;
		double nextWeight = 1.0;
		
		//JPY→BTC→MONA→JPY
		if(data.getRoute().get(2).equals("MONA")){
			nextNode = "JPY";
			nextWeight*=data.getMONAPrice().get("JPY");
		}
		//JPY→BTC→XEM→JPY
		else if(data.getRoute().get(2).equals("XEM")){
			nextNode = "JPY";
			nextWeight*=data.getXEMPrice().get("JPY");
		}
		//JPY→MONA→BTC→???
		else if(data.getRoute().get(2).equals("BTC") && data.getRoute().get(1).equals("MONA")){
			for (int i = 1; i <= data.getNodenumbers()-2;i++){
				String nextNode2 = null;
				double totalWeight2 = 1.0;
				double nextWeight2 = 1.0;
				if(i == 1){////JPY→MONA→BTC→XEM
					nextNode2 = "XEM";
					nextWeight2*=data.getBTCPrice().get("XEM");
					totalWeight2*=data.getBTCPrice().get("XEM");
					totalWeight2*=data.getXEMPrice().get("JPY");
				}
				////JPY→MONA→BTC→JPY
				else if(i == 2){
					nextNode2 = "JPY";
					nextWeight2*=data.getBTCPrice().get("JPY");
					totalWeight2*=data.getBTCPrice().get("JPY");
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
		//JPY→XEM→BTC→???
		else if(data.getRoute().get(2).equals("BTC") && data.getRoute().get(1).equals("XEM")){
			for (int i = 1; i <= data.getNodenumbers()-2;i++){
				String nextNode2 = null;
				double totalWeight2 = 1.0;
				double nextWeight2 = 1.0;
				if(i == 1){////JPY→XEM→BTC→MONA
					nextNode2 = "MONA";
					nextWeight2*=data.getBTCPrice().get("MONA");
					totalWeight2*=data.getBTCPrice().get("MONA");
					totalWeight2*=data.getMONAPrice().get("JPY");
				}
				////JPY→XEM→BTC→JPY
				else if(i == 2){
					nextNode2 = "JPY";
					nextWeight2*=data.getBTCPrice().get("JPY");
					totalWeight2*=data.getBTCPrice().get("JPY");
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
	//第5ノードを決定する
	public void transition4(){
		String nextNode = null;
		double weight = 1.0;
		
		//JPY→XEM→BTC→MONA→JPY
		if(data.getRoute().get(3).equals("MONA")){
			nextNode = "JPY";
			weight*=data.getMONAPrice().get("JPY");
		}
		//JPY→MONA→BTC→XEM→JPY
		else if(data.getRoute().get(3).equals("XEM")){
			nextNode = "JPY";
			weight*=data.getXEMPrice().get("JPY");
		}
		
		data.setGainWeight(weight);
		data.setRoute(nextNode);
	}

}
