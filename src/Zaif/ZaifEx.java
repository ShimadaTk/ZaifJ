package Zaif;


public interface ZaifEx {
	
	/*
	 * 各ノード間の重みを計算、格納
	 */
	public void exchange();

	/*
	 * 効率の良い経路を決定する
	 */
	//第2ノードを決定する
	public void transition();

	//第3ノードを決定する
	public void transition2();

	//第4ノードを決定する
	public void transition3();

	//第5ノードを決定する(必ず帰ってくる)
	public void transition4();
	 
}
