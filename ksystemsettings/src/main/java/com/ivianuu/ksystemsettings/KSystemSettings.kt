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

package com.ivianuu.ksystemsettings

import android.content.ContentResolver
import android.content.Context

/**
 * System settings
 */
class KSystemSettings private constructor(private val contentResolver: ContentResolver) {

    private val contentObservers = ContentObservers(contentResolver)

    /**
     * Returns a new [FloatSystemSetting]
     */
    fun float(
        name: String,
        type: SettingsType,
        defaultValue: Float = DEFAULT_FLOAT
    ): FloatSystemSetting =
        RealSystemSetting(
            contentObservers,
            contentResolver, name, defaultValue, FloatAdapter,
            type
        )

    /**
     * Returns a new [IntSystemSetting]
     */
    fun int(
        name: String,
        type: SettingsType,
        defaultValue: Int = DEFAULT_INT
    ): IntSystemSetting =
        RealSystemSetting(
            contentObservers,
            contentResolver, name, defaultValue, IntAdapter, type
        )

    /**
     * Returns a new [LongSystemSetting]
     */
    fun long(
        name: String,
        type: SettingsType,
        defaultValue: Long = DEFAULT_LONG
    ): LongSystemSetting =
        RealSystemSetting(
            contentObservers,
            contentResolver, name, defaultValue, LongAdapter, type
        )

    /**
     * Returns a new [StringSystemSetting]
     */
    fun string(
        name: String,
        type: SettingsType,
        defaultValue: String = DEFAULT_STRING
    ): StringSystemSetting =
        RealSystemSetting(
            contentObservers,
            contentResolver,
            name,
            defaultValue,
            StringAdapter,
            type
        )

    companion object {
        private const val DEFAULT_FLOAT = 0f
        private const val DEFAULT_INT = 0
        private const val DEFAULT_LONG = 0L
        private const val DEFAULT_STRING = ""

        /**
         * Returns a new [KSystemSettings] instance which uses the [Context.getContentResolver]
         */
        operator fun invoke(context: Context) =
            invoke(context.contentResolver)

        /**
         * Returns a new [KSystemSettings] instance which uses the [contentResolver] internally
         */
        operator fun invoke(contentResolver: ContentResolver) =
            KSystemSettings(contentResolver)
    }
}
