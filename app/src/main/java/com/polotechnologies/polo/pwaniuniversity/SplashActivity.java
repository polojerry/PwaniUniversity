package com.polotechnologies.polo.pwaniuniversity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashImage = (ImageView)findViewById(R.id.image_splash);
        Animation splashAnimation = AnimationUtils.loadAnimation(this,R.anim.splash_transition);
        splashAnimation.setDuration(1500);
        splashImage.startAnimation(splashAnimation);

        splashAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent startloginInIntent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(startloginInIntent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
