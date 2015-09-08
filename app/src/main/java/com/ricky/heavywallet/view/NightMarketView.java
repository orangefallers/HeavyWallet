package com.ricky.heavywallet.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ricky.heavywallet.HeavyWalletActivity;
import com.ricky.heavywallet.R;
import com.ricky.heavywallet.thread.DrawThread;
import com.ricky.heavywallet.thread.TimeThread;
import com.ricky.heavywallet.util.HeavyWalletParameter;
import com.ricky.heavywallet.util.goods;

public class NightMarketView extends SurfaceView implements SurfaceHolder.Callback {

    HeavyWalletActivity noactivity;

    Paint paint = new Paint();
    public DrawThread dt;
    public TimeThread ct;
    Resources r;
    public MediaPlayer gameingmusic;
    public MediaPlayer timeupmusic;
    public MediaPlayer timeupendmusic;

    BitmapFactory.Options options = new BitmapFactory.Options();


    private int appViewWidth;
    private int appViewHeight;
    private int mapX = -1120;    //初始地圖左上x座標
    private int mapY = -1100;    //初始地圖左上y座標
    private int UsermapX = 0;//玩家轉換成地圖座標x
    private int UsermapY = 0;//玩家轉換成地圖座標y
    private int playertotalmoney;
    private int playerscore;  //玩家的總得分
    private boolean isStartGame = false;
    private boolean isPlayBestScore = false;
    private boolean isRePaintMoon = false;
    private boolean isPlayComplete = false;
    private boolean isMapMoveing = false;
    private int playerspendtime;  //玩家所花費的購物時間
    //public int status = -1;
    public int timesec;   //計時變數


    private int[] mapStoreCoordinate = new int[40]; //存放夜市20個店家座標陣列，偶數為x，奇數為y
    private int[] touchfourpoint = new int[8]; //存放玩家點下去的座標範圍
    private int[] isTouchStore = new int[20];   //存放玩家是否點過該家商店，預設皆為否(0為否，1為真)

    private Bitmap maptest;
    private Bitmap timemoon;
    private Bitmap Scorenumber;
    private Bitmap ready;
    private Bitmap go;
    private Bitmap wango;
    private Bitmap timesup;
    private Bitmap ScoreHistory;
    private Bitmap complete;
    private Bitmap BestBonus;

    Rect moon = new Rect();
    Rect scorebmp = new Rect();
    RectF scorenumber = new RectF();
    RectF bestSocreban = new RectF();
    RectF displaybestbonus = new RectF();
    Matrix transform = new Matrix();
    //int x1,y1,pm;


    public NightMarketView(Context context, HeavyWalletActivity noactivity, int appViewWidth, int appViewHeight) {
        super(context);
        getHolder().addCallback(this);

        this.noactivity = noactivity;
        this.appViewHeight = appViewHeight;
        this.appViewWidth = appViewWidth;
        r = getResources();
        options.inSampleSize = 2;

        InitmapStoreCoordinate();

        goods.SetStoreCoordinate(mapStoreCoordinate);   //設定20個店家的地圖座標

        paint.setColor(Color.GREEN);
        paint.setTextSize(20);

        moon.set(this.appViewWidth / 2 - 50, -50, this.appViewWidth / 2 + 50, 50);

        dt = new DrawThread(this, getHolder());    //初始化重繪執行緒

        // TODO Auto-generated constructor stub
    }

    public void InitBitmap() {
        maptest = BitmapFactory.decodeResource(r, R.drawable.map02);
        timemoon = BitmapFactory.decodeResource(r, R.drawable.timemoon2);
        wango = BitmapFactory.decodeResource(r, R.drawable.wango);
        timesup = BitmapFactory.decodeResource(r, R.drawable.timesup);
        complete = BitmapFactory.decodeResource(r, R.drawable.complete);
        BestBonus = BitmapFactory.decodeResource(r, R.drawable.bonus);
        Scorenumber = goods.getScoreNumber();
    }

    public void ReleaseBitmap() {
        if (maptest != null) {
            maptest.recycle();
            timemoon.recycle();
            wango.recycle();
            timesup.recycle();
            complete.recycle();
            BestBonus.recycle();
        }
    }

    public void InitBitmapStart() {
        ready = BitmapFactory.decodeResource(r, R.drawable.ready);
        go = BitmapFactory.decodeResource(r, R.drawable.go);
    }

    public void ReleaseBitmapStart() {
        ready.recycle();
        go.recycle();
    }

