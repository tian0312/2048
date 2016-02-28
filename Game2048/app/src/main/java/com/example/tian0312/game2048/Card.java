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

    // 数字的背景颜色
    public void setColor(int num) {
        switch (num) {
            case 0 : numText.setBackgroundColor(0x33ffffff);break;
            case 2 : numText.setBackgroundColor(0x99ffffff);break;
            case 4 : numText.setBackgroundColor(0xffede014);break;
            case 8 : numText.setBackgroundColor(0xfff4b179);break;
            case 16 : numText.setBackgroundColor(0xfff59163);break;
            case 32 : numText.setBackgroundColor(0xfff67c5f);break;
            case 64 : numText.setBackgroundColor(0xfff65e3b);break;
            case 128 : numText.setBackgroundColor(0xffedcc61);break;
            case 256 : numText.setBackgroundColor(0xffedcc61);break;
            case 512 : numText.setBackgroundColor(0xffedcc61);break;
            case 1024 : numText.setBackgroundColor(0xffedcc61);break;
            case 2048 : numText.setBackgroundColor(0xffedc22e);break;
            case 4096 : numText.setBackgroundColor(0xff0000ff);break;
            default: numText.setBackgroundColor(0xff0000ff);
        }
    }

}
