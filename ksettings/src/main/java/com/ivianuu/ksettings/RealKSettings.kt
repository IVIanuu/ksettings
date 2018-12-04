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

/**
 * Real k settings
 */
internal class RealKSettings(private val contentResolver: ContentResolver) : KSettings {

    private val contentObservers = ContentObservers(contentResolver)

    override fun float(
        name: String,
        type: Setting.Type,
        defaultValue: Float
    ) =
        RealSetting(
            contentObservers,
            contentResolver, name, defaultValue, FloatAdapter,
            type
        )

    override fun int(
        name: String,
        type: Setting.Type,
        defaultValue: Int
    ) =
        RealSetting(
            contentObservers,
            contentResolver, name, defaultValue, IntAdapter, type
        )

    override fun long(
        name: String,
        type: Setting.Type,
        defaultValue: Long
    ) =
        RealSetting(
            contentObservers,
            contentResolver, name, defaultValue, LongAdapter, type
        )

    override fun string(
        name: String,
        type: Setting.Type,
        defaultValue: String
    ) =
        RealSetting(
            contentObservers,
            contentResolver,
            name,
            defaultValue,
            StringAdapter,
            type
        )

    companion object {
        internal const val DEFAULT_FLOAT = 0f
        internal const val DEFAULT_INT = 0
        internal const val DEFAULT_LONG = 0L
        internal const val DEFAULT_STRING = ""
    }
}