package com.ivianuu.rxsystemsettings.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ivianuu.rxsystemsettings.RxSystemSettings
import com.ivianuu.rxsystemsettings.SettingsType

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val systemSettings = RxSystemSettings.create(this)

        val ambientDisplay = systemSettings.getInteger("doze_enabled", SettingsType.SECURE)

        ambientDisplay.observe()
                .subscribe { integer ->
                    Log.d("testt", "helo " + integer!!)
                }
    }
}
