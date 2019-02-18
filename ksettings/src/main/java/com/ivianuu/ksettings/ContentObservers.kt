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

package com.ivianuu.ksettings

import android.content.ContentResolver
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

internal class ContentObservers(private val contentResolver: ContentResolver) {

    private val observers = mutableMapOf<Uri, Observer>()
    private val lock = ReentrantLock()

    fun addListener(uri: Uri, listener: () -> Unit): Unit = lock.withLock {
        observers.getOrPut(uri) {
            Observer().also {
                it.addListener(listener)
                contentResolver.registerContentObserver(uri, false, it)
            }
        }
    }

    fun removeListener(uri: Uri, listener: () -> Unit): Unit = lock.withLock {
        val observer = observers[uri] ?: return@withLock
        observer.removeListener(listener)

        // remove observers without listeners
        if (observer.isEmpty) {
            contentResolver.unregisterContentObserver(observer)
        }
    }

    private class Observer : ContentObserver(HANDLER) {
        private val listeners = mutableListOf<(() -> Unit)>()

        val isEmpty get() = listeners.isEmpty()

        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            listeners.toList().forEach { it() }
        }

        fun addListener(listener: () -> Unit) {
            if (!listeners.contains(listener)) {
                listeners.add(listener)
            }
        }

        fun removeListener(listener: () -> Unit) {
            listeners.remove(listener)
        }
    }

    private companion object {
        private val HANDLER = Handler()
    }
}