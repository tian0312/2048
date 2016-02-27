package com.example.tian0312.game2048;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by tian0312 on 16/1/25.
 */
public class Card extends FrameLayout{

    private int num;
    private TextView numText;

    public Card(Context context) {
        super(context);

        numText = new TextView(getContext());
        numText.setTextSize(32);
        numText.setGravity(Gravity.CENTER);
        numText.setBackgroundColor(0x33ffffff);
        // 把TextView添加至布局
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(20, 20, 20, 20);
        this.addView(numText, lp);

        setNum(0);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if(num <= 0){
            numText.setText("");
        }
        else {
            numText.setText(num + "");
        }
    }

    public boolean equals(Card c) {
        return this.getNum() == c.getNum();
    }

    // 待补充
    public void numColor(int num) {
        switch (num) {
            case 0 : numText.setBackgroundColor(0x33ffffff);break;
            case 2 : numText.setBackgroundColor(0xffff0000);break;
            case 4 : numText.setBackgroundColor(0xff00ff00);break;
            default:;
        }
    }

}
