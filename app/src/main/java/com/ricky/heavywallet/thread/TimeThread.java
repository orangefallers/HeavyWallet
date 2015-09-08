package com.ricky.heavywallet.thread;

import com.ricky.heavywallet.view.NightMarketView;

public class TimeThread extends Thread{
	
	private boolean flag = true;//�j��X��
	
	NightMarketView NmView;
	
	public TimeThread(NightMarketView NmView){//�غc��
		this.NmView=NmView;
	}
	
	public void setFlag(boolean flag){//�]�w�j��аO�줸
		this.flag = flag;
	}
	
	@Override
	public void run(){//�мg��run��k
		while(flag){//�j��
			
			this.NmView.timesec++;
			if(this.NmView.timesec==170)
			{
				if(this.NmView.timeupmusic!=null)
				{
					this.NmView.timeupmusic.start();
					this.NmView.timeupmusic.setLooping(true);
				}
			}
			if(this.NmView.timesec>=180)
			{
				if(this.NmView.timeupmusic!=null)
				{
					this.NmView.timeupmusic.pause();
				}
			}
			
			try{
				Thread.sleep(1000);//�ίv�@���
			}
			catch(Exception e){//���򲧱`
				e.printStackTrace();//�C�L���`��T
			}
		}
	}
}
