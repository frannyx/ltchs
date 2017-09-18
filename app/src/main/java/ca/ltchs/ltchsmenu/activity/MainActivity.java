package ca.ltchs.ltchsmenu.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import ca.ltchs.ltchsmenu.R;
import ca.ltchs.ltchsmenu.db.DBHelper;
import ca.ltchs.ltchsmenu.db.EmployeeDB;
import ca.ltchs.ltchsmenu.db.LocationDB;
import ca.ltchs.ltchsmenu.fragment.AddItemFragment;

public class MainActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();

                    sleep(SPLASH_TIME_OUT);  //Delay of 10 seconds
                } catch (Exception e) {

                } finally {

                    Intent i = new Intent(MainActivity.this,
                            LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }


}





