package com.ivianuu.rxsystemsettings.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.ivianuu.rxsystemsettings.RxSystemSettings
import com.ivianuu.rxsystemsettings.SettingsType
import com.ivianuu.rxsystemsettings.SystemSetting

import io.reactivex.functions.Consumer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val systemSettings = RxSystemSettings.create(this)

        val ambientDisplay = systemSettings.getInteger("doze_enabled", SettingsType.SECURE)

        ambientDisplay.observe()
                .subscribe { integer ->
                    Log.d("testt", "helo " + integer!!)
                    if (integer == 0) {
                        ambientDisplay.set(1)
                    }
                }
    }
}
