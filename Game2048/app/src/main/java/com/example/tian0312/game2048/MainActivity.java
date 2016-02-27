package com.example.tian0312.game2048;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private TextView scoreText;
    private int score = 0;

    private static MainActivity mainActivity = null;

    public MainActivity() {
        mainActivity = this;
    }
    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreText = (TextView)findViewById(R.id.scoreText);

    }

    public void clearScore() {
        score = 0;
        showScore();
    }
    public void showScore() {
        scoreText.setText(score + "");
    }
    public void addScore(int s) {
        score = score + s;
        showScore();
    }
}
