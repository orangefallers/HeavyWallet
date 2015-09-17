package com.ricky.heavywallet.view;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

import com.ricky.heavywallet.HeavyWalletActivity;
import com.ricky.heavywallet.R;
import com.ricky.heavywallet.util.HeavyWalletParameter;
import com.ricky.heavywallet.util.goods;

public class GameMainView extends View implements Runnable {

    private int delay = 500;
    private int appViewWidth;
    private int appViewHeight;
    private int BestScoreNum;
    private boolean isPlayBestScoreEffect = false; //�p�G���a�}�F�̨Τ��ơA�K����{�{�S��
    HeavyWalletActivity hAct;
    MediaPlayer mainmusic;
    //MediaPlayer Chviewmusic;


    private Bitmap main01;
    private Bitmap howtoplay;
    private Bitmap touchtostart;
    private Bitmap scorenum;

    Paint paint = new Paint();
    Paint paintbest = new Paint();
    Rect main = new Rect();
    Rect scorebmp = new Rect();
    RectF Tstart = new RectF();
    RectF Hplay = new RectF();
    RectF scorenumber = new RectF();
    BitmapFactory.Options options = new BitmapFactory.Options();

    public GameMainView(Activity activity, int appViewWidth, int appViewHeight) {
        super(activity);
        // TODO Auto-generated constructor stub

        this.hAct = (HeavyWalletActivity) activity;
        this.appViewHeight = appViewHeight;
        this.appViewWidth = appViewWidth;
        options.inSampleSize = 2;
        //InitBitmap();
        //InitMainMusic();
        //scorenum = BitmapFactory.decodeResource(r, R.drawable.scorenumber);

        main.set(0, 0, this.appViewWidth, this.appViewHeight);
        Tstart.set(this.appViewWidth * 2 / 8, this.appViewHeight * 12 / 16, this.appViewWidth * 5 / 8, this.appViewHeight * 15 / 16);
        Hplay.set(this.appViewWidth * 475 / 960, this.appViewHeight * 35 / 640,
                this.appViewWidth * 475 / 960 + 200, this.appViewHeight * 35 / 640 + 200);

        paint.setColor(Color.GREEN);
        paint.setTextSize(22);

    }

    public void InitBitmap() {
        Resources r = getResources();
        main01 = BitmapFactory.decodeResource(r, R.drawable.main01, options);
        howtoplay = BitmapFactory.decodeResource(r, R.drawable.mainhowtoplay);
        touchtostart = BitmapFactory.decodeResource(r, R.drawable.maintouchtostart);
        scorenum = goods.getScoreNumber();
    }

    public void releaseMainBitmap() {
        main01.recycle();
        howtoplay.recycle();
        touchtostart.recycle();
    }

    public void InitMainMusic() {
        mainmusic = MediaPlayer.create(this.hAct, R.raw.game01);
        mainmusic.start();
    }

    public void ReleaseMainMusic() {
        if (mainmusic != null) {
            mainmusic.release();
            mainmusic = null;
        }
    }

    public void setIsPlayBestScoreEffect(boolean is) {
        this.isPlayBestScoreEffect = is;
    }

    public void setBestScore(int num) {
        this.BestScoreNum = num;
    }

    int alpha = 0, anum = 0;

    private void PaintStartBmp(Canvas canvas) {
        if (anum > 3) anum = 0;
        switch (anum) {
            case 0:
                alpha = 127;
                anum++;
                break;
            case 1:
                alpha = 255;
                anum++;
                break;
            case 2:
                alpha = 127;
                anum++;
                break;
            case 3:
                alpha = 0;
                anum++;
                break;
            default:
                break;

        }

        paint.setAlpha(alpha);
        canvas.drawBitmap(touchtostart, null, Tstart, paint);

    }

    int alpha2 = 0, anum2 = 0;

    private void PaintScoreNumber(Canvas canvas, int num, boolean isplay) {
        if (num < 0) num = 0;
        int mx = 10, sy = 10, sx = 0, sp = 1;

        if (num < 10) sp = 4;
        else if (num >= 10 && num < 100) sp = 3;
        else if (num >= 100 && num < 1000) sp = 2;
        else sp = 1;

        if (isplay) {
            if (anum2 > 9) {
                anum2 = 0;
                this.isPlayBestScoreEffect = false;
            }

            if (anum2 % 2 == 0)
                alpha2 = 255;
            else
                alpha2 = 0;

            anum2++;
            paintbest.setAlpha(alpha2);
        }

        String tmpStr = "" + Math.abs(num);
        for (int i = 0; i < tmpStr.length(); i++) {
            int nm = Integer.parseInt(tmpStr.charAt(i) + "");
            if (nm > 0 && nm < 10)                          //�]�w��ܪ��Ʀr�Ϥ�
                scorebmp.set(125 * (nm - 1), 0, 125 * nm, 125);
            else
                scorebmp.set(1125, 0, 1250, 125);

            scorenumber.set(this.appViewWidth * (i + sp) / 30 + mx - sx * i, this.appViewHeight * 10 / 12 + sy,
                    this.appViewWidth * (i + (sp + 1)) / 30 + mx - sx * i, this.appViewHeight * 11 / 12 + sy);

            canvas.drawBitmap(scorenum, scorebmp, scorenumber, paintbest);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        canvas.drawBitmap(main01, null, main, null);
        canvas.drawBitmap(howtoplay, null, Hplay, null);
        PaintScoreNumber(canvas, this.BestScoreNum, this.isPlayBestScoreEffect);
        PaintStartBmp(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        int x = (int) event.getX();
        int y = (int) event.getY();


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:


                break;
            case MotionEvent.ACTION_MOVE:


                break;
            case MotionEvent.ACTION_UP:
                if (Hplay.contains(x, y)) {
                    hAct.howplayview.InitHowPlayViewBmp();
                    hAct.ncHandler.sendEmptyMessage(HeavyWalletParameter.CHANGETO_GAMERULE);
                } else {
                    this.hAct.nmview.InitBitmap();
                    this.hAct.gaview.InitGameViewBitmap();
                    this.hAct.ncHandler.sendEmptyMessage(HeavyWalletParameter.CHANGETO_NIGHTMARKETVIEW);
                    //releaseMainBitmap();
                    //ReleaseMainMusic();
                }

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
