package com.ivianuu.ksystemsettings.sample

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ivianuu.ksystemsettings.KSystemSettings
import com.ivianuu.ksystemsettings.SettingsType
import com.ivianuu.ksystemsettings.lifecycle.liveData

class MainActivity : AppCompatActivity() {

    private val systemSettings by lazy { KSystemSettings(this) }

    private val autoRotation by lazy {
        systemSettings.int(
            Settings.System.ACCELEROMETER_ROTATION, SettingsType.SYSTEM
        )
    }

    private val autoRotation2 by lazy {
        systemSettings.int(
            Settings.System.ACCELEROMETER_ROTATION, SettingsType.SYSTEM
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        autoRotation.liveData.observe(this, Observer {
            Log.d("testtt", "changed 1 -> $it")
        })

        autoRotation2.liveData.observe(this, Observer {
            Log.d("testtt", "changed 2 -> $it")
        })
    }
}
