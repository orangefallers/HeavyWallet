package com.ricky.heavywallet.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ricky.heavywallet.R;

public class goods {
	
	private static Bitmap goods;
	private static Bitmap goodstext; 
	private static Bitmap moneynumber;
	private static Bitmap scorenumber;
	//private static Bitmap GoodsImage;
	//private static Bitmap[] goodsImage =new Bitmap[20];
	private static Bitmap[] goodsText =new Bitmap[20];
	private static int goodsprice;
	//private static int PlayerMoney;
	private static int starttime;
	private static int endtime;
	private static int StoreGoodsNumber;
    private static int Goodsnum;
	
	
	private static int[] StoreCoordinate=new int[40];
	//private static int[] StoreGoodsNumber= new int [20];
	
	public goods(){}
	
	public static void InitGoodsImage(Resources r)
	{
		//GoodsImage = BitmapFactory.decodeResource(r, R.drawable.goodsbmp);
	}
	
	public static void InitGoodsText(Resources r)
	{
		goodsText[0] = BitmapFactory.decodeResource(r, R.drawable.tx01);
		goodsText[1] = BitmapFactory.decodeResource(r, R.drawable.tx02);
		goodsText[2] = BitmapFactory.decodeResource(r, R.drawable.tx03);
		goodsText[3] = BitmapFactory.decodeResource(r, R.drawable.tx04);
		goodsText[4] = BitmapFactory.decodeResource(r, R.drawable.tx05);
		goodsText[5] = BitmapFactory.decodeResource(r, R.drawable.tx06);
		goodsText[6] = BitmapFactory.decodeResource(r, R.drawable.tx07);
		goodsText[7] = BitmapFactory.decodeResource(r, R.drawable.tx08);
		goodsText[8] = BitmapFactory.decodeResource(r, R.drawable.tx09);
		goodsText[9] = BitmapFactory.decodeResource(r, R.drawable.tx10);
		goodsText[10] = BitmapFactory.decodeResource(r, R.drawable.tx11);
		goodsText[11] = BitmapFactory.decodeResource(r, R.drawable.tx12);
		goodsText[12] = BitmapFactory.decodeResource(r, R.drawable.tx13);
		goodsText[13] = BitmapFactory.decodeResource(r, R.drawable.tx14);
		goodsText[14] = BitmapFactory.decodeResource(r, R.drawable.tx15);
		goodsText[15] = BitmapFactory.decodeResource(r, R.drawable.tx16);
		goodsText[16] = BitmapFactory.decodeResource(r, R.drawable.tx17);
		goodsText[17] = BitmapFactory.decodeResource(r, R.drawable.tx18);
		goodsText[18] = BitmapFactory.decodeResource(r, R.drawable.tx19);
		goodsText[19] = BitmapFactory.decodeResource(r, R.drawable.tx20);
			
	}
	
	public static void ReleaseGoodsText()
	{
		for(int i=0;i<goodsText.length;i++)
			goodsText[i].recycle();
	}
	
	public static void InitNumberbmp(Resources r)
	{
		moneynumber=BitmapFactory.decodeResource(r, R.drawable.moneynumber);
		scorenumber=BitmapFactory.decodeResource(r, R.drawable.scorenumber);
	}
	
	public static void ReleaseNumberbmp()
	{
		moneynumber.recycle();
		scorenumber.recycle();
	}
	
	public static Bitmap getMoneyNumber()
	{
		return moneynumber;
	}
	
	public static Bitmap getScoreNumber()
	{
		return scorenumber;
	}
	/*public static void SetGoodsBmp(Bitmap bmp)
	{
		goods=bmp;
	}
				
	public static Bitmap GetNowStoreGoodsBmp()
	{
		return goods;
	}
	*/
	public static void SetGoodsText(int txtnum)
	{
		goodstext=goodsText[txtnum];
	}
	
	public static void SetGoodsText(Bitmap bmp)
	{
		goodstext=bmp;
	}
		
	public static Bitmap GetNowStoreGoodsText()
	{
		return goodstext;
	}
	public static void SetStoreGoodsNumber(int num)
	{
		StoreGoodsNumber=num;
	}
	
	public static int GetNowGoodsNumber()
	{
		return StoreGoodsNumber;
	}
		
	public static int GetNowGoodsPrice()
	{
		return goodsprice;
	}
	
	public static void SetGoodsnum(int num)
	{
		Goodsnum=num;
	}
	
	public static int GetGoodsnum()
	{
		return Goodsnum;
	}
	
	public static void setStartsec(int stime)
	{
		starttime=stime;
	}
	
	public static void setEndsec(int etime)
	{
		endtime=etime;
	}
	
	public static int getGameDuringTime() //��o���a�Ҫ�O�I��ɶ�
	{
		return endtime-starttime;
	}
	
	public static void SetStoreCoordinate(int[] sarray) //�]�w20�a���a�y��
	{
		for(int i=0;i<sarray.length;i++)
		{
			StoreCoordinate[i]=sarray[i];
		}
	}
	
	public static void coodinateTogoods(int x, int y) //�N���a�y���ഫ���������ӫ~�Ϥ�
	{
		for(int i=0;i<StoreCoordinate.length;i=i+2)
		{
			if(x==StoreCoordinate[i] && y==StoreCoordinate[i+1])
			{
				SetGoodsText(goodsText[i/2]);
				SetStoreGoodsNumber(i/2);
			}
			
		}
	}
}
