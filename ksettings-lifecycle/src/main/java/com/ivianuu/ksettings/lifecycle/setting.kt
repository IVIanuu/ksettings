/*
 * Copyright 2018 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.ksettings.lifecycle

import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.ivianuu.ksettings.ChangeListener
import com.ivianuu.ksettings.KSettingsPlugins
import com.ivianuu.ksettings.Setting

/**
 * Adds the [listener] and automatically calls [Setting.removeListener] on [removeEvent]
 */
fun <T> Setting<T>.addListener(
    owner: LifecycleOwner,
    removeEvent: Lifecycle.Event = KSettingsPlugins.defaultRemoveEvent,
    listener: ChangeListener<T>
): ChangeListener<T> {
    removeEvent.checkValid()

    owner.lifecycle.addObserver(object : GenericLifecycleObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == removeEvent) {
                owner.lifecycle.removeObserver(this)
                removeListener(listener)
            }
        }
    })

    addListener(listener)

    return listener
}

/**
 * Returns a [LiveData] which contains the latest value of [this]
 */
val <T> Setting<T>.liveData: LiveData<T>
    get() = SettingLiveData(this)

private class SettingLiveData<T>(private val setting: Setting<T>) :
    LiveData<T>() {

    private val listener: ChangeListener<T> = { value = it }

    override fun onActive() {
        super.onActive()
        setting.addListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        setting.removeListener(listener)
    }
}