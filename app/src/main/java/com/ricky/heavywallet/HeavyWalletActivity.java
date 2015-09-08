package com.ricky.heavywallet;

import android.app.Activity;
import android.app.Service;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.ricky.heavywallet.util.HeavyWalletParameter;
import com.ricky.heavywallet.util.goods;
import com.ricky.heavywallet.view.GameMainView;
import com.ricky.heavywallet.view.GameShoppingView;
import com.ricky.heavywallet.view.HowPlayView;
import com.ricky.heavywallet.view.NightMarketView;


public class HeavyWalletActivity extends Activity implements Runnable {

    private DisplayMetrics mDisplayMetrics;
    private static int DeviceWidth;
    private static int DeviceHeight;

    HeavyWalletActivity mAct;

    public NightMarketView nmview;
    public GameShoppingView gaview;
    public GameMainView mainview;
    public HowPlayView howplayview;

    Vibrator myVibrator;

    SharedPreferences setting;

    private Handler delay = new Handler();

    private int startgamemoney;
    private int startscore = 0;
    private boolean isStartDelay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_heavy_wallet);
        setFullScreen();

        setting = getSharedPreferences(HeavyWalletParameter.SCOREDATA, 0);

        delay.removeCallbacks(updateDelay);
        delay.postDelayed(updateDelay, 1000);

        Resources r = getResources();
        goods.InitGoodsText(r);
        goods.InitNumberbmp(r);

        mainview = new GameMainView(this, this, DeviceWidth, DeviceHeight);
        nmview = new NightMarketView(this, this, DeviceWidth, DeviceHeight);
        gaview = new GameShoppingView(this, this, DeviceWidth, DeviceHeight);
        howplayview = new HowPlayView(this, this, DeviceWidth, DeviceHeight);

        ncHandler.sendEmptyMessage(HeavyWalletParameter.CHANGETO_GAMEMAINVIEW);

    }

    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

