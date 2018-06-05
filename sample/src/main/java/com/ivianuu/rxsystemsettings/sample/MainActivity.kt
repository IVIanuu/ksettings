package com.ivianuu.rxsystemsettings.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ivianuu.rxsystemsettings.RxSystemSettings
import com.ivianuu.rxsystemsettings.SettingsType
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val systemSettings = RxSystemSettings.create(this)

        val ambientDisplay = systemSettings.getInt("doze_enabled", SettingsType.SECURE)

        disposable = ambientDisplay.observe()
                .subscribe {
                    Log.d("RxSystemSettings", "ambient display changed $it")
                }
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}
