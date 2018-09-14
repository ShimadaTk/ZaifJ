package Zaif;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Zaifのデータを格納するクラス（他の取引所のデータも格納予定）
 */
public class ZaifData {
	
	//各通貨のフィールド
	private ZaifBTC BTC;
	private ZaifJPY JPY;
	private ZaifMONA MONA;
	private ZaifXEM XEM;
	
	
	//各通貨の変換先を格納
	private Map<String, Double> BTCMap = new HashMap<>();
	private Map<String, Double> JPYMap = new HashMap<>();
	private Map<String, Double> MONAMap = new HashMap<>();
	private Map<String, Double> XEMMap = new HashMap<>();
	
	//変換の際の状態を示す
	private ZaifEx zaifExchange = null;
	
	//通貨の総数
	private int nodenumbers = 4;
	
	/*
	 * ノードの経路の重さ表すArrayList
	 */
	private Double gainWeight = 1.0;
	
	/*
	 * ノードの経路の重さ表すArrayList
	 */
	private ArrayList<String> route = new ArrayList<>();
	
	/*
	 * コンストラクタ
	 */
	public ZaifData(){
		BTC = new ZaifBTC(this);
		JPY = new ZaifJPY(this);
		MONA = new ZaifMONA(this);
		XEM = new ZaifXEM(this);
	}
/////////////////////////////////////////////////////////////////////
	//ゲッター(BTC)
	public ZaifBTC getBTC(){
		return BTC;		
	}
	
	//ゲッター(JPY)
	public ZaifJPY getJPY(){
		return JPY;		
	}
	 //ゲッター(MONA)
	public ZaifMONA getMONA(){
		return MONA;		
	}
	
	//ゲッター(XEM)
	public ZaifXEM getXEM(){
		return XEM;		
	}
/////////////////////////////////////////////////////////////////////
	
	//セッター(BTCMap)
	public void setBTCPrice(String key,Double value){
		this.BTCMap.put(key,value);		
	}
	
	//セッター(JPYMap)
	public void setJPYPrice(String key,Double value){
		this.JPYMap.put(key,value);		
	}
	
	//セッター(MONAMap)
	public void setMONAPrice(String key,Double value){
		this.MONAMap.put(key,value);		
	}
	
	//セッター(XEMMap)
	public void setXEMPrice(String key,Double value){
		this.XEMMap.put(key,value);		
	}
	
/////////////////////////////////////////////////////////////////////
	
	////ゲッター(BTCMap)
	public Map<String, Double> getBTCPrice(){
		return BTCMap;		
	}
	
	//ゲッター(JPYMap)
	public Map<String, Double> getJPYPrice(){
		return JPYMap;		
	}
	 //ゲッター(MONAMap)
	public Map<String, Double> getMONAPrice(){
		return MONAMap;		
	}
	
	//ゲッター(XEMMap)
	public Map<String, Double> getXEMPrice(){
		return XEMMap;		
	}
/////////////////////////////////////////////////////////////////////
	//セッター(扱う仮想通貨)
	public void setzaifExchange(ZaifEx zaifExchange){
		this.zaifExchange = zaifExchange;
	}
	
	//ゲッター(扱う仮想通貨)
	public ZaifEx getzaifExchange(){
		return zaifExchange;
	}
/////////////////////////////////////////////////////////////////////
	//ゲッター(仮想通貨の数)
	public int getNodenumbers(){
		return nodenumbers;
	}
/////////////////////////////////////////////////////////////////////
	//ゲッター(結果の重み)
	public Double getGainWeight(){
		return gainWeight;
	}
	//セッター(結果の重み)
	public void setGainWeight(Double gainWeight){
		this.gainWeight *= gainWeight;
	}
/////////////////////////////////////////////////////////////////////
	//ゲッター(経路)
	public ArrayList<String> getRoute(){
		return route;
	}
	//セッター(経路)
	public void setRoute(String nextNode){
		this.route.add(nextNode);
	}
	
/////////////////////////////////////////////////////////////////////
}