//        WindowManager windowManager = getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        DeviceWidth = display.getWidth();
//        DeviceHeight = display.getHeight();

        mDisplayMetrics = getResources().getDisplayMetrics();
        DeviceWidth = mDisplayMetrics.widthPixels;
        DeviceHeight = mDisplayMetrics.heightPixels;


    }

    public Handler ncHandler = new Handler() {
        public void handleMessage(Message msg) {
            int money, sec, score = 0;
            switch (msg.what) {
                case HeavyWalletParameter.CHANGETO_GAMEMAINVIEW:

                    //nmview.ReleaseBitmap();
                    //nmview.ReleaseGameingMusic();

                    mainview.setBestScore(getBestScore());
                    mainview.InitBitmap();
                    mainview.InitMainMusic();
                    setContentView(mainview);
                    InitializeGame();
                    break;
                case HeavyWalletParameter.CHANGETO_NIGHTMARKETVIEW:
                    //mainmusic.stop();
                    //mainmusic.release();

                    sec = nmview.getTimeSec();
                    goods.setEndsec(sec);
                    //nmview.setDuringTime(goods.getGameDuringTime());

                    startscore = startscore + gaview.getPlayerScore();
                    nmview.setPlayerScore(startscore);
                    nmview.setDuringTime(goods.getGameDuringTime());
                    setContentView(nmview);
                    nmview.startRePaintMoon();
                    money = gaview.getPlayerNowMoney();
                    nmview.setPlayerTotalMoney(money);

                    gaview.InitGameViewing();

                    break;
                case HeavyWalletParameter.CHANGETO_GAMEVIEW:
                    //Chviewmusic.start();
                    setContentView(gaview);

                    money = nmview.getPlayerTotalMoney();
                    sec = nmview.getTimeSec();
                    goods.setStartsec(sec);

                    gaview.setPlayNowMoney(money);

                    break;
                case HeavyWalletParameter.TRIGGER_DELAYHANDLER:
                    //Chviewmusic.release();
                    if (isStartDelay == false)
                        isStartDelay = true;
                    else
                        isStartDelay = false;

                    break;
                case HeavyWalletParameter.SAVE_BESTSCORE:
                    //Chviewmusic.release();
                    //gaview.ReleaseGameViewBmp();
                    score = setting.getInt(HeavyWalletParameter.BESTSCORE, 0);
                    if (nmview.getPlayerScore() > score) {
                        mainview.setIsPlayBestScoreEffect(true);
                        setting.edit()
                                .putInt(HeavyWalletParameter.BESTSCORE, nmview.getPlayerScore())
                                .commit();
                    }

                    break;
                case 5:
                    myVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
                    myVibrator.vibrate(500);
                    break;
                case HeavyWalletParameter.CHANGETO_GAMERULE:
                    howplayview.InitHowPlayView();
                    setContentView(howplayview);
                    break;

                default:
                    break;
            }
        }
    };

    private int[] Randgoodsprice() {
        int goods20[] = new int[20];
        int randarray[] = new int[20];
        int rnum = 20;
        //int PlayerMoney=0;
        //int RandRange=101;
        //int minprice=800;
        double Rprice, Rarray;

        for (int i = 0; i < randarray.length; i++)
            randarray[i] = i;


        for (int i = 0; i < goods20.length; i++) {
            Rprice = Math.random();
            Rarray = Math.random();
            Rarray = Rarray * 1000;

            if (i == 0)
                goods20[randarray[(int) (Rarray % rnum)]] = (int) ((Rprice * 1000000) % 401) + 800;
            else if (i == 1)
                goods20[randarray[(int) (Rarray % rnum)]] = (int) ((Rprice * 1000000) % 201) + 400;
            else if (i == 2)
                goods20[randarray[(int) (Rarray % rnum)]] = (int) ((Rprice * 1000000) % 101) + 300;
            else if (i == 3)
                goods20[randarray[(int) (Rarray % rnum)]] = (int) ((Rprice * 1000000) % 101) + 200;
            else if (i >= 4 && i < 10)
                goods20[randarray[(int) (Rarray % rnum)]] = (int) ((Rprice * 1000000) % 201) + 100;
            else if (i >= 10 && i < 15)
                goods20[randarray[(int) (Rarray % rnum)]] = (int) ((Rprice * 1000000) % 101) + 50;
            else if (i >= 15 && i < goods20.length)
                goods20[randarray[(int) (Rarray % rnum)]] = (int) ((Rprice * 1000000) % 50) + 10;

            randarray[(int) (Rarray % rnum)] = randarray[rnum - 1];
            rnum--;

        }

		/*Rprice = Math.random();
        goods20[0]=(int)((Rprice*1000000)%401)+800;

		Rprice = Math.random();
		goods20[1]=(int)((Rprice*1000000)%201)+400;

		Rprice = Math.random();
		goods20[2]=(int)((Rprice*1000000)%101)+300;

		Rprice = Math.random();
		goods20[3]=(int)((Rprice*1000000)%101)+200;

		for(int i=4;i<10;i++)
		{
			Rprice = Math.random();
			goods20[i]=(int)((Rprice*1000000)%201)+100;
		}

		for(int i=10;i<15;i++)
		{
			Rprice = Math.random();
			goods20[i]=(int)((Rprice*1000000)%101)+50;
		}

		for(int i=15;i<20;i++)
		{
			Rprice = Math.random();
			goods20[i]=(int)((Rprice*1000000)%50)+10;
		}*/

        for (int i = 0; i < goods20.length; i++)
            this.startgamemoney = this.startgamemoney + goods20[i];

        return goods20;

    }

    public void InitializeGame() {
        nmview.InitBitmapStart();

        startscore = 0;

        gaview.setPlayNowMoney(0);
        //nmview.setPlayerTotalMoney(0);
        this.startgamemoney = 0;

        //gaview.setGoodsnum(0);
        goods.SetGoodsnum(0);
        gaview.InitGameView();
        gaview.setGoodsPrice(Randgoodsprice());
        gaview.setMoneyToCoin(this.startgamemoney);
        gaview.setPlayNowMoney(this.startgamemoney);
        //gaview.setPlayerScore(0);

        //nmview.setPlayerTotalMoney(this.startgamemoney);
        nmview.setPlayerScore(0);
        nmview.InitNmView();

    }

    private int getBestScore() {
        int score = 0;
        score = setting.getInt(HeavyWalletParameter.BESTSCORE, 0);
        return score;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mainview.run();
        howplayview.run();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //mainmusic.release();
        nmview.ReleaseGameingMusic();
        mainview.ReleaseMainMusic();
        howplayview.ReleaseHowPlayViewbmp();
        //gaview.ReleaseGameViewBmp();
        goods.ReleaseGoodsText();
        goods.ReleaseNumberbmp();

        nmview.dt.setFlag(false);
        gaview.gt.setFlag(false);
        //nmview.ct.setFlag(false);
        nmview = null;
        gaview = null;
        mainview = null;
        howplayview = null;

        delay.removeCallbacks(updateDelay);
    }

    int sec = 0;
    private Runnable updateDelay = new Runnable() {

        public void run() {
            if (isStartDelay) {
                sec++;
                if (sec > 2) {
                    isStartDelay = false;
                    sec = 0;
                    ncHandler.sendEmptyMessage(HeavyWalletParameter.CHANGETO_NIGHTMARKETVIEW);

                }
            }

            delay.postDelayed(this, 1000);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_heavy_wallet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run() {

        try {

            for (int i = 0; i < 2; i++) {
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
