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

package com.ivianuu.rxsystemsettings

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler

import io.reactivex.Observable

/**
 * Creates content observer observables
 */
internal class ContentObserverFactory(
    private val context: Context
) {

    private val handler = Handler()

    fun observe(uri: Uri): Observable<Unit> = Observable.create { e ->
        val observer = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                e.onNext(Unit)
            }

            override fun deliverSelfNotifications() = false
        }

        e.setCancellable { context.contentResolver.unregisterContentObserver(observer) }

        context.contentResolver
            .registerContentObserver(uri, false, observer)
    }
}
