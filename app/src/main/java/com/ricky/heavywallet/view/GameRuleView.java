package com.ricky.heavywallet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ricky.heavywallet.HeavyWalletActivity;
import com.ricky.heavywallet.R;
import com.ricky.heavywallet.util.HeavyWalletParameter;

import java.io.IOException;
import java.io.InputStream;

public class GameRuleView extends RelativeLayout {

	private LayoutInflater mInflater;
	private BitmapRegionDecoder mDecoder;
	
	private View rootView;
	private ImageView gameruleView;
	private ImageButton gotoGamebtn;
		
	private int appWidth, appHeight = 0;
	private int Value_Ycoordinate, Value_LastYcoordinate = 0;
	private int Value_TouchDownYcoordinate = 0;
	
	private HeavyWalletActivity hAct;
	Rect RectSrc_howplay = new Rect();
	
	public GameRuleView(HeavyWalletActivity mAct, int w, int h) {
		super(mAct);
		// TODO Auto-generated constructor stub
		
		this.hAct = mAct;
		this.appWidth = w;
		this.appHeight = h;
		mInflater = (LayoutInflater)hAct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		InitUI();
	    ReadBitmap();
	    setImageRegion(0,0);
	}
	
	private void InitUI(){
		rootView = mInflater.inflate(R.layout.gamerule, this);
		gameruleView = (ImageView)rootView.findViewById(R.id.GameRuleView);
		gotoGamebtn = (ImageButton) rootView.findViewById(R.id.GotoGame);
			
	}

	private void ReadBitmap(){
		
		try {
			InputStream is = getResources().openRawResource(R.mipmap.howtoplay);
			mDecoder = BitmapRegionDecoder.newInstance(is, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setImageRegion(int left, int top) {

		final int imgWidth = mDecoder.getWidth();
		final int imgHeight = mDecoder.getHeight();

		int right = left + this.appWidth;
		int bottom = top + this.appHeight;

		ShowGoToGameBtn(false);
		
		if (right > imgWidth)
			right = imgWidth;
		if (bottom > imgHeight) {
			top = imgHeight - this.appHeight;
			bottom = imgHeight;
			ShowGoToGameBtn(true);
			
		}

		if (left < 0)
			left = 0;
		if (top < 0) {
			top = 0;
			bottom = top + this.appHeight;
		}

		RectSrc_howplay.set(left, top, right, bottom);
		Bitmap bm = mDecoder.decodeRegion(RectSrc_howplay, null);
		
		gameruleView.setImageBitmap(bm);
	}
	
	private void ShowGoToGameBtn(boolean isShow){
		if (isShow) {
			gotoGamebtn.setVisibility(View.VISIBLE);
			gotoGamebtn.setOnClickListener(clickListener);
		} else {
			gotoGamebtn.setVisibility(View.GONE);
		}
	}
	
	private void GoToGameShoppingView(){
		
		this.hAct.ncHandler.sendEmptyMessage(HeavyWalletParameter.CHANGETO_GAMEVIEW);
	}

	private OnClickListener clickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			switch (v.getId()) {

			case R.id.GotoGame:
				// hAct.GameViewChangeHandler.sendEmptyMessage(HeavyWalletParameter.CHANGETO_GAMEMAINVIEW);
				GoToGameShoppingView();
				//hAct.GameViewChangeHandler.sendEmptyMessage(HeavyWalletParameter.CHANGETO_GAMEENDVIEW);
				break;
			}
		}
	};
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		final int action = ev.getAction();
		//final int x = (int) ev.getX();
		final int y = (int) ev.getY();
		
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			
			Value_TouchDownYcoordinate = y;
			
		case MotionEvent.ACTION_MOVE:
			
			if (Value_Ycoordinate < 0)
				Value_Ycoordinate = 0;

			else if (Value_Ycoordinate >= 0 && Value_Ycoordinate <= 3200)
				Value_Ycoordinate = Value_LastYcoordinate + (Value_TouchDownYcoordinate - y);

			else if (Value_Ycoordinate > (3200 - this.appHeight))
				Value_Ycoordinate = 3200 - this.appHeight;
								
			//Log.v("tag", "Value_Ycoordinate = "+ Value_Ycoordinate);
			//Log.v("tag", "Value_TouchDownYcoordinate = "+ Value_TouchDownYcoordinate);
			//Log.v("tag", "y = "+ y + " (y - Value_TouchDownYcoordinate) = " + (y - Value_TouchDownYcoordinate));
			//Log.v("tag", "Value_LastYcoordinate = "+ Value_LastYcoordinate);
				
			setImageRegion(0, Value_Ycoordinate);
			break;
		case MotionEvent.ACTION_UP:
			Value_LastYcoordinate = Value_Ycoordinate;
			break;
		}
		
		return true;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
	}

}
