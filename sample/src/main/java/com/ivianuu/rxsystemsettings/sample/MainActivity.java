package com.ivianuu.rxsystemsettings.sample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ivianuu.rxsystemsettings.RxSystemSettings;
import com.ivianuu.rxsystemsettings.SettingsType;
import com.ivianuu.rxsystemsettings.SystemSetting;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri uri = Settings.Global.getUriFor("jdfjdjf");
        Log.d("testtt", "uri null ? " + String.valueOf(uri == null));

        RxSystemSettings systemSettings = RxSystemSettings.create(this);

        SystemSetting<Integer> ambientDisplay = systemSettings.getInt("doze_endjdjdabled", SettingsType.SECURE);

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
