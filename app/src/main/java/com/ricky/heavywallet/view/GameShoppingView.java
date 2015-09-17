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
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ricky.heavywallet.HeavyWalletActivity;
import com.ricky.heavywallet.R;
import com.ricky.heavywallet.thread.GameThread;
import com.ricky.heavywallet.util.HeavyWalletParameter;
import com.ricky.heavywallet.util.goods;

public class GameShoppingView extends SurfaceView implements SurfaceHolder.Callback {

    HeavyWalletActivity noactivity;
    MediaPlayer paymusic;
    MediaPlayer nomoneymusic;

    public GameThread gt;

    Paint paint = new Paint();
    RectF onethounsand = new RectF();
    RectF fivehundred = new RectF();
    RectF onehundred = new RectF();
    RectF fifty = new RectF();
    RectF ten = new RectF();
    RectF five = new RectF();
    RectF one = new RectF();
    Rect movemoney = new Rect();   //�즲����Ϯ�
    Rect payplace = new Rect();    //�I��϶�
    Rect yesplace = new Rect();    //�T�w��
    Rect background = new Rect();  //��C���I����
    Rect moneybmp = new Rect();
    Rect foodbmp = new Rect();
    Rect scorebmp = new Rect();
    Rect specialnumbmp = new Rect();
    RectF displaymoney = new RectF();
    RectF Moneynumber = new RectF();   // �]�w���B�ƶq����ܮئ�m
    RectF MoneyIcon = new RectF();
    RectF displaygoods = new RectF();  //�]�w������ܮئ�m
    RectF displaygoodsText = new RectF();
    RectF displayscoreban = new RectF();
    RectF scorenumber = new RectF();
    RectF displayspecialnum = new RectF(); //�]�w�S��ƶq��ܮئ�m
    RectF displaysbonus = new RectF(); //�]�wbonus��ܮئ�m

    RectF cancelplaceS = new RectF();   //���b���_�l��m
    RectF cancelplaceE = new RectF();   //���b��������m


    private static final int Onethousand = 0;
    private static final int Fivehundred = 1;
    private static final int Onehundred = 2;
    private static final int Fifty = 3;
    private static final int Ten = 4;
    private static final int Five = 5;
    private static final int One = 6;


    private Bitmap money1000;
    private Bitmap money500;
    private Bitmap money100;
    private Bitmap money50;
    private Bitmap money10;
    private Bitmap money5;
    private Bitmap money1;

    private Bitmap moneynum;
    //private Bitmap GoodsBmp;   //��o���a�ҳc�檺���~�Ϥ�
    private Bitmap moneyicon;
    private Bitmap GoodsImage; //20�i�����Ϥ�
    private Bitmap GoodsText;  //�����W��
    private Bitmap Scoreban;
    private Bitmap Scorenumber;
    private Bitmap Specialnumber;
    private Bitmap Bonus;
    private Bitmap Minus;


    private Bitmap[] paymoneybmp = new Bitmap[15];
    private Bitmap[] sMoneybmp = new Bitmap[7];
    private Bitmap[] backgroundbmp = new Bitmap[3];
    //private Bitmap[] goodsimage = new Bitmap[20];


    private int appViewWidth;
    private int appViewHeight;
    private int selectmoney;     //���a�ҿ�쪺�����B
    private int selectmoneytopay;//���a�ҥI����B
    private int mX, mY;           //���a�즲����y��
    private int PlayerNowMoney;  //���a�ثe�Ҿ֦�������
    //private int GoodsPrice;    //��o���a�ҳc�檺���~���
    private int PlayerPayMoney; //���a�ҥI�X�����B
    private int PlayerScore; //���a�o��
    private int PlayerPayNum;  //���a�ҥI�������ƶq
    private int PlayerTotalSocre;
    private int BonusScore;
    private int MinusScore;
    private int MinusAllScore;
    //private static int goodsnum=0; //���~���}�C�p��
    private int specialnum;  //���~�ƶq
    public boolean isPlayScoreban = false;
    private boolean isCanceling = false;


    private int[] money = new int[7]; //���a�ثe�Ҿ֦�������Ӽ�
    private int[] firstmoney = new int[7];  //����a�@�i�C���e���ɡA�@�}�l������Ӽ�
    private int[] givemoney = new int[7];   //�I��ҧ�����Ӽ�
    private int[] paymoney = new int[7];    //����a�I�F����
    private int[] goods20price = new int[20];  //�Ҧ�20�Ӱӫ~�����
    private int[] displaypaymoneycoordinate = new int[7];  //�x�s��ܪ��a�ҥI���B���K�Ϯy��
    private int[] displaygivemoneycoordinate = new int[5]; //�x�s��ܪ��a�ҧ��K�Ϯy��
    int pm;

    public GameShoppingView(Activity activity, int appViewWidth, int appViewHeight) {
        super(activity);
        getHolder().addCallback(this);

        this.noactivity = (HeavyWalletActivity) activity;
        this.appViewHeight = appViewHeight;
        this.appViewWidth = appViewWidth;

        InitRectF();
        InitRect();
        Initdisplaymoneycoordinate();
        goods.SetGoodsnum(0);
        paint.setColor(Color.GREEN);
        paint.setTextSize(20);

        gt = new GameThread(this, getHolder());
        // TODO Auto-generated constructor stub
    }

    public void InitGameView() {
        gn = 0;
        PlayerScore = 0;
    }

    public void InitGameViewing() {
        isPlayScoreban = false;  //��l�o���O����m�M���
        scoremove = -14;
        PlayerPayNum = 0;
        PlayerPayMoney = 0;
        BonusScore = 0;
        MinusAllScore = 0;
        MinusScore = 0;

        for (int i = 0; i < givemoney.length; i++)
            givemoney[i] = 0;
        for (int i = 0; i < paymoney.length; i++)
            paymoney[i] = 0;

    }

    public int getPlayerNowMoney() {
        return this.PlayerNowMoney;
    }

