package com.ivianuu.ksystemsettings.sample

import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.ivianuu.ksystemsettings.KSystemSettings
import com.ivianuu.ksystemsettings.SettingsType
import com.ivianuu.rxsystemsettings.sample.R

class MainActivity : AppCompatActivity() {

    private val systemSettings by lazy { KSystemSettings(this) }

    private val autoRotation by lazy {
        systemSettings.int(
            Settings.System.ACCELEROMETER_ROTATION, SettingsType.SYSTEM
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
