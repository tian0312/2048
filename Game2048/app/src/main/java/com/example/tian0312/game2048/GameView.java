package com.example.tian0312.game2048;

import android.content.Context;
import android.net.wifi.WifiEnterpriseConfig;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

/**
 * Created by tian0312 on 16/1/24.
 */
public class GameView extends GridLayout{

    public GameView(Context context) {
        super(context);
        initGameView();
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    private void initGameView() {
        // 设置GridLayout为4列
        this.setColumnCount(4);
        this.setBackgroundColor(0xffbbada0);

        GestureListener gl = new GestureListener();
        this.setOnTouchListener(gl);

    }

    // 适应手机的宽高
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w, h))/4;
        // 添加方块
        addCard(cardWidth, cardWidth);
    }

    // 添加16个方块
    private void addCard(int cardWidth, int cardHeight) {
        Card c;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());
                c.setNum(2);
                addView(c, cardWidth, cardHeight);
            }
        }
    }

    class GestureListener implements OnTouchListener {

        private float xStart;
        private float yStart;

        private float xOffset;
        private float yOffset;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            // 获取用户触碰和离开屏幕的坐标
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN :
                    xStart = event.getX();
                    yStart = event.getY();
                    break;
                case MotionEvent.ACTION_UP :
                    xOffset = event.getX() - xStart;
                    yOffset = event.getY() - yStart;
                    // 判断手势
                    if(Math.abs(xOffset) > Math.abs(yOffset)) {
                        if(xOffset < -5) {
                            System.out.println("left");
                        }
                        else if(xOffset > 5) {
                            System.out.println("right");
                        }
                    }
                    else {
                        if(yOffset < -5) {
                            System.out.println("up");
                        }
                        else if(yOffset > 5) {
                            System.out.println("down");
                        }
                    }
                    break;
            }

            return true;
        }
    }
}
