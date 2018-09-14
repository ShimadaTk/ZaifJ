package test;

import java.util.Scanner;

import Zaif.ZaifData;


/**
 * Zaifでの、変換結果を示すクラス
 */
public class Main_MaxRoute {

	public static void main(String[] args) throws InterruptedException {
		// TODO 自動生成されたメソッド・スタブ
		ZaifData data = new ZaifData();
		
		//変換先の通貨との対応する重みを格納
		data.getBTC().exchange();
		data.getJPY().exchange();
		data.getMONA().exchange();
		data.getXEM().exchange();
		
		System.out.println("変換する通貨を選択してください：");
		Scanner scan = new Scanner(System.in);
		String str = scan.next();	    
    
		//変換する通貨を決定
		if(str.equals("BTC")){//変換元＝BTC
			data.setzaifExchange(data.getBTC());
			data.setRoute("BTC");
		}
		else if(str.equals("JPY")){//変換元＝JPY
			data.setzaifExchange(data.getJPY());
			data.setRoute("JPY");
		}
		else if(str.equals("MONA")){//変換元＝MONA
			data.setzaifExchange(data.getMONA());
			data.setRoute("MONA");
		}
		else if(str.equals("XEM")){//変換元＝XEM
			data.setzaifExchange(data.getXEM());
			data.setRoute("XEM");
		}
		else{
			System.out.print("入力した通貨は存在しません");
			return;
		}
	    
/*	    //最も効率の良いルートを決定する
	   ArrayList<String> route = calc.getzaifExchange().transition();
	   System.out.println("時間の影響なしで考える場合");
	   for (int j = 0; j < route.size();j++){
			if(j<route.size()-1){
				System.out.print(route.get(j)+"→");
			}
			else{
				System.out.println(route.get(j)+"が最も効率の良い変換です。");
				System.out.println("変換結果:"+calc.getGainWeight());
			}
		}
		*/
	   
	   for(int i = 1; i <= 5; i++){
		   data.getBTC().exchange();
		   data.getJPY().exchange();
		   data.getMONA().exchange();
		   data.getXEM().exchange();
		   if(i==1){
			   data.getzaifExchange().transition();
		   }
		   else if(i==2){
			   data.getzaifExchange().transition2();
		   }
		   else if(i==3){
			   data.getzaifExchange().transition3();
		   }
		   else if(i==4){
			   data.getzaifExchange().transition4();
		   }
		   if((data.getRoute().get(i)).equals(str)){
			   break;
		   }
		//   Thread.sleep(10000);
	   }
	   for (int j = 0; j < data.getRoute().size();j++){
			if(j<data.getRoute().size()-1){
				System.out.print(data.getRoute().get(j)+"→");
			}
			else{
				System.out.println(data.getRoute().get(j)+"が最も効率の良い変換です。");
				System.out.println("変換結果:"+data.getGainWeight());
			}
	   }
	}

}