    public void setPlayNowMoney(int money) {
        this.PlayerNowMoney = money;
    }

    public void setMoneyToCoin(int money)     //�N���B�ഫ���U����Ӽ�
    {
        MoneyToCoin(money);
        for (int i = 0; i < firstmoney.length; i++)
            firstmoney[i] = this.money[i];
    }

    public void setGoodsPrice(int[] price) {
        for (int i = 0; i < price.length; i++) {
            goods20price[i] = price[i];
        }
    }

    public void setPlayerScore(int score) {
        this.PlayerScore = score;
    }

    public int getPlayerScore() {
        return this.PlayerScore;
    }

    public void InitGameViewMusic() {
        paymusic = MediaPlayer.create(this.noactivity, R.raw.pay);
        nomoneymusic = MediaPlayer.create(this.noactivity, R.raw.nomoney);
    }

    public void ReleaseGameViewMusic() {
        if (paymusic != null)
            paymusic.release();
        nomoneymusic.release();
    }

    public void InitGameViewBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        Resources r = getResources();
        money1000 = BitmapFactory.decodeResource(r, R.drawable.m1000);
        money500 = BitmapFactory.decodeResource(r, R.drawable.m500);
        money100 = BitmapFactory.decodeResource(r, R.drawable.m100);
        money50 = BitmapFactory.decodeResource(r, R.drawable.m50);
        money10 = BitmapFactory.decodeResource(r, R.drawable.m10);
        money5 = BitmapFactory.decodeResource(r, R.drawable.m5);
        money1 = BitmapFactory.decodeResource(r, R.drawable.m1);

        moneyicon = BitmapFactory.decodeResource(r, R.drawable.moneyicon);
        //moneynum = BitmapFactory.decodeResource(r, R.drawable.moneynumber);
        GoodsImage = BitmapFactory.decodeResource(r, R.drawable.goodsbmp, options);
        Scoreban = BitmapFactory.decodeResource(r, R.drawable.scoreban);
        //Scorenumber = BitmapFactory.decodeResource(r, R.drawable.scorenumber);
        Specialnumber = BitmapFactory.decodeResource(r, R.drawable.specialnumber);
        Bonus = BitmapFactory.decodeResource(r, R.drawable.bonus);
        Minus = BitmapFactory.decodeResource(r, R.drawable.minus);
        Scorenumber = goods.getScoreNumber();
        moneynum = goods.getMoneyNumber();


        sMoneybmp[Onethousand] = BitmapFactory.decodeResource(r, R.drawable.sm1000);
        sMoneybmp[Fivehundred] = BitmapFactory.decodeResource(r, R.drawable.sm500);
        sMoneybmp[Onehundred] = BitmapFactory.decodeResource(r, R.drawable.sm100);
        sMoneybmp[Fifty] = BitmapFactory.decodeResource(r, R.drawable.sm50);
        sMoneybmp[Ten] = BitmapFactory.decodeResource(r, R.drawable.sm10);
        sMoneybmp[Five] = BitmapFactory.decodeResource(r, R.drawable.sm5);
        sMoneybmp[One] = BitmapFactory.decodeResource(r, R.drawable.sm1);

