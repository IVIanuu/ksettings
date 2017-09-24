package com.ivianuu.rxsystemsettings.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ivianuu.rxsystemsettings.RxSystemSettings;
import com.ivianuu.rxsystemsettings.SettingsType;
import com.ivianuu.rxsystemsettings.SystemSetting;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxSystemSettings systemSettings = RxSystemSettings.create(this);

        SystemSetting<Integer> ambientDisplay = systemSettings.getInt("mobile_data", SettingsType.SECURE);

        ambientDisplay.observe()
                .subscribe(integer -> {
                    Log.d("testt", "hello " + integer);
                    if (integer == 0) {
                        ambientDisplay.set(1);
                    }
                });
    }
}