    public void InitHistorybmp() {
        int num = 0;
        double Rbsbmp;
        Rbsbmp = Math.random();
        num = (int) ((Rbsbmp * 100) % 6);

        switch (num) {
            case 0:
                ScoreHistory = BitmapFactory.decodeResource(r, R.drawable.history01, options);
                break;
            case 1:
                ScoreHistory = BitmapFactory.decodeResource(r, R.drawable.history02, options);
                break;
            case 2:
                ScoreHistory = BitmapFactory.decodeResource(r, R.drawable.history03, options);
                break;
            case 3:
                ScoreHistory = BitmapFactory.decodeResource(r, R.drawable.history04, options);
                break;
            case 4:
                ScoreHistory = BitmapFactory.decodeResource(r, R.drawable.history05, options);
                break;
            case 5:
                ScoreHistory = BitmapFactory.decodeResource(r, R.drawable.history06, options);
                break;

        }

    }

    public void ReleaseHistorybmp() {
        ScoreHistory.recycle();
    }

    public void InitGameingMusic() {
        int num = 0;
        double RGmusic;
        RGmusic = Math.random();
        num = (int) ((RGmusic * 100) % 2);
        if (num == 0)
            gameingmusic = MediaPlayer.create(this.noactivity, R.raw.gameing01);
        else
            gameingmusic = MediaPlayer.create(this.noactivity, R.raw.gameing02);

        gameingmusic.start();
        gameingmusic.setLooping(true);
        timeupmusic = MediaPlayer.create(this.noactivity, R.raw.timesup);
        timeupendmusic = MediaPlayer.create(this.noactivity, R.raw.timesup02);

        //Chviewmusic = MediaPlayer.create(this.noactivity, R.raw.changeview);
    }

    public void ReleaseGameingMusic() {
        if (gameingmusic != null) {
            ///gameingmusic.stop();
            gameingmusic.release();
            gameingmusic = null;
            timeupmusic.release();
            timeupmusic = null;
            timeupendmusic.release();
            timeupendmusic = null;
        }

		/*if(Chviewmusic!=null)
        {
			Chviewmusic.release();
			Chviewmusic=null;
		}*/
    }

    private void InitmapStoreCoordinate() {
        mapStoreCoordinate[0] = 565;
        mapStoreCoordinate[1] = 600;

        mapStoreCoordinate[2] = 540;
        mapStoreCoordinate[3] = 570;

        mapStoreCoordinate[4] = 485;
        mapStoreCoordinate[5] = 520;

        mapStoreCoordinate[6] = 430;
        mapStoreCoordinate[7] = 465;

        mapStoreCoordinate[8] = 405;
        mapStoreCoordinate[9] = 435;

        mapStoreCoordinate[10] = 300;
        mapStoreCoordinate[11] = 330;

        mapStoreCoordinate[12] = 220;
        mapStoreCoordinate[13] = 250;

        mapStoreCoordinate[14] = 140;
        mapStoreCoordinate[15] = 170;

        mapStoreCoordinate[16] = 265;
        mapStoreCoordinate[17] = 130;

        mapStoreCoordinate[18] = 295;
        mapStoreCoordinate[19] = 165;

        mapStoreCoordinate[20] = 380;
        mapStoreCoordinate[21] = 245;

        mapStoreCoordinate[22] = 485;
        mapStoreCoordinate[23] = 350;

        mapStoreCoordinate[24] = 510;
        mapStoreCoordinate[25] = 380;

        mapStoreCoordinate[26] = 590;
        mapStoreCoordinate[27] = 450;

        mapStoreCoordinate[28] = 640;
        mapStoreCoordinate[29] = 500;

        mapStoreCoordinate[30] = 715;
        mapStoreCoordinate[31] = 580;

        mapStoreCoordinate[32] = 820;
        mapStoreCoordinate[33] = 500;

        mapStoreCoordinate[34] = 630;
        mapStoreCoordinate[35] = 310;

        mapStoreCoordinate[36] = 580;
        mapStoreCoordinate[37] = 260;

        mapStoreCoordinate[38] = 425;
        mapStoreCoordinate[39] = 100;
    }

    public void InitNmView() {

        InitIsTouchStore(); //初始是否進過攤位
        mapX = -420;    //初始地圖左上x座標
        mapY = -420;    //初始地圖左上y座標
        mapx = -420;
        mapy = -420;

        timesec = 0;
        rg = 0;
        tumove = 0;

        isPlayBestScore = false;
        isStartGame = false;
        isPlayComplete = false;

    }

