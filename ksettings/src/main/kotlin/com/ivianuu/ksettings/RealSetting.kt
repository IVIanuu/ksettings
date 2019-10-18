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
import java.util.concurrent.atomic.AtomicBoolean

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

    override val uri: Uri by lazy {
        when (type) {
            Setting.Type.Global -> Settings.Global.getUriFor(name)
            Setting.Type.Secure -> Settings.Secure.getUriFor(name)
            Setting.Type.System -> Settings.System.getUriFor(name)
        }
    }

    override val exists: Boolean by lazy { SettingsValidator.doesExist(name) }

    private val contentListener = {
        val value = get()
        synchronized(listeners) { listeners }
            .toList()
            .forEach { it(value) }
    }

    private val listeners = mutableListOf<ChangeListener<T>>()
    private val contentListenerAdded = AtomicBoolean(false)

    override fun get(): T = try {
        adapter.get(name, defaultValue, contentResolver, type)
    } catch (e: Exception) {
        throw RuntimeException("couldn't read value for name: $name", e)
    }

    override fun set(value: T) {
        try {
            adapter.set(name, value, contentResolver, type)
        } catch (e: Exception) {
            throw RuntimeException("couldn't write value for name: $name", e)
        }
    }

    override fun addListener(listener: ChangeListener<T>) {
        synchronized(listeners) { listeners += listener }

        // dispatch initial value
        listener(get())

        // register content listener
        if (!contentListenerAdded.getAndSet(true)) {
            contentObservers.addListener(uri, contentListener)
        }
    }

    override fun removeListener(listener: ChangeListener<T>) {
        val isEmpty = synchronized(this) {
            listeners -= listener
            listeners.isEmpty()
        }

        // unregister content listener
        if (isEmpty && contentListenerAdded.getAndSet(false)) {
            contentObservers.removeListener(uri, contentListener)
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
