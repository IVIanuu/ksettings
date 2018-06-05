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

import android.content.ContentResolver
import android.content.Context
import com.ivianuu.rxsystemsettings.adapter.FloatAdapter
import com.ivianuu.rxsystemsettings.adapter.IntAdapter
import com.ivianuu.rxsystemsettings.adapter.LongAdapter
import com.ivianuu.rxsystemsettings.adapter.StringAdapter

/**
 * Rx system settings
 */
class RxSystemSettings private constructor(
    private val contentResolver: ContentResolver,
    private val contentObserverFactory: ContentObserverFactory
) {

    @JvmOverloads
    fun getFloat(
        name: String,
        type: SettingsType,
        defaultValue: Float = DEFAULT_FLOAT
    ): SystemSetting<Float> =
        RealSystemSetting(
            contentResolver, name, defaultValue, FloatAdapter,
            contentObserverFactory, type
        )

    @JvmOverloads
    fun getInt(
        name: String,
        type: SettingsType,
        defaultValue: Int = DEFAULT_INT
    ): SystemSetting<Int> =
        RealSystemSetting(
            contentResolver, name, defaultValue, IntAdapter, contentObserverFactory, type
        )

    @JvmOverloads
    fun getLong(
        name: String,
        type: SettingsType,
        defaultValue: Long = DEFAULT_LONG
    ): SystemSetting<Long> =
        RealSystemSetting(
            contentResolver, name, defaultValue, LongAdapter, contentObserverFactory, type
        )

    @JvmOverloads
    fun getString(
        name: String,
        type: SettingsType,
        defaultValue: String = DEFAULT_STRING
    ): SystemSetting<String> =
        RealSystemSetting(
            contentResolver, name, defaultValue, StringAdapter, contentObserverFactory,
            type
        )

    companion object {
        private const val DEFAULT_FLOAT = 0f
        private const val DEFAULT_INT = 0
        private const val DEFAULT_LONG = 0L
        private const val DEFAULT_STRING = ""

        @JvmStatic
        fun create(context: Context): RxSystemSettings {
            return RxSystemSettings(
                context.contentResolver, ContentObserverFactory(context)
            )
        }
    }
}
