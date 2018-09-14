package test;

import Zaif.ZaifData;

/**
 * ZaifExクラスに関連するクラスのメソッドexchange()をテストするクラス
 */
public class ZaifEXTest_exchange {

	public static void main(String[] args) {
		ZaifData data = new ZaifData();
		
		final int BTC_JPYnodeNumber = data.getNodenumbers()-1;
		final int othersnodeNumber = 2;
		/*
		 * ZaifBTCのテスト
		 */
		data.setzaifExchange(data.getBTC());
		data.getBTC().exchange();				

		//変換先ノード数テスト
		if(!(data.getBTCPrice().size() == BTC_JPYnodeNumber)){
			System.out.println("ZaifBTCクラスのexchange()は失敗作のため、テストを終了します。");
			return;
		}
		else System.out.println("変換先ノード数正常　: " + (BTC_JPYnodeNumber));
		
		//格納されるkeyが正常かどうかのテスト
		int checkNode = 0;
		for (String key:data.getBTCPrice().keySet()){
			if(key.equals("JPY")){
				System.out.println(key);
				checkNode+=1;
			}
			if(key.equals("MONA")){
				System.out.println(key);
				checkNode+=1;
			}
			if(key.equals("XEM")){
				System.out.println(key);
				checkNode+=1;
			}
		}
		if(checkNode == BTC_JPYnodeNumber){
			System.out.println("ZaifBTCクラスのexchange()は正常に動作しています。\n");
		}
		else {
			System.out.println("ZaifBTCクラスのexchange()は失敗作のため、テストを終了します。");
			return;
		}
///////////////////////////////////////////////////////////////////////////
		/*
		 * ZaifJPYのテスト
		 */
		data.setzaifExchange(data.getJPY());
		data.getJPY().exchange();
		
		//変換先ノード数テスト
		if(!(data.getJPYPrice().size() == BTC_JPYnodeNumber)){
			System.out.println("ZaifJPYクラスのexchange()は失敗作のため、テストを終了します。");
			return;
		}
		else System.out.println("変換先ノード数正常　: " + BTC_JPYnodeNumber);
		
		//格納されるkeyが正常かどうかのテスト
		checkNode = 0;
		for (String key:data.getJPYPrice().keySet()){
			if(key.equals("BTC")){
				System.out.println(key);
				checkNode+=1;
			}
			if(key.equals("MONA")){
				System.out.println(key);
				checkNode+=1;
			}
			if(key.equals("XEM")){
				System.out.println(key);
				checkNode+=1;
			}
		}
		if(checkNode == BTC_JPYnodeNumber){
			System.out.println("ZaifJPYクラスのexchange()は正常に動作しています。\n");
		}
		else {
			System.out.println("ZaifJPYクラスのexchange()は失敗作のため、テストを終了します。");
			return;
		}
///////////////////////////////////////////////////////////////////////////
		/*
		 * ZaifMONAのテスト
		 */
		data.setzaifExchange(data.getMONA());
		data.getMONA().exchange();
		
		//変換先ノード数テスト
		if(!(data.getMONAPrice().size() == othersnodeNumber)){
			System.out.println("ZaifMONAクラスのexchange()は失敗作のため、テストを終了します。");
			return;
		}
		else System.out.println("変換先ノード数正常　: " + othersnodeNumber);
		
		//格納されるkeyが正常かどうかのテスト
		checkNode = 0;
		for (String key:data.getMONAPrice().keySet()){
			if(key.equals("BTC")){
				System.out.println(key);
				checkNode+=1;
			}
			if(key.equals("JPY")){
				System.out.println(key);
				checkNode+=1;
			}
		}
		if(checkNode == othersnodeNumber){
			System.out.println("ZaifMONAクラスのexchange()は正常に動作しています。\n");
		}	
		else {
			System.out.println("ZaifMONAクラスのexchange()は失敗作のため、テストを終了します。");
			return;
		}
///////////////////////////////////////////////////////////////////////////
		/*
		 * ZaifXEMのテスト
		 */
		data.setzaifExchange(data.getXEM());
		data.getXEM().exchange();
		
		//変換先ノード数テスト
		if(!(data.getXEMPrice().size() == othersnodeNumber)){
			System.out.println("ZaifXEMクラスのexchange()は失敗作のため、テストを終了します。");
			return;
		}
		else System.out.println("変換先ノード数正常　: " + othersnodeNumber);
		
		//格納されるkeyが正常かどうかのテスト
		checkNode = 0;
		for (String key:data.getXEMPrice().keySet()){
			if(key.equals("BTC")){
				System.out.println(key);
				checkNode+=1;
			}
			if(key.equals("JPY")){
				System.out.println(key);
				checkNode+=1;
			}
		}
		if(checkNode == othersnodeNumber){
			System.out.println("ZaifXEMクラスのexchange()は正常に動作しています。\n");
		}
		else {
			System.out.println("ZaifXEMクラスのexchange()は失敗作のため、テストを終了します。");
			return;
		}
///////////////////////////////////////////////////////////////////////////
		/*
		 * ここまでテストコードを実行できれば異常なし
		 */
		System.out.println("ZaifExクラスのexchange()は正常に動作しています。");
	}

}
