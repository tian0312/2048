package com.example.tian0312.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian0312 on 16/1/24.
 */
public class GameView extends GridLayout {

    private Card[][] cardsMap = new Card[4][4];
    // 用于纪录没有数字卡片的位置
    private List<Point> emptyPoints = new ArrayList<Point>();
    private boolean isChanged;

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

        startGame();
    }

    // 添加16个方块
    private void addCard(int cardWidth, int cardHeight) {
        Card c;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());
                addView(c, cardWidth, cardHeight);
                cardsMap[x][y] = c;
            }
        }
    }

    private void startGame() {
        // 清理
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                 cardsMap[x][y].setNum(0);
            }
        }
        // 初始添加两个数
        addRandomNum();
        addRandomNum();
        // 设置初始状态颜色
        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setColor(cardsMap[x][y].getNum());
            }
        }
        // 分数清零
        MainActivity.getMainActivity().clearScore();
    }

    // 开始游戏随即添加两个数
    private void addRandomNum() {

        emptyPoints.clear();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }

        Point p = emptyPoints.remove((int)(Math.random() * emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);

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
                            leftGesture();
                        }
                        else if(xOffset > 5) {
                            rightGesture();
                        }
                    }
                    else {
                        if(yOffset < -5) {
                            upGesture();
                        }
                        else if(yOffset > 5) {
                            downGesture();
                        }
                    }
                    break;
            }

            return true;
        }
    }

    // 游戏逻辑
    private void leftGesture() {
        // 判断是否应该再产生一个随机数
        isChanged = false;
        // 两层循环遍历所有16个方格
        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++) {
                // 从当前元素的右边一列开始遍历
                for (int x1 = x+1; x1 < 4; x1++) {
                    // 若遍历到的元素不是0
                    if(cardsMap[x1][y].getNum() > 0) {
                        // 若当前元素为0，则把数字移过来，原先带数字的清零
                        if(cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            isChanged = true;
                            // 此处研究一下
                            // 为了防止类似2222情况后面两个2不合并
                            x--;
                            break;
                        }
                        // 若当前元素不为0，且两数字相同，合并，并清掉一个
                        else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            // 加分
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            isChanged = true;
                            break;
                        }
                        else if(!cardsMap[x][y].equals(cardsMap[x1][y])) {
                            break;
                        }
                    }
                }
            }
        }
        if(isChanged) {
            addRandomNum();
            checkEnd();
        }
        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setColor(cardsMap[x][y].getNum());
            }
        }
    }
    private void rightGesture() {
        isChanged = false;
        // 两层循环遍历所有16个方格
        for (int y = 0; y < 4; y++){
            for (int x = 3; x >= 0; x--) {
                // 从当前元素的左边一列开始遍历
                for (int x1 = x-1; x1 >= 0; x1--) {
                    // 若遍历到的元素不是0
                    if(cardsMap[x1][y].getNum() > 0) {
                        // 若当前元素为0，则把数字移过来，原先带数字的清零
                        if(cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            isChanged = true;
                            // 此处研究一下
                            x++;
                            break;
                        }
                        // 若当前元素不为0，且两数字相同，合并，并清掉一个
                        else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            isChanged = true;
                            break;
                        }
                        else if(!cardsMap[x][y].equals(cardsMap[x1][y])) {
                            break;
                        }
                    }
                }
            }
        }
        if(isChanged) {
            addRandomNum();
            checkEnd();
        }
        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setColor(cardsMap[x][y].getNum());
            }
        }
    }
    private void downGesture() {
        isChanged = false;
        // 两层循环遍历所有16个方格
        for (int x = 0; x < 4; x++){
            for (int y = 3; y >= 0; y--) {
                // 从当前元素的上面一行开始遍历
                for (int y1 = y-1; y1 >= 0; y1--) {
                    // 若遍历到的元素不是0
                    if(cardsMap[x][y1].getNum() > 0) {
                        // 若当前元素为0，则把数字移过来，原先带数字的清零
                        if(cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            isChanged = true;
                            // 此处研究一下
                            y++;
                            break;
                        }
                        // 若当前元素不为0，且两数字相同，合并，并清掉一个
                        else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            isChanged = true;
                            break;
                        }
                        else if(!cardsMap[x][y].equals(cardsMap[x][y1])) {
                            break;
                        }
                    }
                }
            }
        }
        if(isChanged) {
            addRandomNum();
            checkEnd();
        }
        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setColor(cardsMap[x][y].getNum());
            }
        }
    }
    private void upGesture() {
        isChanged = false;
        // 两层循环遍历所有16个方格
        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++) {
                // 从当前元素的下面一行开始遍历
                for (int y1 = y+1; y1 < 4; y1++) {
                    // 若遍历到的元素不是0
                    if(cardsMap[x][y1].getNum() > 0) {
                        // 若当前元素为0，则把数字移过来，原先带数字的清零
                        if(cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            isChanged = true;
                            // 此处研究一下
                            y--;
                            break;
                        }
                        // 若当前元素不为0，且两数字相同，合并，并清掉一个
                        else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            isChanged = true;
                            break;
                        }
                        else if(!cardsMap[x][y].equals(cardsMap[x][y1])) {
                            break;
                        }
                    }
                }
            }
        }
            if(isChanged) {
            addRandomNum();
            checkEnd();
        }
        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setColor(cardsMap[x][y].getNum());
            }
        }
    }

    private void checkEnd() {

        boolean isEnd = true;

        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() == 0 ||
                        (x > 0 && cardsMap[x][y].equals(cardsMap[x-1][y])) ||
                        (x < 3 && cardsMap[x][y].equals(cardsMap[x+1][y])) ||
                        (y > 0 && cardsMap[x][y].equals(cardsMap[x][y-1])) ||
                        (y < 3 && cardsMap[x][y].equals(cardsMap[x][y+1]))
                        ) {
                    isEnd = false;
                    break ALL;
                }
            }
        }

        if (isEnd) {
            new AlertDialog.Builder(getContext()).setTitle("Tip").setMessage("Game Over").setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }
    }
}