        backgroundbmp[0] = BitmapFactory.decodeResource(r, R.drawable.b0001);
        backgroundbmp[1] = BitmapFactory.decodeResource(r, R.drawable.b0002);
        backgroundbmp[2] = BitmapFactory.decodeResource(r, R.drawable.b0003);
        InitGameViewMusic();
    }

    public void ReleaseGameViewBmp() {
        money1000.recycle();
        money500.recycle();
        money100.recycle();
        money50.recycle();
        money10.recycle();
        money5.recycle();
        money1.recycle();
        moneyicon.recycle();
        GoodsImage.recycle();
        Scoreban.recycle();
        Specialnumber.recycle();
        Bonus.recycle();
        sMoneybmp[Onethousand].recycle();
        sMoneybmp[Fivehundred].recycle();
        sMoneybmp[Onehundred].recycle();
        sMoneybmp[Fifty].recycle();
        sMoneybmp[Ten].recycle();
        sMoneybmp[Five].recycle();
        sMoneybmp[One].recycle();

        backgroundbmp[0].recycle();
        backgroundbmp[1].recycle();
        backgroundbmp[2].recycle();

        ReleaseGameViewMusic();
    }

    private void InitRectF() {
        float salceheight = 7 / 9f;
        int mx = 0, my = 5, my2 = 15, smx = 10;
        one.set(this.appViewWidth * 2 / 11 - mx, this.appViewHeight * salceheight - my2, this.appViewWidth * 3 / 11 - mx, this.appViewHeight);
        five.set(this.appViewWidth * 3 / 11 - mx, this.appViewHeight * salceheight - my2, this.appViewWidth * 4 / 11 - mx, this.appViewHeight);
        ten.set(this.appViewWidth * 4 / 11 - mx, this.appViewHeight * salceheight - my2, this.appViewWidth * 5 / 11 - mx, this.appViewHeight);
        fifty.set(this.appViewWidth * 5 / 11 - mx, this.appViewHeight * salceheight - my, this.appViewWidth * 6 / 11 - mx, this.appViewHeight);
        onehundred.set(this.appViewWidth * 6 / 11 - mx, this.appViewHeight * salceheight - my2, this.appViewWidth * 7 / 11 - mx, this.appViewHeight);
        fivehundred.set(this.appViewWidth * 7 / 11 - mx, this.appViewHeight * salceheight - my2, this.appViewWidth * 8 / 11 - mx, this.appViewHeight);
        onethounsand.set(this.appViewWidth * 8 / 11 - mx, this.appViewHeight * salceheight - my2, this.appViewWidth * 9 / 11 - mx, this.appViewHeight);

        MoneyIcon.set(this.appViewWidth * 7 / 30 + smx, this.appViewHeight * 3 / 12 - my, this.appViewWidth * 8 / 30 + smx, this.appViewHeight * 4 / 12 - my);
        displaygoodsText.set(this.appViewWidth * 4 / 30 + smx, this.appViewHeight * 1 / 12 + my * 2, this.appViewWidth * 15 / 30 - smx, this.appViewHeight * 2 / 12 + my * 2);
        displayspecialnum.set(this.appViewWidth * 9 / 60 + 2, this.appViewHeight * 2 / 10 + 5, this.appViewWidth * 13 / 60 + 2, this.appViewHeight * 3 / 10 + 5);


        cancelplaceS.set(this.appViewWidth * 1 / 20, this.appViewHeight * 12 / 20, this.appViewWidth * 5 / 20, this.appViewHeight * 17 / 20);
        cancelplaceE.set(this.appViewWidth * 0 / 11, this.appViewHeight * 8 / 9, this.appViewWidth * 3 / 11, this.appViewHeight);

    }

    private void InitRect() {

        payplace.set(this.appViewWidth * 3 / 11, this.appViewHeight * 3 / 11, this.appViewWidth * 7 / 11, this.appViewHeight * 17 / 22);
        yesplace.set(this.appViewWidth * 15 / 22, this.appViewHeight * 6 / 11, this.appViewWidth * 21 / 22, this.appViewHeight * 17 / 22 + 10);
        background.set(0, 0, this.appViewWidth, this.appViewHeight);


    }

    private void Initdisplaymoneycoordinate() {
        /*displaypaymoneycoordinate[0]=this.appViewWidth*2/20;

		displaypaymoneycoordinate[1]=this.appViewWidth*3/20;
		
		displaypaymoneycoordinate[2]=this.appViewWidth*4/20;
		
		displaypaymoneycoordinate[3]=this.appViewWidth*5/20;
		
		displaypaymoneycoordinate[4]=this.appViewWidth*6/20;
		
		displaypaymoneycoordinate[5]=this.appViewWidth*7/20;
		*/
        displaypaymoneycoordinate[0] = this.appViewHeight * 12 / 20;
        displaypaymoneycoordinate[1] = this.appViewHeight * 13 / 20;
        displaypaymoneycoordinate[2] = this.appViewHeight * 14 / 20;
        displaypaymoneycoordinate[3] = this.appViewHeight * 15 / 20;
        displaypaymoneycoordinate[4] = this.appViewHeight * 16 / 20;
        displaypaymoneycoordinate[5] = this.appViewHeight * 17 / 20;
        displaypaymoneycoordinate[6] = this.appViewHeight * 18 / 20;

        displaygivemoneycoordinate[0] = this.appViewWidth * 3 / 20;
        displaygivemoneycoordinate[1] = this.appViewWidth * 4 / 20;
        displaygivemoneycoordinate[2] = this.appViewWidth * 5 / 20;
        displaygivemoneycoordinate[3] = this.appViewWidth * 6 / 20;
        displaygivemoneycoordinate[4] = this.appViewWidth * 7 / 20;

    }

    private void PaintPayMoney(Canvas canvas, Bitmap[] mBmp, int drawnum) {

        for (int i = 0; i < drawnum; i++) {
            if (i <= 4) {
                /*displaymoney.set(displaypaymoneycoordinate[i],this.appViewHeight*6/10,
				                 displaypaymoneycoordinate[i+1],this.appViewHeight*7/10);*/
                displaymoney.set(this.appViewWidth * 2 / 20, displaypaymoneycoordinate[i],
                        this.appViewWidth * 3 / 20, displaypaymoneycoordinate[i + 2]);

                canvas.drawBitmap(mBmp[i], null, displaymoney, paint);
            } else if (i > 4 && i <= 9) {
				/*displaymoney.set(displaypaymoneycoordinate[i-5],this.appViewHeight*13/20,
		                         displaypaymoneycoordinate[i-4],this.appViewHeight*15/20);*/

                displaymoney.set(this.appViewWidth * 3 / 20, displaypaymoneycoordinate[i - 5],
                        this.appViewWidth * 4 / 20, displaypaymoneycoordinate[i - 3]);
                canvas.drawBitmap(mBmp[i], null, displaymoney, paint);
            } else if (i > 9 && i <= 14) {
                displaymoney.set(this.appViewWidth * 4 / 20, displaypaymoneycoordinate[i - 10],
                        this.appViewWidth * 5 / 20, displaypaymoneycoordinate[i - 8]);
                canvas.drawBitmap(mBmp[i], null, displaymoney, paint);

            }
        }

    }

    private void PaintGiveMoney(Canvas canvas) {

        if (givemoney[Fivehundred] == 1) {
            displaymoney.set(this.appViewWidth * 3 / 20, this.appViewHeight * 7 / 20,
                    this.appViewWidth * 4 / 20, this.appViewHeight * 9 / 20);
            canvas.drawBitmap(money500, null, displaymoney, null);
        }
        if (givemoney[Onehundred] > 0) {
            for (int i = 0; i < givemoney[Onehundred]; i++) {
                displaymoney.set(displaygivemoneycoordinate[i], this.appViewHeight * 8 / 20,
                        displaygivemoneycoordinate[i + 1], this.appViewHeight * 10 / 20);
                canvas.drawBitmap(money100, null, displaymoney, null);
            }
        }
        if (givemoney[Fifty] == 1) {
            displaymoney.set(this.appViewWidth * 3 / 20, this.appViewHeight * 9 / 20,
                    this.appViewWidth * 4 / 20, this.appViewHeight * 11 / 20);
            canvas.drawBitmap(money50, null, displaymoney, null);
        }
        if (givemoney[Ten] > 0) {
            for (int i = 0; i < givemoney[Ten]; i++) {
                displaymoney.set(displaygivemoneycoordinate[i], this.appViewHeight * 10 / 20,
                        displaygivemoneycoordinate[i + 1], this.appViewHeight * 12 / 20);
                canvas.drawBitmap(money10, null, displaymoney, null);
            }
        }
        if (givemoney[Five] == 1) {
            displaymoney.set(this.appViewWidth * 3 / 20, this.appViewHeight * 11 / 20,
                    this.appViewWidth * 4 / 20, this.appViewHeight * 13 / 20);
            canvas.drawBitmap(money5, null, displaymoney, null);
        }
        if (givemoney[One] > 0) {
            for (int i = 0; i < givemoney[One]; i++) {
                displaymoney.set(displaygivemoneycoordinate[i], this.appViewHeight * 12 / 20,
                        displaygivemoneycoordinate[i + 1], this.appViewHeight * 14 / 20);
                canvas.drawBitmap(money1, null, displaymoney, null);
            }
        }

    }

    private void PaintPriceNumber(Canvas canvas, int num) {
        if (num < 0) num = 0;
        int mx = 10, my = 5, sy = 2, sx = 2;
        ;

        if (isPlayScoreban == false) {
            String tmpStr = "" + Math.abs(num);
            for (int i = 0; i < tmpStr.length(); i++) {
                int nm = Integer.parseInt(tmpStr.charAt(i) + "");
                if (nm > 0 && nm < 10)                          //�]�w��ܪ��Ʀr�Ϥ�
                    moneybmp.set(125 * (nm - 1), 0, 125 * nm, 125);
                else
                    moneybmp.set(1125, 0, 1250, 125);

                Moneynumber.set(this.appViewWidth * (i + 8) / 30 + mx - sx * i, this.appViewHeight * 3 / 12 - my + sy,
                        this.appViewWidth * (i + 9) / 30 + mx - sx * i, this.appViewHeight * 4 / 12 - my - sy);

                canvas.drawBitmap(moneynum, moneybmp, Moneynumber, paint);

            }
        }
    }

    private int getScore(int[] mon, int[] givemon, int sec)   //�p��o���覡
    {
        int scoremax = 100;
        int coinnum = 0;
        double Rscore;
        boolean isMinus = false;

        Rscore = Math.random();
        scoremax = scoremax + (int) ((Rscore * 100) % 6);

        for (int i = 0; i < 7; i++)
            coinnum = coinnum + givemon[i];
        scoremax = scoremax - coinnum * 3 - sec;

        for (int i = 0; i < paymoney.length; i++)  //�p�⪱�a�I������A���S���M��^��������ۦP���B
        {
            if (paymoney[i] > 0) {
                if (paymoney[i] != (paymoney[i] - givemon[i])) {
                    MinusScore = 10 * givemon[i];
                    scoremax = scoremax - MinusScore;
                    MinusAllScore = MinusAllScore + MinusScore;
                    isMinus = true;
                }
            }
        }

        if (mon[One] > 4) {
            Rscore = Math.random();
            MinusScore = 10 + (int) ((Rscore * 100) % 4);
            scoremax = scoremax - MinusScore;
            MinusAllScore = MinusAllScore + MinusScore;
            isMinus = true;
        }
        if (mon[Five] > 1) {
            Rscore = Math.random();
            MinusScore = 10 + (int) ((Rscore * 100) % 4);
            scoremax = scoremax - MinusScore;
            MinusAllScore = MinusAllScore + MinusScore;
            isMinus = true;
        }
        if (mon[Ten] > 4) {
            Rscore = Math.random();
            MinusScore = 10 + (int) ((Rscore * 100) % 4);
            scoremax = scoremax - MinusScore;
            MinusAllScore = MinusAllScore + MinusScore;
            isMinus = true;
        }
        if (mon[Fifty] > 1) {
            Rscore = Math.random();
            MinusScore = 10 + (int) ((Rscore * 100) % 4);
            scoremax = scoremax - MinusScore;
            MinusAllScore = MinusAllScore + MinusScore;
            isMinus = true;
        }
        if (mon[Onehundred] > 4) {
            Rscore = Math.random();
            MinusScore = 10 + (int) ((Rscore * 100) % 4);
            scoremax = scoremax - MinusScore;
            MinusAllScore = MinusAllScore + MinusScore;
            isMinus = true;
        }
        if (mon[Fivehundred] > 1) {
            Rscore = Math.random();
            MinusScore = 10 + (int) ((Rscore * 100) % 4);
            scoremax = scoremax - MinusScore;
            MinusAllScore = MinusAllScore + MinusScore;
            isMinus = true;
        }


        if ((PlayerPayNum - coinnum) > 3 && isMinus == false)   //�Y�S�������A�h�i�H��o�B�~����
        {
            Rscore = Math.random();
            BonusScore = (PlayerPayNum - coinnum) + (int) ((Rscore * 100) % 6);
            scoremax = scoremax + BonusScore;
        }
        this.PlayerPayNum = 0;

        if (scoremax <= 0) scoremax = 1;
        return scoremax;

    }

    private void findcoin(int userpay, int storeprice)  //���Ӽ�
    {
        int Subtraction = 0;
        Subtraction = userpay - storeprice;
        givemoney[Onethousand] = Subtraction / 1000;
        Subtraction = Subtraction - givemoney[Onethousand] * 1000;

        givemoney[Fivehundred] = Subtraction / 500;
        Subtraction = Subtraction - givemoney[Fivehundred] * 500;

        givemoney[Onehundred] = Subtraction / 100;
        Subtraction = Subtraction - givemoney[Onehundred] * 100;

        givemoney[Fifty] = Subtraction / 50;
        Subtraction = Subtraction - givemoney[Fifty] * 50;

        givemoney[Ten] = Subtraction / 10;
        Subtraction = Subtraction - givemoney[Ten] * 10;

        givemoney[Five] = Subtraction / 5;
        Subtraction = Subtraction - givemoney[Five] * 5;

        givemoney[One] = Subtraction;

    }

    private void MoneyToCoin(int coin)  //���B�Ǵ�������Ӽ�
    {
        money[Onethousand] = coin / 1000;
        coin = coin - money[Onethousand] * 1000;

        money[Fivehundred] = coin / 500;
        coin = coin - money[Fivehundred] * 500;

        money[Onehundred] = coin / 100;
        coin = coin - money[Onehundred] * 100;

        money[Fifty] = coin / 50;
        coin = coin - money[Fifty] * 50;

        money[Ten] = coin / 10;
        coin = coin - money[Ten] * 10;

        money[Five] = coin / 5;
        coin = coin - money[Five] * 5;

        money[One] = coin;

    }

    private void moveMoney(Canvas canvas) {
        int move = 30;
        if (mX != 0 && mY != 0) {
            movemoney.set(mX - this.appViewWidth / 22 - move, mY - this.appViewHeight / 9 - move, mX + this.appViewWidth / 22 - move, mY + this.appViewHeight / 9 - move);
            switch (selectmoney) {
                case 1:
                    if (money[6] > 0) {
                        selectmoneytopay = 1;
                        canvas.drawBitmap(money1, null, movemoney, paint);
                    } else
                        selectmoneytopay = 0;
                    break;
                case 5:
                    if (money[5] > 0) {
                        selectmoneytopay = 5;
                        canvas.drawBitmap(money5, null, movemoney, paint);
                    } else
                        selectmoneytopay = 0;
                    break;
                case 10:
                    if (money[4] > 0) {
                        selectmoneytopay = 10;
                        canvas.drawBitmap(money10, null, movemoney, paint);
                    } else
                        selectmoneytopay = 0;
                    break;
                case 50:
                    if (money[3] > 0) {
                        selectmoneytopay = 50;
                        canvas.drawBitmap(money50, null, movemoney, paint);
                    } else
                        selectmoneytopay = 0;
                    break;
                case 100:
                    if (money[2] > 0) {
                        selectmoneytopay = 100;
                        canvas.drawBitmap(money100, null, movemoney, paint);
                    } else
                        selectmoneytopay = 0;
                    break;
                case 500:
                    if (money[1] > 0) {
                        selectmoneytopay = 500;
                        canvas.drawBitmap(money500, null, movemoney, paint);
                    } else
                        selectmoneytopay = 0;
                    break;
                case 1000:
                    if (money[0] > 0) {
                        selectmoneytopay = 1000;
                        canvas.drawBitmap(money1000, null, movemoney, paint);
                    } else
                        selectmoneytopay = 0;
                    break;
                default:
                    break;

            }
        }
    }

    private void ChangeCoinNumber(int scoin) {
        switch (scoin) {
            case 1000:
                if (money[Onethousand] > 0)
                    money[Onethousand] = money[Onethousand] - 1;
                else
                    money[Onethousand] = 0;
                break;
            case 500:
                if (money[Fivehundred] > 0)
                    money[Fivehundred] = money[Fivehundred] - 1;
                else
                    money[Fivehundred] = 0;
                break;
            case 100:
                if (money[Onehundred] > 0)
                    money[Onehundred] = money[Onehundred] - 1;
                else
                    money[Onehundred] = 0;
                break;
            case 50:
                if (money[Fifty] > 0)
                    money[Fifty] = money[Fifty] - 1;
                else
                    money[Fifty] = 0;
                break;
            case 10:
                if (money[Ten] > 0)
                    money[Ten] = money[Ten] - 1;
                else
                    money[Ten] = 0;
                break;
            case 5:
                if (money[Five] > 0)
                    money[Five] = money[Five] - 1;
                else
                    money[Five] = 0;
                break;
            case 1:
                if (money[One] > 0)
                    money[One] = money[One] - 1;
                else
                    money[One] = 0;
                break;
            default:
                break;

        }

    }

    private void setPayMoneybmp(int scoin, int num)   //�]�w���a�̧ǩҥI�����B�Ϥ�
    {
        if (num < paymoneybmp.length) {
            switch (scoin) {
                case 1000:
                    if (money[Onethousand] > 0) {
                        paymoney[Onethousand]++;
                        paymoneybmp[num] = sMoneybmp[Onethousand];
                        this.PlayerPayNum++;
                    }
                    break;
                case 500:
                    if (money[Fivehundred] > 0) {
                        paymoney[Fivehundred]++;
                        paymoneybmp[num] = sMoneybmp[Fivehundred];
                        this.PlayerPayNum++;
                    }
                    break;
                case 100:
                    if (money[Onehundred] > 0) {
                        paymoney[Onehundred]++;
                        paymoneybmp[num] = sMoneybmp[Onehundred];
                        this.PlayerPayNum++;
                    }
                    break;
                case 50:
                    if (money[Fifty] > 0) {
                        paymoney[Fifty]++;
                        paymoneybmp[num] = sMoneybmp[Fifty];
                        this.PlayerPayNum++;
                    }
                    break;
                case 10:
                    if (money[Ten] > 0) {
                        paymoney[Ten]++;
                        paymoneybmp[num] = sMoneybmp[Ten];
                        this.PlayerPayNum++;
                    }
                    break;
                case 5:
                    if (money[Five] > 0) {
                        paymoney[Five]++;
                        paymoneybmp[num] = sMoneybmp[Five];
                        this.PlayerPayNum++;
                    }
                    break;
                case 1:
                    if (money[One] > 0) {
                        paymoney[One]++;
                        paymoneybmp[num] = sMoneybmp[One];
                        this.PlayerPayNum++;
                    }
                    break;
                default:
                    break;
            }

        }

    }

    private void CheckMoneybigTen()   //�ˬd������S���W�L�Q��
    {
        if (money[One] > 9) {
            money[One] = money[One] - 10;
            money[Ten] = money[Ten] + 1;
        }

        if (money[Five] > 9) {
            money[Five] = money[Five] - 10;
            money[Fifty] = money[Fifty] + 1;
        }

        if (money[Ten] > 9) {
            money[Ten] = money[Ten] - 10;
            money[Onehundred] = money[Onehundred] + 1;
        }

        if (money[Fifty] > 9) {
            money[Fifty] = money[Fifty] - 10;
            money[Fivehundred] = money[Fivehundred] + 1;
        }

        if (money[Onehundred] > 9) {
            money[Onehundred] = money[Onehundred] - 10;
            money[Onethousand] = money[Onethousand] + 1;
        }

    }

    private void paintMoneyNumber(Canvas canvas, int num, int coin)//num ��ܪ��Ʀr  coin ��ܦb���Ӫ��B�W
    {
        int mx = 0, hx = 3;
        if (num > 0 && num < 10)                          //�]�w��ܪ��Ʀr�Ϥ�
            moneybmp.set(125 * (num - 1), 0, 125 * num, 125);
        else
            moneybmp.set(1125, 0, 1250, 125);

        switch (coin) {
            case 1000:
                Moneynumber.set(this.appViewWidth * 8 / 11 - mx + this.appViewWidth / (11 * 4), this.appViewHeight * 20 / 27 + hx * 4,
                        this.appViewWidth * 9 / 11 - mx - this.appViewWidth / (11 * 4), this.appViewHeight * 22 / 27 + hx * 4);
                canvas.drawBitmap(moneynum, moneybmp, Moneynumber, paint);
                break;
            case 500:
                Moneynumber.set(this.appViewWidth * 7 / 11 - mx + this.appViewWidth / (11 * 4), this.appViewHeight * 20 / 27 + hx * 4,
                        this.appViewWidth * 8 / 11 - mx - this.appViewWidth / (11 * 4), this.appViewHeight * 22 / 27 + hx * 4);
                canvas.drawBitmap(moneynum, moneybmp, Moneynumber, paint);
                break;
            case 100:
                Moneynumber.set(this.appViewWidth * 6 / 11 - mx + this.appViewWidth / (11 * 4), this.appViewHeight * 20 / 27 + hx * 4,
                        this.appViewWidth * 7 / 11 - mx - this.appViewWidth / (11 * 4), this.appViewHeight * 22 / 27 + hx * 4);
                canvas.drawBitmap(moneynum, moneybmp, Moneynumber, paint);
                break;
            case 50:
                Moneynumber.set(this.appViewWidth * 5 / 11 - mx + this.appViewWidth / (11 * 4), this.appViewHeight * 20 / 27 + hx * 4,
                        this.appViewWidth * 6 / 11 - mx - this.appViewWidth / (11 * 4), this.appViewHeight * 22 / 27 + hx * 4);
                canvas.drawBitmap(moneynum, moneybmp, Moneynumber, paint);
                break;
            case 10:
                Moneynumber.set(this.appViewWidth * 4 / 11 - mx + this.appViewWidth / (11 * 4), this.appViewHeight * 20 / 27 + hx * 4,
                        this.appViewWidth * 5 / 11 - mx - this.appViewWidth / (11 * 4), this.appViewHeight * 22 / 27 + hx * 4);
                canvas.drawBitmap(moneynum, moneybmp, Moneynumber, paint);
                break;
            case 5:
                Moneynumber.set(this.appViewWidth * 3 / 11 - mx + this.appViewWidth / (11 * 4), this.appViewHeight * 20 / 27 + hx * 4,
                        this.appViewWidth * 4 / 11 - mx - this.appViewWidth / (11 * 4), this.appViewHeight * 22 / 27 + hx * 4);
                canvas.drawBitmap(moneynum, moneybmp, Moneynumber, paint);
                break;
            case 1:
                Moneynumber.set(this.appViewWidth * 2 / 11 - mx + this.appViewWidth / (11 * 4), this.appViewHeight * 20 / 27 + hx * 4,
                        this.appViewWidth * 3 / 11 - mx - this.appViewWidth / (11 * 4), this.appViewHeight * 22 / 27 + hx * 4);
                canvas.drawBitmap(moneynum, moneybmp, Moneynumber, paint);
                break;
        }

    }

    private void PaintMoneyCoin(Canvas canvas) {
        if (money[6] > 0)
            canvas.drawBitmap(money1, null, one, paint);
        if (money[5] > 0)
            canvas.drawBitmap(money5, null, five, paint);
        if (money[4] > 0)
            canvas.drawBitmap(money10, null, ten, paint);
        if (money[3] > 0)
            canvas.drawBitmap(money50, null, fifty, paint);
        if (money[2] > 0)
            canvas.drawBitmap(money100, null, onehundred, paint);
        if (money[1] > 0)
            canvas.drawBitmap(money500, null, fivehundred, paint);
        if (money[0] > 0)
            canvas.drawBitmap(money1000, null, onethounsand, paint);

    }

    private void PaintGoods(Canvas canvas, int fnum) {
        int scalenum = 2;
        if (fnum >= 0 && fnum < 4) {
            foodbmp.set(0 / scalenum, fnum * 256 / scalenum, 256 / scalenum, (fnum + 1) * 256 / scalenum);
        } else if (fnum >= 4 && fnum < 8) {
            foodbmp.set(256 / scalenum, (fnum - 4) * 256 / scalenum, 512 / scalenum, (fnum - 3) * 256 / scalenum);
        } else if (fnum >= 8 && fnum < 12) {
            foodbmp.set(512 / scalenum, (fnum - 8) * 256 / scalenum, 768 / scalenum, (fnum - 7) * 256 / scalenum);
        } else if (fnum >= 12 && fnum < 16) {
            foodbmp.set(768 / scalenum, (fnum - 12) * 256 / scalenum, 1024 / scalenum, (fnum - 11) * 256 / scalenum);
        } else if (fnum >= 16 && fnum < 20) {
            foodbmp.set(1024 / scalenum, (fnum - 16) * 256 / scalenum, 1280 / scalenum, (fnum - 15) * 256 / scalenum);
        }
        displaygoods.set(this.appViewWidth * 3 / 11, this.appViewHeight * 4 / 11,
                this.appViewWidth * 7 / 11, this.appViewHeight * 9 / 11);
        canvas.drawBitmap(GoodsImage, foodbmp, displaygoods, paint);
        //canvas.drawRect(displaygoods, paint);

    }

    int scoremove = -14;

    private void PaintScoreBan(Canvas canvas) {
        if (this.isPlayScoreban) {

            if (scoremove < 1) {
                scoremove = scoremove + 5;
                //isPlayScoreban=false;
            } else {
                scoremove = 1;
            }

            displayscoreban.set(this.appViewWidth * 1 / 12, this.appViewHeight * scoremove / 8, this.appViewWidth * 11 / 12, this.appViewHeight * (scoremove + 6) / 8);
            //displayscoreban.set(this.appViewWidth*3/30,this.appViewHeight*(scoremove)/24,this.appViewWidth*15/30,this.appViewHeight*(scoremove+11)/24);
            canvas.drawBitmap(Scoreban, null, displayscoreban, paint);
            if (scoremove == 1) {
                if (this.BonusScore > 0) PaintBonus(canvas, Bonus, this.BonusScore);
                else if (this.MinusScore > 0) PaintBonus(canvas, Minus, this.MinusAllScore);
                PaintScoreNumber(canvas, this.PlayerScore, (scoremove + 3));
                PaintGiveMoney(canvas);
            }

        }

    }

    private void PaintBonus(Canvas canvas, Bitmap bmp, int num) {
        displaysbonus.set(this.appViewWidth * 19 / 32, this.appViewHeight * 2 / 8,
                this.appViewWidth * 27 / 32, this.appViewHeight * 3 / 8);
        canvas.drawBitmap(bmp, null, displaysbonus, null);

        if (num < 0) num = 0;
        //int sy=0;
        //int sx=3;

        String tmpStr = "" + Math.abs(num);
        for (int i = 0; i < tmpStr.length(); i++) {
            int nm = Integer.parseInt(tmpStr.charAt(i) + "");
            if (nm > 0 && nm < 10)                          //�]�w��ܪ��Ʀr�Ϥ�
                scorebmp.set(125 * (nm - 1), 0, 125 * nm, 125);
            else
                scorebmp.set(1125, 0, 1250, 125);

            scorenumber.set(this.appViewWidth * (i + 17) / 24, this.appViewHeight * 9 / 24,
                    this.appViewWidth * (i + 18) / 24, this.appViewHeight * 11 / 24);

            canvas.drawBitmap(Scorenumber, scorebmp, scorenumber, null);
        }


    }

    private void PaintScoreNumber(Canvas canvas, int num, int move) {
        if (num < 0) num = 0;
        int sy = 10, sx = 3;

        String tmpStr = "" + Math.abs(num);
        for (int i = 0; i < tmpStr.length(); i++) {
            int nm = Integer.parseInt(tmpStr.charAt(i) + "");
            if (nm > 0 && nm < 10)                          //�]�w��ܪ��Ʀr�Ϥ�
                scorebmp.set(125 * (nm - 1), 0, 125 * nm, 125);
            else
                scorebmp.set(1125, 0, 1250, 125);
			
			/*scorenumber.set(this.appViewWidth*(i+3)/15-sx*i, this.appViewHeight*(move)/6,
			                this.appViewWidth*(i+4)/15-sx*i, this.appViewHeight*(move+1)/6-sy);*/
            scorenumber.set(this.appViewWidth * (i + 5) / 12 - sx * i, this.appViewHeight * 2 / 4 - sy,
                    this.appViewWidth * (i + 6) / 12 - sx * i, this.appViewHeight * 3 / 4 - sy);

            canvas.drawBitmap(Scorenumber, scorebmp, scorenumber, paint);
        }

    }


    private void PaintSpecialnumber(Canvas canvas, int num)   //�e�S��ƶq��
    {
        if (num < 1 || num > 5) num = 1;

        specialnumbmp.set(200 * (num - 1), 0, 200 * num, 200);
        canvas.drawBitmap(Specialnumber, specialnumbmp, displayspecialnum, paint);

    }

    int Cnum = 0;

    private void PaintBackGround(Canvas canvas) {

        if (Cnum > 11) Cnum = 0;
        switch (Cnum) {
            case 0:
            case 1:
            case 2:
                canvas.drawBitmap(backgroundbmp[0], null, background, paint);
                Cnum++;
                break;
            case 3:
            case 4:
            case 5:
                canvas.drawBitmap(backgroundbmp[1], null, background, paint);
                Cnum++;
                break;
            case 6:
            case 7:
            case 8:
                canvas.drawBitmap(backgroundbmp[2], null, background, paint);
                Cnum++;
                break;
            case 9:
            case 10:
            case 11:
                canvas.drawBitmap(backgroundbmp[1], null, background, paint);
                Cnum++;
                break;

            default:
                break;
        }

    }

    //int pm2,pm3,pm4;


    public void doDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        //canvas.drawBitmap(gvbackground, null, background, paint);
        PaintBackGround(canvas);
        //canvas.drawRect(this.yesplace, paint);
        //canvas.drawRect(this.payplace, paint);

        canvas.drawBitmap(moneyicon, null, MoneyIcon, paint);

        PaintMoneyCoin(canvas);
        PaintGoods(canvas, goods.GetNowGoodsNumber());

        moveMoney(canvas);

        if (money[0] > 0)
            paintMoneyNumber(canvas, money[0], 1000);
        if (money[1] > 0)
            paintMoneyNumber(canvas, money[1], 500);
        if (money[2] > 0)
            paintMoneyNumber(canvas, money[2], 100);
        if (money[3] > 0)
            paintMoneyNumber(canvas, money[3], 50);
        if (money[4] > 0)
            paintMoneyNumber(canvas, money[4], 10);
        if (money[5] > 0)
            paintMoneyNumber(canvas, money[5], 5);
        if (money[6] > 0)
            paintMoneyNumber(canvas, money[6], 1);

        PaintPayMoney(canvas, paymoneybmp, PlayerPayNum);    //�K�ҥI������
        //paintPriceNumber(canvas,goods20price[goodsnum]);    //�K�ӫ~���
        PaintPriceNumber(canvas, goods20price[goods.GetGoodsnum()]);    //�K�ӫ~���

        if (isPlayScoreban == false) {
            GoodsText = goods.GetNowStoreGoodsText();
            canvas.drawBitmap(GoodsText, null, displaygoodsText, paint);  //�K�ӫ~��r
        }

        if (goods20price[goods.GetGoodsnum()] >= 500)
            specialnum = 5;
        else if (goods20price[goods.GetGoodsnum()] < 500 && goods20price[goods.GetGoodsnum()] >= 300)
            specialnum = 4;
        else if (goods20price[goods.GetGoodsnum()] < 300 && goods20price[goods.GetGoodsnum()] >= 200)
            specialnum = 3;
        else if (goods20price[goods.GetGoodsnum()] < 200 && goods20price[goods.GetGoodsnum()] >= 50)
            specialnum = 2;
        else if (goods20price[goods.GetGoodsnum()] < 50)
            specialnum = 1;
        PaintSpecialnumber(canvas, specialnum);


        PaintScoreBan(canvas);
        //PaintBonus(canvas,50);
        //canvas.drawBitmap(Scoreban,0,0, paint);
        //canvas.drawBitmap(history01, null, background, paint);

        //pm=goods.getGameDuringTime();
        //pm2=this.PlayerNowMoney;
        //pm3=this.PlayerPayMoney;
        //pm4=this.PlayerScore;
        //String str1=String.valueOf(pm);
        //String str2=String.valueOf(pm2);
        //String str3=String.valueOf(pm3);
        //String str4=String.valueOf(pm4);
        //String str5=String.valueOf(scoremove);

        //canvas.drawText(str1+"�g�L�ɶ�", 0, 70, paint);
        //canvas.drawText(str2+"���a���B", 0, 90, paint);
        //canvas.drawText(str3+"�ҥI���B", 0, 110, paint);
        //canvas.drawText(str4+"���a�o��", 0, 130, paint);
        //canvas.drawText(str5+"���a�`��", 0, 50, paint);

    }

    int uX, uY, gn;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        int x = (int) event.getX();
        int y = (int) event.getY();


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                if (onethounsand.contains(x, y))
                    selectmoney = 1000;
                else if (fivehundred.contains(x, y))
                    selectmoney = 500;
                else if (onehundred.contains(x, y))
                    selectmoney = 100;
                else if (fifty.contains(x, y))
                    selectmoney = 50;
                else if (ten.contains(x, y))
                    selectmoney = 10;
                else if (five.contains(x, y))
                    selectmoney = 5;
                else if (one.contains(x, y))
                    selectmoney = 1;
                else if (cancelplaceS.contains(x, y))
                    isCanceling = true;


                break;
            case MotionEvent.ACTION_MOVE:

                mX = x;
                mY = y;

                break;
            case MotionEvent.ACTION_UP:

                uX = x;
                uY = y;
                if (payplace.contains(mX, mY)) {

                    setPayMoneybmp(selectmoneytopay, this.PlayerPayNum);
                    ChangeCoinNumber(selectmoney);
                    //this.PlayerPayNum++;

                    //this.PlayerNowMoney=this.PlayerNowMoney-selectmoneytopay;
                    this.PlayerPayMoney = this.PlayerPayMoney + selectmoneytopay;
                } else if (yesplace.contains(uX, uY)) {
                    if (isPlayScoreban == false) {
                        if (this.PlayerPayMoney >= goods20price[goods.GetGoodsnum()]) {
                            int sec;
                            sec = noactivity.nmview.getTimeSec();
                            goods.setEndsec(sec);
                            paymusic.start();

                            findcoin(this.PlayerPayMoney, goods20price[goods.GetGoodsnum()]);     //�p��X���w���Ӽ�


                            for (int i = 0; i < money.length; i++)                //�{����ƥ[�W�����
                                money[i] = money[i] + givemoney[i];

                            CheckMoneybigTen();

                            for (int i = 0; i < firstmoney.length; i++)
                                firstmoney[i] = money[i];

                            this.PlayerNowMoney = PlayerNowMoney - goods20price[goods.GetGoodsnum()];  //���a�`���B��ӫ~���=�ѤU����


                            this.PlayerScore = getScore(money, givemoney, goods.getGameDuringTime());   //��o�����ʶR�ӫ~���o��

                            this.PlayerTotalSocre = this.PlayerTotalSocre + this.PlayerScore; //�ֿn���a������

                            isPlayScoreban = true; //���񦹦��o���S��
							
							
							
						
							
							/*goodsnum++; 
							if(goodsnum>19)goodsnum=0;*/
                            gn++;
                            if (gn > 19) gn = 0;
                            goods.SetGoodsnum(gn);

                            noactivity.ncHandler.sendEmptyMessage(HeavyWalletParameter.TRIGGER_DELAYHANDLER);
                        } else {
                            noactivity.ncHandler.sendEmptyMessage(5);
                            nomoneymusic.start();
                        }
                    }
                } else if (cancelplaceE.contains(uX, uY)) {
                    if (isCanceling) {
                        this.PlayerPayMoney = 0;
                        this.PlayerPayNum = 0;
                        for (int i = 0; i < firstmoney.length; i++)
                            money[i] = firstmoney[i];
                        for (int i = 0; i < paymoneybmp.length; i++)
                            paymoneybmp[i] = null;
                        for (int i = 0; i < paymoney.length; i++)
                            paymoney[i] = 0;
                        isCanceling = false;
                    }

                }


                selectmoney = 0;
                selectmoneytopay = 0;
                mX = 0;
                mY = 0;

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
        if (!gt.isAlive()) {    //�YDrawThread�S���ҰʡA�N�Ұʳo�Ӱ����
            gt.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }


}
