package com.example.alexander.hikebulgaria;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alexander.hikebulgaria.login.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    public Animation animation;
    public ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        animation = AnimationUtils.loadAnimation(SplashScreen.this,
                R.anim.splash_anim);
        imgView = (ImageView) findViewById(R.id.imgView);

        playAnim();

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    protected void playAnim() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                imgView.startAnimation(animation);
            }
        }, 1000);
    }
    // Destroy the splashscreen so user cannot go back to it
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


}
