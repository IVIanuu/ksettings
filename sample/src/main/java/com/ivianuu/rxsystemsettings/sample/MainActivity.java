package com.ivianuu.rxsystemsettings.sample;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ivianuu.rxsystemsettings.RxSystemSettings;
import com.ivianuu.rxsystemsettings.SettingsType;
import com.ivianuu.rxsystemsettings.SystemSetting;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxSystemSettings systemSettings = RxSystemSettings.create(this);

        SystemSetting<Integer> hapticFeedback = systemSettings.getInt(
                Settings.System.HAPTIC_FEEDBACK_ENABLED, SettingsType.SYSTEM);

        hapticFeedback.observe()
                .subscribe(integer -> {
                    Log.d("testtt", "haptic feedback is now on ? " + String.valueOf(integer == 1));
                });


        SystemSetting<String> accessibility = systemSettings.getString(
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, SettingsType.SECURE);

        accessibility.observe()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("testtt", s);
                    }
                });

        SystemSetting<Integer> locationMode = systemSettings.getInt(
                Settings.Secure.LOCATION_MODE, SettingsType.SECURE);

        locationMode.observe()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("testtt", "location mode " + integer);
                    }
                });
    }
}
