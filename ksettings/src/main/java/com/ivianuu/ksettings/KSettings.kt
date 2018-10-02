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
import android.content.Context

/**
 * settings
 */
class KSettings private constructor(private val contentResolver: ContentResolver) {

    private val contentObservers = ContentObservers(contentResolver)

    /**
     * Returns a new [FloatSetting]
     */
    fun float(
        name: String,
        type: Setting.Type,
        defaultValue: Float = DEFAULT_FLOAT
    ): FloatSetting =
        RealSetting(
            contentObservers,
            contentResolver, name, defaultValue, FloatAdapter,
            type
        )

    /**
     * Returns a new [IntSetting]
     */
    fun int(
        name: String,
        type: Setting.Type,
        defaultValue: Int = DEFAULT_INT
    ): IntSetting =
        RealSetting(
            contentObservers,
            contentResolver, name, defaultValue, IntAdapter, type
        )

    /**
     * Returns a new [LongSetting]
     */
    fun long(
        name: String,
        type: Setting.Type,
        defaultValue: Long = DEFAULT_LONG
    ): LongSetting =
        RealSetting(
            contentObservers,
            contentResolver, name, defaultValue, LongAdapter, type
        )

    /**
     * Returns a new [StringSetting]
     */
    fun string(
        name: String,
        type: Setting.Type,
        defaultValue: String = DEFAULT_STRING
    ): StringSetting =
        RealSetting(
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
         * Returns a new [KSettings] instance which uses the [Context.getContentResolver]
         */
        operator fun invoke(context: Context) =
            invoke(context.contentResolver)

        /**
         * Returns a new [KSettings] instance which uses the [contentResolver] internally
         */
        operator fun invoke(contentResolver: ContentResolver) =
            KSettings(contentResolver)
    }
}
