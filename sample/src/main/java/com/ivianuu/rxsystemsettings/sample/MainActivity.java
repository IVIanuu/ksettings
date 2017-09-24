package com.ivianuu.rxsystemsettings.sample;

import android.provider.Settings;
import android.support.annotation.RequiresPermission;
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

        SystemSetting<Integer> ambientDisplay = systemSettings.getInt("doze_enabled", SettingsType.SECURE);

        ambientDisplay.observe()
                .subscribe(ambientDisplay.consume());

        ambientDisplay.observe()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("testt", "hello " + integer);
                        if (integer == 0) {
                            ambientDisplay.set(1);
                        }
                    }
                });
    }
}
