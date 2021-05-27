package net.transolyfer.transolyfergo.app.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import net.transolyfer.transolyfergo.BuildConfig;
import net.transolyfer.transolyfergo.R;
import net.transolyfer.transolyfergo.app.ui.core.BaseCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseCompatActivity {

    @BindView(R.id.tvVersion)
    TextView tvVersion;

    private static final long SPLASH_SCREEN_DELAY = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        ButterKnife.bind(this);

        tvVersion.setText(BuildConfig.VERSION_NAME);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.i("SPLASH","Done");
                showLoginActivity();

            }
        }, SPLASH_SCREEN_DELAY);

    }

    private synchronized void showLoginActivity(){


        Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
        startActivity(i);
        finish();
    }
}
