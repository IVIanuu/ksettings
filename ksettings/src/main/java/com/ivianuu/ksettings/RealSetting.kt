/*
 * Copyright 2017 Manuel Wrage
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

package com.ivianuu.ksettings

import android.content.ContentResolver
import android.net.Uri
import android.provider.Settings

/**
 * Implementation of an [Setting]
 */
internal class RealSetting<T>(
    private val contentObservers: ContentObservers,
    private val contentResolver: ContentResolver,
    override val name: String,
    private val defaultValue: T,
    private val adapter: Adapter<T>,
    override val type: Setting.Type
) : Setting<T> {

    override var value: T
        get() = adapter.get(name, defaultValue, contentResolver, type)
        set(value) {
            adapter.set(name, value, contentResolver, type)
        }

    override val uri: Uri by lazy {
        when (type) {
            Setting.Type.GLOBAL -> Settings.Global.getUriFor(name)
            Setting.Type.SECURE -> Settings.Secure.getUriFor(name)
            Setting.Type.SYSTEM -> Settings.System.getUriFor(name)
        }
    }

    override val exists by lazy { SettingsValidator.doesExist(name) }

    private val contentListener = {
        val value = value
        listeners.toList().forEach { it(value) }
    }

    private val listeners = mutableSetOf<ChangeListener<T>>()

    private var contentListenerAdded = false

    override fun addListener(listener: ChangeListener<T>) {
        listeners.add(listener)

        // dispatch initial value
        listener(value)

        // register content listener
        if (!contentListenerAdded) {
            contentObservers.addListener(uri, contentListener)
            contentListenerAdded = true
        }
    }

    override fun removeListener(listener: ChangeListener<T>) {
        listeners.remove(listener)

        // unregister content listener
        if (listeners.isEmpty() && contentListenerAdded) {
            contentObservers.removeListener(uri, contentListener)
            contentListenerAdded = false
        }
    }

    interface Adapter<T> {

        fun get(
            name: String,
            defaultValue: T,
            contentResolver: ContentResolver,
            type: Setting.Type
        ): T

        fun set(
            name: String,
            value: T,
            contentResolver: ContentResolver,
            type: Setting.Type
        )
    }
}