    private void InitIsTouchStore() {
        for (int i = 0; i < isTouchStore.length; i++)
            isTouchStore[i] = 0;
    }

    public void StartMoonTime() {
        ct = new TimeThread(this);   //啟動計時器
        ct.start();
    }

    public int getH() {
        return maptest.getHeight();
    }

    public int getW() {
        return maptest.getWidth();
    }

    public int getTimeSec() {
        return this.timesec;
    }

    public int getPlayerTotalMoney() {
        return playertotalmoney;
    }

    public void setPlayerTotalMoney(int money) {
        this.playertotalmoney = money;
    }

    public void setPlayerScore(int score) {
        this.playerscore = score;
    }

    public int getPlayerScore() {
        return this.playerscore;
    }

    public void setDuringTime(int sec) {
        this.playerspendtime = sec;
    }

    private void setisTouchStore(int x, int y) {
        for (int i = 0; i < mapStoreCoordinate.length; i = i + 2) {
            if (x == mapStoreCoordinate[i] && y == mapStoreCoordinate[i + 1]) {
                isTouchStore[i / 2] = 1;
            }
        }
    }

    private boolean getisTouchStore(int x, int y) {
        for (int i = 0; i < mapStoreCoordinate.length; i = i + 2) {
            if (x == mapStoreCoordinate[i] && y == mapStoreCoordinate[i + 1]) {
                if (isTouchStore[i / 2] == 1)
                    return true;
            }
        }

        return false;
    }

    private void MapMoveRange() {
        if (mapX > 0 && mapY > 0) {
            mapX = 0;
            mapY = 0;
        }
        if (mapX > 0) {
            mapX = 0;
        }
        if (mapY > 0) {
            mapY = 0;
        }
        if (mapX < (appViewWidth - maptest.getWidth()) && mapY < (appViewHeight - maptest.getHeight())) {
            mapX = (appViewWidth - maptest.getWidth());
            mapY = (appViewHeight - maptest.getHeight());
        }
        if (mapX < (appViewWidth - maptest.getWidth())) {
            mapX = (appViewWidth - maptest.getWidth());
        }
        if (mapY < (appViewHeight - maptest.getHeight())) {
            mapY = (appViewHeight - maptest.getHeight());
        }
    }

    private void TransCoordinate(int ux, int uy) {
        UsermapX = ux - mapX;
        UsermapY = uy - mapY;
        TouchRange(UsermapX, UsermapY);
    }

    private void TouchRange(int tx, int ty) {
        int range = 30;   //touch範圍大小
        touchfourpoint[0] = tx - range;   //左上x
        touchfourpoint[2] = tx + range;      //右上x
        touchfourpoint[4] = tx - range;   //左下x
        touchfourpoint[6] = tx + range;   //右下x

        touchfourpoint[1] = ty - range;  //左上y
        touchfourpoint[3] = ty - range;  //右上y
        touchfourpoint[5] = ty + range;  //左下y
        touchfourpoint[7] = ty + range;  //右下y
    }

    private boolean IsCloseStore(int maparray[], int[] Tarray, int tx, int ty)  //mapStoreCoordinate[], toucharray,touchx,touchy
    {

        int[][] inRange = new int[5][2];  //存儲玩家所選到的店家座標在

        int inrangenum = 0;

        for (int i = 0; i < maparray.length; i = i + 2) {
            if (Tarray[0] <= maparray[i] && maparray[i] <= Tarray[2]) {
                if (Tarray[1] <= maparray[i + 1] && maparray[i + 1] <= Tarray[5]) {
                    inRange[inrangenum][0] = maparray[i];
                    inRange[inrangenum][1] = maparray[i + 1];
                    inrangenum++;

                }
            }
        }

        if (inrangenum == 1) {
            if (getisTouchStore(inRange[0][0], inRange[0][1])) //如果已經去過的店家，則回傳false
                return false;
            else {
                goods.coodinateTogoods(inRange[0][0], inRange[0][1]);
                setisTouchStore(inRange[0][0], inRange[0][1]);
                //goods.RandGoodsPrice(this.playertotalmoney);
                return true;
            }
        } else if (inrangenum > 1) {
            int min = (inRange[0][0] - tx) * (inRange[0][0] - tx) + (inRange[0][1] - ty) * (inRange[0][1] - ty);
            int x2 = inRange[0][0], y2 = inRange[0][1];

            for (int i = 0; i < inRange.length; i++) {
                if ((inRange[i][0] - tx) * (inRange[i][0] - tx) + (inRange[i][1] - ty) * (inRange[i][1] - ty) < min) {
                    min = (inRange[i][0] - tx) * (inRange[i][0] - tx) + (inRange[i][1] - ty) * (inRange[i][1] - ty);
                    x2 = inRange[i][0];
                    y2 = inRange[i][1];
                }
            }

            if (getisTouchStore(x2, y2))
                return false;
            else {
                goods.coodinateTogoods(x2, y2);
                setisTouchStore(x2, y2);
                return true;
            }
        } else
            return false;
    }
	
