package com.ricky.heavywallet.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.ricky.heavywallet.HeavyWalletActivity;
import com.ricky.heavywallet.R;
import com.ricky.heavywallet.util.HeavyWalletParameter;

public class HowPlayView extends View implements Runnable {

    private int delay = 50;
    private int appViewWidth;
    private int appViewHeight;
    private int Ymove = 0, ymove = 0;
    private int Ybig = 1600;
    HeavyWalletActivity noactivity;


    private Bitmap howplay;
    private Bitmap backbutton;

    RectF displayhowplay = new RectF();
    RectF displayback = new RectF();

    public HowPlayView(Context context, HeavyWalletActivity noactivity, int appViewWidth, int appViewHeight) {
        super(context);
        this.noactivity = noactivity;
        this.appViewHeight = appViewHeight;
        this.appViewWidth = appViewWidth;
        displayhowplay.set(0, Ymove, this.appViewWidth, Ymove + Ybig);
        displayback.set(this.appViewWidth * 6 / 7, this.appViewHeight * 7 / 8, this.appViewWidth, this.appViewHeight);
        // TODO Auto-generated constructor stub
    }

    public void InitHowPlayViewBmp() {
        Resources r = getResources();
        howplay = BitmapFactory.decodeResource(r, R.mipmap.howtoplay);
        backbutton = BitmapFactory.decodeResource(r, R.drawable.back);


    }

    public void ReleaseHowPlayViewbmp() {
        if (howplay != null)
            howplay.recycle();
        if (backbutton != null)
            backbutton.recycle();
    }

    public void InitHowPlayView() {
        Ymove = 0;
        ymove = 0;
    }

    private void howplayrange() {
        if (Ymove > 0) {
            Ymove = 0;
        }
        if (Ymove < (appViewHeight - Ybig)) {
            Ymove = (appViewHeight - Ybig);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub

        canvas.drawColor(Color.BLACK);
        howplayrange();
        displayhowplay.set(0, Ymove, this.appViewWidth, Ymove + Ybig);
        canvas.drawBitmap(howplay, null, displayhowplay, null);

        canvas.drawBitmap(backbutton, null, displayback, null);

    }

    int touchY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        int x = (int) event.getX();
        int y = (int) event.getY();


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                touchY = (int) event.getY();
                //Ymove=Ymove-10;
                break;
            case MotionEvent.ACTION_MOVE:

                if (Ymove <= 0 && Ymove >= (appViewHeight - Ybig)) {
                    Ymove = y - touchY + ymove;
                }

                //mapY=my-touchY+Ymove;
                break;
            case MotionEvent.ACTION_UP:

                ymove = Ymove;

                if (displayback.contains(x, y)) {
                    ReleaseHowPlayViewbmp();
                    noactivity.ncHandler.sendEmptyMessage(HeavyWalletParameter.CHANGETO_GAMEMAINVIEW);

                }
                //mapx=mapX;
                //mapy=mapY;
                break;
        }


        return true;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        invalidate();
        postDelayed(this, delay);
    }

}
