package com.ricky.heavywallet.util;

public class GetRandomFunction {

	private int GoodsAmount = 20;
	private int GoodsTotalPrice = 0;
	private int[] Array_GoodsPrice;
	private int[] Array_ForRandom;
	
	public GetRandomFunction(int amount){
	
		this.GoodsAmount = amount;
			
		Array_GoodsPrice = new int[GoodsAmount];
		Array_ForRandom = new int[GoodsAmount];
		
		double Rprice, Rarray;
		
		for (int i = 0; i < Array_ForRandom.length; i++)
			Array_ForRandom[i] = i;
		
		for(int i = 0; i < Array_GoodsPrice.length; i++){
			
			Rprice = Math.random();
			Rarray = Math.random();
			Rarray = Rarray*1000;
			
			if(i == 0)
				Array_GoodsPrice[Array_ForRandom[(int)(Rarray % GoodsAmount)]] = (int)((Rprice*1000000) % 401)+800;
			else if(i == 1)
				Array_GoodsPrice[Array_ForRandom[(int)(Rarray % GoodsAmount)]] = (int)((Rprice*1000000) % 201)+400;
			else if(i == 2)
				Array_GoodsPrice[Array_ForRandom[(int)(Rarray % GoodsAmount)]] = (int)((Rprice*1000000) % 101)+300;
			else if(i == 3)
				Array_GoodsPrice[Array_ForRandom[(int)(Rarray % GoodsAmount)]] = (int)((Rprice*1000000) % 101)+200;
			else if(i >= 4 && i < 10)
				Array_GoodsPrice[Array_ForRandom[(int)(Rarray % GoodsAmount)]] = (int)((Rprice*1000000) % 201)+100;	
			else if(i >= 10 && i < 15)
				Array_GoodsPrice[Array_ForRandom[(int)(Rarray % GoodsAmount)]] = (int)((Rprice*1000000) % 101)+50;
			else if(i >= 15 && i < Array_GoodsPrice.length)
				Array_GoodsPrice[Array_ForRandom[(int)(Rarray % GoodsAmount)]] = (int)((Rprice*1000000) % 50)+10;	
				
			Array_ForRandom[(int)(Rarray % GoodsAmount)] = Array_ForRandom[GoodsAmount-1];
			GoodsAmount--;
				
		}
				
		for (int i = 0; i < Array_GoodsPrice.length; i++)
			this.GoodsTotalPrice = this.GoodsTotalPrice + Array_GoodsPrice[i];
			
	}
	
	public void SetGoodsAmount(int num){
		this.GoodsAmount = num;
	}
	
	public int[] GetRandGoodsPriceArray(){
        return Array_GoodsPrice != null ? Array_GoodsPrice : null;
	}
	
	public int GetGoodsTotalPrice(){
        return this.GoodsTotalPrice > 0 ? this.GoodsTotalPrice : 0;
	}

}