	/*int scalenum=0;
	
	private void PlayEffect(Bitmap bmp , Canvas canvas)
	{
		float sx = 1f,sy = 1f;
		
		if(scalenum >=0 && scalenum<2)
		{
			sx=1.5f;
			sy=1.5f;
			scalenum++;
		}
		else if(scalenum>=2 && scalenum<4)
		{

			sx=2.0f;
			sy=2.0f;
			scalenum++;
		}
		else if(scalenum>=4 && scalenum<6)
		{

			sx=2.5f;
			sy=2.5f;
			scalenum++;
		}
		else if(scalenum>=6)
		{

			sx=3.0f;
			sy=3.0f;
			scalenum++;
		}
		
		canvas.save();
		canvas.scale(sx,sy,(this.appViewWidth/2-bmp.getWidth()/2),(this.appViewHeight/2-bmp.getHeight()/2));
		canvas.drawBitmap(bmp,(this.appViewWidth/2-bmp.getWidth()/2),(this.appViewHeight/2-bmp.getHeight()/2), paint);
		canvas.restore();
		
		if(scalenum>10)
		{
			scalenum=0;
			isClose=false;
			this.noactivity.ncHandler.sendEmptyMessage(2);
		}
		
	}*/

    public void startRePaintMoon() {
        this.isRePaintMoon = true;
    }

    int cbonus = 1;

    private void PaintTimeMoon(Canvas canvas)  //月亮計時器
    {

        if (isRePaintMoon) {
            transform.setTranslate(this.appViewWidth / 2 - timemoon.getWidth() / 2, 0 - timemoon.getHeight() / 2);
            transform.preRotate(timesec, timemoon.getWidth() / 2, timemoon.getHeight() / 2);
            isRePaintMoon = false;

        }
        if (this.playertotalmoney <= 0)   //玩家全部買完
        {
            if (isStartGame)   //遊戲結束後，只執行一次的地方
            {
                timeupmusic.pause();
                noactivity.gaview.ReleaseGameViewBmp();
                noactivity.mainview.InitBitmap();
                int bonus = 0;
                bonus = (180 - timesec) * 4;
                cbonus = bonus;
                this.playerscore = this.playerscore + bonus;

                noactivity.ncHandler.sendEmptyMessage(HeavyWalletParameter.SAVE_BESTSCORE);   //呼叫紀綠最佳分數

                timesec = 179;
                ct.setFlag(false);
                isStartGame = false;

            }


            PaintComplete(canvas);

        } else if (timesec >= 180)    //時間倒數結束
        {
            if (isStartGame) {
                noactivity.gaview.ReleaseGameViewBmp();
                noactivity.mainview.InitBitmap();
                noactivity.ncHandler.sendEmptyMessage(HeavyWalletParameter.SAVE_BESTSCORE);   //呼叫紀綠最佳分數
                timeupendmusic.start();

                timesec = 180;
                ct.setFlag(false);
                isStartGame = false;
            }


            PaintTimesUp(canvas);


        }
        if (timesec % 5 == 0 || timesec < 5) {
            transform.setTranslate(this.appViewWidth / 2 - timemoon.getWidth() / 2, 0 - timemoon.getHeight() / 2);
            transform.preRotate(timesec, timemoon.getWidth() / 2, timemoon.getHeight() / 2);

        }

        canvas.drawBitmap(timemoon, transform, paint);
    }

