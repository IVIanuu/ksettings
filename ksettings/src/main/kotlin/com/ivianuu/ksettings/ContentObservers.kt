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

internal class ContentObservers(private val contentResolver: ContentResolver) {

    private val observers = mutableMapOf<Uri, Observer>()

    fun addListener(uri: Uri, listener: () -> Unit) {
        val oldObserver = synchronized(observers) { observers[uri] }
        if (oldObserver != null) {
            oldObserver.addListener(listener)
        } else {
            val newObserver = Observer()
            synchronized(observers) { observers[uri] = newObserver }
            contentResolver.registerContentObserver(uri, false, newObserver)
            newObserver.addListener(listener)
        }
    }

    fun removeListener(uri: Uri, listener: () -> Unit) {
        val observer = synchronized(observers) { observers[uri] } ?: return
        observer.removeListener(listener)

        // remove observers without listeners
        if (observer.isEmpty) {
            contentResolver.unregisterContentObserver(observer)
            synchronized(observers) { observers -= uri }
        }
    }

    private class Observer : ContentObserver(HANDLER) {
        private val listeners = mutableListOf<(() -> Unit)>()

        val isEmpty get() = synchronized(listeners) { listeners.isEmpty() }

        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            synchronized(listeners) { listeners }
                .toList()
                .forEach { it() }
        }

        fun addListener(listener: () -> Unit) {
            listeners += listener
        }

        fun removeListener(listener: () -> Unit) {
            listeners -= listener
        }
    }

    private companion object {
        private val HANDLER = Handler()
    }
}