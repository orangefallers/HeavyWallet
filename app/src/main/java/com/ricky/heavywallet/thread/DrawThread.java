package com.ricky.heavywallet.thread;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.ricky.heavywallet.view.NightMarketView;

public class DrawThread extends Thread {

	NightMarketView nmview;
	SurfaceHolder surfaceHolder;
	boolean flag=false;
	private int sleepspan=40;
	
	long start = System.nanoTime();			//�O��_�l�ɶ��A���ܼƥΩ�p��e������t�v
	int count=0;								//�O��V�ơA���ܼƥΩ�p��e������t�v
	
	
	public DrawThread(NightMarketView nmview,SurfaceHolder surfaceHolder){
		this.nmview = nmview;							
		this.surfaceHolder = surfaceHolder;	
		this.flag = true;						//�]�w�X�Ц줸
	}
	
	public void setFlag(boolean flag)
	{
		this.flag=flag;
	}
	
	//��k�G���������k�A�Ω�ø�s�ù��M�p��e������t�v
	public void run(){
		Canvas canvas = null;								   //�ŧi�@��Canvas����
		while(flag)
		{//1
			try{
				canvas = surfaceHolder.lockCanvas(null);	  
				synchronized(surfaceHolder){
					nmview.doDraw(canvas);			
				}
			}
			catch(Exception e){
				e.printStackTrace();						   //����æC�L���`
			}
			finally{
				if(canvas != null){						   //�p�Gcanvas������
					surfaceHolder.unlockCanvasAndPost(canvas);//surfaceHolder����ñN�e������Ǧ^
				}
			}		
		
			this.count++;
			if(count == 20){								   //�p�G�p��20���
				count = 0;								   //�M�ŭp�ƾ�
				long tempStamp = System.nanoTime();		   //���ثe�ɶ�
				long span = tempStamp - start;				   //���ɶ����j
				start = tempStamp;						   //��start���s��w��
				double fps = Math.round(100000000000.0/span*20)/100.0;	//�p��e������tv
				//bsview.fps = "FPS:"+fps;		//�N�p��X���e������t�v�]�w��BallView�������r�ꪫ��
			}
			
			
			
			try{
				Thread.sleep(sleepspan);		//������v�@�q�ɶ�
			}
			catch(Exception e){					
				e.printStackTrace();		//����æC�L���`
			}
		}//1
	}	
	
	
}