    private void PaintScoreNumber(Canvas canvas, int num) {
        if (num < 0) num = 0;
        int sy = 2, sx = 3, sp = 23; //sp=貼圖起始位置座標

        if (num < 10) sp = 26;
        else if (num >= 10 && num < 100) sp = 25;
        else if (num >= 100 && num < 1000) sp = 24;
        else sp = 23;

        String tmpStr = "" + Math.abs(num);
        for (int i = 0; i < tmpStr.length(); i++) {
            int nm = Integer.parseInt(tmpStr.charAt(i) + "");
            if (nm > 0 && nm < 10)                          //設定顯示的數字圖片
                scorebmp.set(125 * (nm - 1), 0, 125 * nm, 125);
            else
                scorebmp.set(1125, 0, 1250, 125);

            scorenumber.set(this.appViewWidth * (i + sp) / 28 - sx * i, this.appViewHeight * 0,
                    this.appViewWidth * (i + (sp + 1)) / 28 - sx * i, this.appViewHeight * 1 / 12 - sy);

            canvas.drawBitmap(Scorenumber, scorebmp, scorenumber, paint);
        }

    }

    private void PaintBestScoreNumber(Canvas canvas, int num) {
        if (num < 0) num = 0;

        String tmpStr = "" + Math.abs(num);
        for (int i = 0; i < tmpStr.length(); i++) {
            int nm = Integer.parseInt(tmpStr.charAt(i) + "");
            if (nm > 0 && nm < 10)                          //設定顯示的數字圖片
                scorebmp.set(125 * (nm - 1), 0, 125 * nm, 125);
            else
                scorebmp.set(1125, 0, 1250, 125);

            scorenumber.set(this.appViewWidth * (i * 2 + 5) / 16, this.appViewHeight * 5 / 24,
                    this.appViewWidth * (i * 2 + 7) / 16, this.appViewHeight * 13 / 24);

            canvas.drawBitmap(Scorenumber, scorebmp, scorenumber, paint);
        }

    }

    private void PaintWanGo(Canvas canvas) {
        int mx = 15, my = 70;
        for (int i = 0; i < isTouchStore.length; i++) {
            if (isTouchStore[i] == 1)
                canvas.drawBitmap(wango, mapX + mapStoreCoordinate[i * 2] - mx, mapY + mapStoreCoordinate[i * 2 + 1] - my, null);
            //x1=mapX+mapStoreCoordinate[i*2]-mx;
            //y1=mapY+mapStoreCoordinate[i*2+1]-my;
        }
    }

    int rg;

    private void PaintReadyGo(Canvas canvas) {
        if (rg >= 0 && rg < 20) {
            canvas.drawBitmap(ready, this.appViewWidth / 2 - ready.getWidth() / 2, this.appViewHeight / 2 - ready.getHeight() / 2, null);
            rg++;
        } else if (rg >= 20 && rg < 40) {
            canvas.drawBitmap(go, this.appViewWidth / 2 - go.getWidth() / 2, this.appViewHeight / 2 - go.getHeight() / 2, null);
            rg++;
        }

        if (rg >= 40) {
            isStartGame = true;
            StartMoonTime();
            ReleaseBitmapStart();
            InitGameingMusic();
            rg = -1;
        }

    }

    int tumove, tunum;

    private void PaintTimesUp(Canvas canvas) {

        if (tumove == 0)
            tumove = 5;
        else if (tumove == 5)
            tumove = -5;
        else if (tumove == -5) {
            tumove = 0;
            tunum++;
            if (tunum > 10) {
                InitHistorybmp();
                this.isPlayBestScore = true;
                tumove = -1;
                tunum = 0;

            }

        }
        canvas.drawBitmap(timesup, this.appViewWidth / 2 - timesup.getWidth() / 2, this.appViewHeight / 2 - timesup.getHeight() / 2 + tumove, null);
    }

    private void PaintComplete(Canvas canvas) {

        if (tumove == 0)
            tumove = 5;
        else if (tumove == 5)
            tumove = -5;
        else if (tumove == -5) {
            tumove = 0;
            tunum++;
            if (tunum > 10) {
                InitHistorybmp();
                //this.isPlayBestScore=true;
                this.playerscore = this.playerscore - cbonus;
                this.isPlayComplete = true;
                tumove = -1;
                tunum = 0;

            }

        }
        canvas.drawBitmap(complete, this.appViewWidth / 2 - complete.getWidth() / 2, this.appViewHeight / 2 - complete.getHeight() / 2 + tumove, null);
    }

    //int bsbmove;
    private void PaintBestScoreBan(Canvas canvas) {
        if (isPlayBestScore) {
            bestSocreban.set(0, 0, this.appViewWidth, this.appViewHeight);
            canvas.drawBitmap(ScoreHistory, null, bestSocreban, paint);
            PaintBestScoreNumber(canvas, this.playerscore);

        }
    }

