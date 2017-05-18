package com.example.alexander.hikebulgaria;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexander.hikebulgaria.login.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    private Animation animation;
    private ImageView imgView;
    private TextView tvTitle;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //playSong(R.raw.eddie_vedder_longnights);

        animation = AnimationUtils.loadAnimation(SplashScreen.this,
                R.anim.splash_anim);

        imgView = (ImageView) findViewById(R.id.imgView);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                "fonts/beyond_the_mountains.ttf");

        tvTitle.setTypeface(custom_font);

        playAnim();

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(1000);
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

    private void playSong(int songID) {
        mediaPlayer = MediaPlayer.create(this, songID);
        mediaPlayer.start();
    }

    public void stopSong() {
        mediaPlayer.stop();
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
