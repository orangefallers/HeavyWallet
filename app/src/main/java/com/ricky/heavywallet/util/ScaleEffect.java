package com.ricky.heavywallet.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class ScaleEffect {
	
	private Bitmap bmp;
	Paint paint = new Paint();
	
	public ScaleEffect(Bitmap bmp)
	{
		this.bmp=bmp;
		
	}
	
	public void drawEffect(Canvas canvas)
	{
		canvas.drawBitmap(bmp, 10,10, paint);
	}

}