    private void PaintBestScoreBanBonus(Canvas canvas) {

        if (isPlayComplete) {
            bestSocreban.set(0, 0, this.appViewWidth, this.appViewHeight);
            canvas.drawBitmap(ScoreHistory, null, bestSocreban, paint);

            displaybestbonus.set(this.appViewWidth * 9 / 15, this.appViewHeight * 2 / 24,
                    this.appViewWidth * 14 / 15, this.appViewHeight * 6 / 24);
            canvas.drawBitmap(BestBonus, null, displaybestbonus, null);

            PaintBestScoreNumber(canvas, this.playerscore);

            if (cbonus > 0) {
                this.playerscore = this.playerscore + 1;
                cbonus--;
            }//noactivity.ncHandler.sendEmptyMessage(4);   //呼叫紀綠最佳分數
        }
    }

    int pm, pm2;
    int x1, y1, x2, y2;

    public void doDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        MapMoveRange();
        canvas.drawBitmap(maptest, mapX, mapY, paint);
        PaintReadyGo(canvas);
        PaintWanGo(canvas);
        if (this.isStartGame)
            PaintScoreNumber(canvas, this.playerscore);
        PaintTimeMoon(canvas);
        PaintBestScoreBan(canvas);
        PaintBestScoreBanBonus(canvas);

        if (isClose && isStartGame) {
            //Chviewmusic.start();
            isClose = false;
            this.noactivity.ncHandler.sendEmptyMessage(HeavyWalletParameter.CHANGETO_GAMEVIEW);
        }
		
			
			 
		/*canvas.save();
		canvas.scale(3.0f,3.0f,20,20);
	
		canvas.drawBitmap(effecttest,20,20, paint);
		canvas.restore();
		*/

        //pm=this.touchX;
        //pm2=this.touchY;
        //String str1=String.valueOf(x1);
        //String str2=String.valueOf(y1);
        //String str3=String.valueOf(pm);
        //String str4=String.valueOf(pm2);
        //String str5=String.valueOf(mapX);
        //String str6=String.valueOf(mapY);


        //canvas.drawText(str1, 0, 30, paint);
        //canvas.drawText(str2, 0, 50, paint);
        //canvas.drawText(str3, 0, 70, paint);
        //canvas.drawText(str4, 0, 90, paint);
        //canvas.drawText(str5, 0, 110, paint);
        //canvas.drawText(str6, 0, 130, paint);

    }

    private void MapMoveFunction(int mx, int my)    //移動地圖函式
    {
        if (isStartGame) {
            if (mapX <= 0 && mapX >= (appViewWidth - maptest.getWidth())) {
                mapX = mx - touchX + mapx;
                if ((mx - touchX) * (mx - touchX) > 100) isMapMoveing = true;
                else isMapMoveing = false;
            }
            if (mapy <= 0 && mapy >= (appViewHeight - maptest.getHeight())) {
                mapY = my - touchY + mapy;
                if ((my - touchY) * (my - touchY) > 100) isMapMoveing = true;
                else isMapMoveing = false;
            }

        }
    }

    int touchX;
    int touchY;
    int mapx = -1120, mapy = -1100;    //初始地圖位置
    boolean isClose = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        //int x =(int)event.getX();
        //int y =(int)event.getY();


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                touchX = (int) event.getX();
                touchY = (int) event.getY();

                //pm=0;
                break;
            case MotionEvent.ACTION_MOVE:
                int mX, mY;

                mX = (int) event.getX();
                mY = (int) event.getY();
                //x1=mX;
                //y1=mY;

                MapMoveFunction(mX, mY);

                break;
            case MotionEvent.ACTION_UP:
                if (this.isStartGame) {
                    mapx = mapX;
                    mapy = mapY;
                    if (this.isMapMoveing == false) {
                        TransCoordinate(touchX, touchY);
                        if (this.isPlayBestScore == false)
                            isClose = IsCloseStore(mapStoreCoordinate, touchfourpoint, UsermapX, UsermapY);  //判斷有沒有按到店家
                    }
                } else if (this.isPlayBestScore || cbonus <= 0) {
                    ReleaseBitmap();
                    ReleaseGameingMusic();
                    noactivity.ncHandler.sendEmptyMessage(HeavyWalletParameter.CHANGETO_GAMEMAINVIEW);
                    ReleaseHistorybmp();
                }
                break;
        }
        return true;
    }


    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        if (!dt.isAlive()) {    //若DrawThread沒有啟動，就啟動這個執行緒
            dt.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

        //dt=null;
        //dt.setFlag(false);


    }

}
