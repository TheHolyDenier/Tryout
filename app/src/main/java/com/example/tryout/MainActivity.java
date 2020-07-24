package com.example.tryout;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unity3d.player.UnityPlayer;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    protected UnityPlayer mUnityPlayer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        

        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.uno:
                        startActivity(new Intent(getBaseContext(), UnoActivity.class));
                        break;
                    case R.id.dos:
                        startActivity(new Intent(getBaseContext(), DosActivity.class));
                        break;
                }
                return false;
            }
        });
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUnityPlayer = new UnityPlayer(this);

        int glesMode = mUnityPlayer.getSettings().getInt("gles_mode", 1);
        mUnityPlayer.init(glesMode, false);

        FrameLayout layout = findViewById(R.id.framelayout);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        layout.addView(mUnityPlayer, 0, lp);
        mUnityPlayer.windowFocusChanged(true);
        mUnityPlayer.resume();

        ActivityManager actManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        long totalMemory = memInfo.totalMem;

        Log.d("ALEX MAIn-> ", String.valueOf(totalMemory));
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    @Override
    protected void onDestroy() {
        mUnityPlayer.quit();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mUnityPlayer.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mUnityPlayer.resume();
    }
}