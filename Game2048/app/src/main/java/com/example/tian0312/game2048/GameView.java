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

        GestureListener gl = new GestureListener();
        this.setOnTouchListener(gl);

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
